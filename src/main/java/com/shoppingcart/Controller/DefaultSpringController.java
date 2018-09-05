package com.shoppingcart.Controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.shoppingcart.Beans.ProductsBean;
import com.shoppingcart.DAO.ProductsDao;

/**
 * This controller handle the default request only.
 * 
 * @author santoshkumar_si
 * 
 */
@Controller
public class DefaultSpringController {

	@Autowired
	private ProductsDao productsDao;

	@RequestMapping(value = { "/" })
	public ModelAndView defaultMapping(HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		System.out.println(" in my default controller ... ");
		// ModelAndView mv = new ModelAndView();
		mv.setViewName("ProductList");
		List<ProductsBean> productList = productsDao.getProductList();
		if (productList.isEmpty()) {
			mv.addObject("emptyProduct",
					"Product is not available in database.");
		} else {
			mv.addObject("productList", productList);
			return mv;
		}
		return mv;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView defaultURLMapping(HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		System.out.println(" in my ---------default controller ... ");
		// ModelAndView mv = new ModelAndView();
		mv.setViewName("ProductList");
		List<ProductsBean> productList = productsDao.getProductList();
		if (productList.isEmpty()) {
			mv.addObject("emptyProduct",
					"Product is not available in database.");
		} else {
			mv.addObject("productList", productList);
			return mv;
		}
		return mv;
	}

	@RequestMapping("/500")
	public void notFoundHandler() {
		System.out.println("Item not found. HTTP 500 returned.");
	}

}