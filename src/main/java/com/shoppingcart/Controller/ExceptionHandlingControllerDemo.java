package com.shoppingcart.Controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingcart.ExceptionHandling.CustomGenericException;


public class ExceptionHandlingControllerDemo {

	@RequestMapping(value = "/{type:.+}", method = RequestMethod.GET)
	public ModelAndView getPages(@PathVariable("type") String type)
			throws Exception {
		System.out.println(" Error Type: " + type);
		if ("error".equals(type)) {
			// go handleCustomException
			throw new CustomGenericException("E888", "This is custom message");
		} else if ("io-error".equals(type)) {
			// go handleAllException
			// throw new IOException();
			throw new CustomGenericException("E888", "This is custom message");
		} else if ("login".equals(type)) {
			// go handleAllException
			// throw new IOException();
			System.out.println(" in login error .....");
			throw new CustomGenericException("E888", "This is custom message");
		} else {
			return new ModelAndView("index").addObject("msg", type);
		}

	}
}
