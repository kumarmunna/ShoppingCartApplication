package com.shoppingcart.DAO;

import java.util.List;

import com.shoppingcart.Beans.OrderDetailsBean;
import com.shoppingcart.Beans.OrderListBean;

public interface OrdersDao {

	
	public void saveOrder();
	public List<OrderListBean> getOrderByEmailID(String email);
}
