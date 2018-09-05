package com.shoppingcart.DAO.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcart.Beans.OrderListBean;
import com.shoppingcart.Beans.UserDetailsBean;
import com.shoppingcart.DAO.OrdersDao;
import com.shoppingcart.DAO.Orders_DetailsDao;
import com.shoppingcart.DAO.UserDao;
import com.shoppingcart.Model.Orders;

public class OrdersDaoImpl implements OrdersDao {

	@Autowired
	private HibernateDaoImpl hibernateDaoImpl;
	@Autowired
	Orders_DetailsDao orders_DetailsDao;
	@Autowired
	UserDao userDao;
	
	public void setOrderInCart(String productCode) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		Session session = null;
		try {
			session = hibernateDaoImpl.getSessionFactory().openSession();
			transaction = session.getTransaction();
			CriteriaBuilder ctrBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Orders> query = ctrBuilder.createQuery(Orders.class);
			Root<Orders> root = query.from(Orders.class);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public void saveOrder() {
		// TODO Auto-generated method stub
		int orderid = getOrderId();
		Orders order = new Orders();
		order.setOrderid(orderid);
		order.setTotal_amount(1000);
		order.setCust_email("santosh@gmail.com");
		order.setDate(new Date());
		Session session =  hibernateDaoImpl.getSessionFactory().openSession();
		session.save(order);
		session.close();
	}
	public int getOrderId() {
		
		return 100;
	}

	public List<OrderListBean> getOrderByEmailID(String email) {
		// TODO Auto-generated method stub
			List<Orders> lst = new ArrayList<Orders>();
			Orders order = new Orders();
			order.setCust_email(email);
			lst.add(order);
			//System.out.println(" order list size : "+ lst.size()+ " values: "+ lst.toString());
			OrderListBean orderbean = null;
			List<OrderListBean> list = new ArrayList<OrderListBean>();
			Iterator<Orders> iterator = lst.iterator();
			while (iterator.hasNext()) {
				Orders orders = (Orders) iterator.next();
				orderbean = new OrderListBean();
				int maxID = orders_DetailsDao.getMaxOrderID(orders.getCust_email());
				orderbean.setOrderid(maxID);
				orderbean.setEmail(orders.getCust_email());
				int sum = orders_DetailsDao.getSumOfOrders(orders.getCust_email());
				orderbean.setTotalamount(sum);
				UserDetailsBean userdetail = userDao.getUserByEmailId(orders.getCust_email());
				orderbean.setName(userdetail.getName());
				orderbean.setAddress(userdetail.getAddress());
				list.add(orderbean);
			}
			return list;
	}
		
	private List<Orders> getOrderByEmail(String email){
		
		//List<Orders> orderList = new ArrayList<Orders>();
		Session session = hibernateDaoImpl.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Orders> query = builder.createQuery(Orders.class);
		Root<Orders> root = query.from(Orders.class);
		query.select(root).where(builder.equal(root.get("cust_email"), email));
		query.orderBy(builder.desc(root.get("orderid")));
		Query<Orders> qq = session.createQuery(query);
		List<Orders> orderList = qq.list();
		
		return orderList;
	}

}
