package com.shoppingcart.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Orders {
	

	private int orderid;
	private int total_amount;
	private String cust_email;
	private Date date;
	
	
	
	public Orders() {
		super();
	}
	
	public Orders(int orderid, Date date) {
		super();
		this.orderid = orderid;
		this.date = date;
	}




	public Orders(String cust_email) {
		super();
		this.cust_email = cust_email;
	}
	
	public Orders(int orderid) {
		super();
		this.orderid = orderid;
	}


	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OrderId",columnDefinition = "INT DEFAULT 1")
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	@Column(name = "total_amount",columnDefinition = "INT DEFAULT 0")
	public int getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	@Column(name = "customer_email")
	public String getCust_email() {
		return cust_email;
	}
	public void setCust_email(String cust_email) {
		this.cust_email = cust_email;
	}
	@Column(name = "date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Orders [orderid=" + orderid + ", total_amount=" + total_amount
				+ ", cust_email=" + cust_email + ", date=" + date + "]";
	}
	
	
	
}
