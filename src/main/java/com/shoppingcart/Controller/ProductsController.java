package com.shoppingcart.Controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingcart.Beans.ProductsBean;
import com.shoppingcart.DAO.ProductsDao;
import com.shoppingcart.DAO.impl.ProductsDaoImpl;
import com.shoppingcart.Validator.ProductValidator;

@Controller
public class ProductsController {

	@Autowired
	private ProductsDao productsDao;

	@Autowired
	private ProductValidator productValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		System.out.println(" in init binder ........ ");
		binder.setDisallowedFields("data");
		binder.addValidators(productValidator);
	}

	/*
	 * @RequestMapping(value = { "/" }, method = RequestMethod.GET) public
	 * ModelAndView welcomePage(HttpServletRequest request) {
	 * 
	 * System.out.println(" default product controller ....referer: "+
	 * request.getHeader("Referer")); ModelAndView mv = new ModelAndView();
	 * mv.setViewName("ProductList"); List<ProductsBean> productList =
	 * productsDao.getProductList(); if(productList.isEmpty()){
	 * mv.addObject("emptyProduct", "Product is not available in database.");
	 * }else{ mv.addObject("productList", productList); } return mv; }
	 */

	/**
	 * Method use to redirect on the add product view.
	 * 
	 * @return
	 */
	@RequestMapping("/createProduct")
	public ModelAndView product() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addProducts");
		ProductsBean productsBean = new ProductsBean();
		String productCode = getProductCode();
		productsBean.setCode(productCode);

		mv.addObject("productbean", productsBean);
		return mv;
	}

	/**
	 * Method used for add product in database.
	 * 
	 * @param productsBean
	 * @param result
	 * @param model
	 * @return
	 * @throws SerialException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ModelAndView SaveProductInDataBase(HttpServletRequest request,
			@RequestParam("data") MultipartFile file,
			@ModelAttribute("productbean") @Validated ProductsBean productsBean, BindingResult result,
			Model model) throws SerialException, SQLException {
		ModelAndView mv = new ModelAndView();
		System.out.println("Going to add Product Bean with: " + productsBean);
		if(result.hasErrors()){
			mv.setViewName("addProducts");
			return mv;
		}
		boolean status = productsDao.saveProducts(file, productsBean);
		if(status){
			mv.addObject("success", " Product added successfull in database.");
		} else {
			mv.addObject("error", " Product is not added in database due to internal issues.");
		}
		mv.setViewName("addProducts");
		return mv;
	}

	/**
	 * Method used for edit product.
	 * 
	 * @param productCode
	 * @return
	 */
	@RequestMapping("/editAndSaveProduct")
	public ModelAndView editProduct(
			@ModelAttribute("productbean") @Validated ProductsBean productsBean,
			@RequestParam("data") MultipartFile file, BindingResult result,
			Model model) {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			mv.setViewName("EditProduct");
		} else {
			productsDao.editProducts(file, productsBean);
			mv.setViewName("redirect:/productList");
		}
		return mv;
	}

	/**
	 * This method is used to redirect to edit product page.
	 * 
	 * @param productCode
	 * @return
	 */
	@RequestMapping("/editProduct")
	public ModelAndView editPageRedirect(
			@RequestParam("productCode") String productCode) {
		ModelAndView mv = new ModelAndView();
		ProductsBean productsBean = productsDao.getProductsDetail(productCode);
		mv.addObject("productbean", productsBean);
		mv.setViewName("EditProduct");
		return mv;
	}

	@RequestMapping("/productList")
	public ModelAndView getProductsList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("ProductList");
		List<ProductsBean> productList = productsDao.getProductList();
		if (productList.isEmpty()) {
			mv.addObject("emptyProduct",
					"Product is not available in database.");
		} else {
			mv.addObject("productList", productList);
		}
		return mv;
	}


	/**
	 * 
	 * @param productCode
	 * @return
	 */
	@RequestMapping("/deleteProduct")
	public ModelAndView deleteProductFromDatabase(
			@RequestParam("productCode") String productCode) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("ProductList");
		productsDao.deleteProductByCode(productCode);
		List<ProductsBean> productList = productsDao.getProductList();
		mv.addObject("productList", productList);
		return mv;
	}

	/**
	 * This method is used to generate a unique product code to display on add
	 * product page when user(MANAGER) trying to add new product in database.
	 */
	public String getProductCode() {
		return productsDao.getNewProductCodeFromDB();
	}
}