package com.shoppingcart.DAO.impl;

import java.awt.image.BandCombineOp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcart.Beans.CartInfoBean;
import com.shoppingcart.Beans.OrderDetailsBean;
import com.shoppingcart.Beans.OrderListBean;
import com.shoppingcart.Beans.ProductsBean;
import com.shoppingcart.Beans.UserDetailsBean;
import com.shoppingcart.DAO.Orders_DetailsDao;
import com.shoppingcart.DAO.ProductsDao;
import com.shoppingcart.DAO.UserDao;
import com.shoppingcart.Model.Orders;
import com.shoppingcart.Model.Orders_Details;
import com.shoppingcart.Model.Products;

public class Orders_DetailsDaoImpl implements Orders_DetailsDao {

	@Autowired
	HibernateDaoImpl hibernateDaoImpl;
	@Autowired
	UserDao userDao;
	@Autowired
	ProductsDao productsDao;

	public void saveOrder(Set<CartInfoBean> cartInfoBeans,UserDetailsBean userDetailsBean) {
		// TODO Auto-generated method stub
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Orders_Details orders_Details = null;
		//System.out.println(" in save order -----email is-"+userDetailsBean.getEmail()+" name: "+ userDetailsBean.getName());
		
		Orders order = new Orders();
		int totalAmntInCart = getTotalAmountInCartItems(cartInfoBeans);
//		System.out.println(" max id-----> "+ order.toString());
		order.setTotal_amount(totalAmntInCart);
		//order.setOrderid(order.getOrderid() + 1);
		order.setCust_email(userDetailsBean.getEmail());
		order.setDate(new java.sql.Date(new Date().getTime()));
		try {
			for (CartInfoBean cartInfoBean : cartInfoBeans) {
				orders_Details = new Orders_Details();
				//System.out.println("orderid:"+order.getOrderid() +" eamil: "+ order.getCust_email() +" total:"+order.getTotal_amount());
				orders_Details.setOrderId(order);
				orders_Details.setProduct_code(cartInfoBean.getCode());
				orders_Details.setProduct_price(cartInfoBean.getPrice());
				orders_Details.setQuantity(cartInfoBean.getQuantity());
				int productTotal = (((Double)cartInfoBean.getPrice()).intValue() * cartInfoBean.getQuantity());
				orders_Details.setTotal_amount(productTotal);
				session.save(orders_Details);
			}

			//session.close();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTotalAmountInCartItems(Set<CartInfoBean> cartInfoBeans){
		int totalAmount = 0;
		
		Iterator<CartInfoBean> itr = cartInfoBeans.iterator();
		while (itr.hasNext()) {
			CartInfoBean cartInfoBean = (CartInfoBean) itr.next();
			Double dd = (Double) cartInfoBean.getPrice();
			int qty = cartInfoBean.getQuantity();
			totalAmount += (dd.intValue()* qty);
		}
		return totalAmount;
	}
	public Orders getMaxId(UserDetailsBean userDetailsBean) {
		Orders o = new Orders();
		//System.out.println(" in getmaxid ------");
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		DetachedCriteria maxOrderId = DetachedCriteria.forClass(Orders.class)
				.setProjection(Projections.max("orderid"));
		@SuppressWarnings("deprecation")
		Orders order = (Orders) session
				.createCriteria(Orders.class)
				.add(org.hibernate.criterion.Property.forName("orderid").eq(
						maxOrderId)).uniqueResult();
		//System.out.println(" in save order ------"+ order);
		if(order == null){
			order = new Orders();
			order.setOrderid(0);
		}
		session.clear();
		session.close();
		return o;
	}

	/**
	 * This method will return the order list from DB.
	 * 1. It will fetch all distinct mail id from the order table and then based on the highest total price it will return 
	 * the distinct record with highest total price not all price.
	 * 
	 */
	public List<OrderListBean> orderList() {
		// TODO Auto-generated method stub
		
		List<Orders> lst = getUniqueEmailFromOrder();
		//System.out.println(" order list size : "+ lst.size()+ " values: "+ lst.toString());
		OrderListBean orderbean = null;
		List<OrderListBean> list = new ArrayList<OrderListBean>();
		Iterator<Orders> iterator = lst.iterator();
		while (iterator.hasNext()) {
			Orders orders = (Orders) iterator.next();
			orderbean = new OrderListBean();
			int maxID = getMaxOrderID(orders.getCust_email());
			orderbean.setOrderid(maxID);
			orderbean.setEmail(orders.getCust_email());
			int sum = getSumOfOrders(orders.getCust_email());
			orderbean.setTotalamount(sum);
			UserDetailsBean userdetail = userDao.getUserByEmailId(orders.getCust_email());
			orderbean.setName(userdetail.getName());
			orderbean.setAddress(userdetail.getAddress());
			list.add(orderbean);
		}
		return list;
	}

	/**
	 * Based on the order id, it will get the 
	 */
	public List<OrderDetailsBean> getOrderByOrderId(int orderid) {
		// TODO Auto-generated method stub
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Orders_Details> query = builder.createQuery(Orders_Details.class);
		Root<Orders_Details> root = query.from(Orders_Details.class);
		Orders order = new Orders();
		String email = getEmailIDFromOrderID(orderid);
		order.setOrderid(orderid);
		
		List list = session.createQuery("from Orders_Details where cust_email='"+email+"'").list();
		List<OrderDetailsBean> orderDetailList = new ArrayList<OrderDetailsBean>();
		OrderDetailsBean bean = null;
		//System.out.println(" befor for loop ----->> ");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Orders_Details orders_Details = (Orders_Details) iterator.next();
			bean = new OrderDetailsBean();
			//System.out.println(" inside for loop with orderid----->> "+ orders_Details.getOrderId().getOrderid());
			bean.setOrderid(orders_Details.getOrderId().getOrderid());
			bean.setProductCode(orders_Details.getProduct_code());
			ProductsBean productsBean = productsDao.getProductsDetail(orders_Details.getProduct_code());
			bean.setProductName(productsBean.getName());
			bean.setProductPrice(orders_Details.getProduct_price());
			bean.setQuantity(orders_Details.getQuantity());
			bean.setTotalAmount(orders_Details.getTotal_amount());
			orderDetailList.add(bean);
		}
		return orderDetailList;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Orders> getUniqueEmailFromOrder(){
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Orders> query = builder.createQuery(Orders.class);
		Root<Orders> root = query.from(Orders.class);
		query.distinct(true).multiselect(root.get("cust_email"));
		//query.select(root).where(builder.equal(root.get("email"), email));
		Query<Orders> qq = session.createQuery(query);
		List<Orders> lst = qq.list();
		return lst;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private List<Orders> getUnqueOrder(String email){
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		String hql = "FROM Orders WHERE cust_email = '"+email+"'";
		Query<Orders> query = session.createQuery(hql);
		//query.setMaxResults(1);
		session.close();
		List<Orders> result = query.list();
		return result;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public int getSumOfOrders(String email){
		int sum = 0;
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Orders.class)
                .setProjection(Projections.sum("total_amount"));
		criteria.add(Restrictions.eq("cust_email", email));
		
		Long mysum = (Long) criteria.uniqueResult();
		sum = mysum.intValue();
		return sum;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public int getMaxOrderID(String email) {
		int maxID = 1;
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Orders.class)
                .setProjection(Projections.max("orderid"));
		criteria.add(Restrictions.eq("cust_email", email));
		maxID= (Integer) criteria.uniqueResult();
		return maxID;
	}
	
	/**
	 * 
	 * @param orderID
	 * @return
	 */
	private String getEmailIDFromOrderID(int orderID){
		String mail = null;
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Orders.class);
		criteria.setProjection(Projections.property("cust_email"));
		criteria.add(Restrictions.eq("orderid", orderID));
		mail= (String) criteria.uniqueResult();
		return mail;
	}

	/**
	 * 
	 */
	public List<OrderDetailsBean> getOrderByEmailID(String email) {
		// TODO Auto-generated method stub
		
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Orders> query = builder.createQuery(Orders.class);
		Root<Orders> root = query.from(Orders.class);
		query.multiselect(root.get("orderid"),root.get("date")).where(
				builder.equal(root.get("cust_email"), email));
		query.orderBy(builder.desc(root.get("date")));
		org.hibernate.query.Query<Orders> q = session.createQuery(query);
		List<Orders> orderidList = q.list();
		List<OrderDetailsBean> orderDetailsLists = getOrderDetailsByID(orderidList);
		
		return orderDetailsLists;
	}
	
	/**
	 * 
	 * @param orderIdList
	 * @return
	 */
	private List<OrderDetailsBean> getOrderDetailsByID(List<Orders> orderIdList){
		List<OrderDetailsBean> orderDetailsList = new ArrayList<OrderDetailsBean>();
		
		for (Orders orderid : orderIdList) {
			List<Orders_Details> orders_Details = getOrderDetailsByOrderId(orderid.getOrderid());
			setDataInOrderDetaisBean(orders_Details,orderDetailsList,orderid.getDate());
		}
		
		return orderDetailsList;
	}
	
	/**
	 * 
	 * @param morderid
	 * @return
	 */
	private List<Orders_Details> getOrderDetailsByOrderId(int morderid) {
		
		System.out.println(" comming orderid: "+ morderid);
		Orders orders = new Orders();
		orders.setOrderid(morderid);
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		/*CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Orders_Details> query = builder.createQuery(Orders_Details.class);
		Root<Orders_Details> root = query.from(Orders_Details.class);
		query.select(root).where(
				builder.equal(root.get("orderid"), orders));*/
		String query = " from Orders_Details where orderid="+morderid;
		org.hibernate.query.Query<Orders_Details> detailQuery = session.createQuery(query);
		List<Orders_Details> orderDetailList = detailQuery.list();
		session.close();
		
		return orderDetailList;
	}
	
	/**
	 * Setting the required parameters in orderDetailsBean
	 * @param orderdetails
	 * @param orderDetailsBeans
	 */
	private void setDataInOrderDetaisBean(List<Orders_Details> orderdetails,List<OrderDetailsBean> orderDetailsBeans,
			Date date) {
		OrderDetailsBean orderDetailsBean = null;
		for (Orders_Details orderDetails : orderdetails) {
			orderDetailsBean = new OrderDetailsBean();
			orderDetailsBean.setOrderid(orderDetails.getOrderId().getOrderid());
			orderDetailsBean.setProductCode(orderDetails.getProduct_code());
			orderDetailsBean.setProductName(productsDao.getProductsDetail(orderDetails.getProduct_code()).getName());
			orderDetailsBean.setProductPrice(orderDetails.getProduct_price());
			orderDetailsBean.setQuantity(orderDetails.getQuantity());
			orderDetailsBean.setTotalAmount(orderDetails.getTotal_amount());
			orderDetailsBean.setDate(date);
			orderDetailsBeans.add(orderDetailsBean);
		}
	}
}
