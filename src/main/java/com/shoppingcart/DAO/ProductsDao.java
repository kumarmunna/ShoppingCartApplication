package com.shoppingcart.DAO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoppingcart.Beans.ProductsBean;

public interface ProductsDao {

	public boolean saveProducts(MultipartFile file,ProductsBean productsBean);
	public void editProducts(MultipartFile file,ProductsBean productsBean);
	public ProductsBean getProductsDetail(String productCode);
	public List<ProductsBean> getProductList();
	public ProductsBean getProductToCart(String productCode);
	public void deleteProduct(ProductsBean productsBean);
	public void deleteProductByCode(String productCode);
	public double getPrice(String productCode);
	public String getNewProductCodeFromDB();
}
