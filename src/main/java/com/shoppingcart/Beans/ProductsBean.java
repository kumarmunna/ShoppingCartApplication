package com.shoppingcart.Beans;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ProductsBean {

	private String code;
	private Date create_date;
	private MultipartFile data;
	private String name;
	private double price;
	private String filename;
	private String filePath;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public MultipartFile getData() {
		return data;
	}
	public void setData(MultipartFile data) {
		this.data = data;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Override
	public String toString() {
		return "ProductsBean [code=" + code + ", create_date=" + create_date
				+ ", data=" + data + ", name=" + name + ", price=" + price
				+ ", filename=" + filename + ", filePath=" + filePath + "]";
	}
}
