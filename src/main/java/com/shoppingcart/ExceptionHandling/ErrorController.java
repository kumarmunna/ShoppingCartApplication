package com.shoppingcart.ExceptionHandling;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@RequestMapping(value = "errors", method = RequestMethod.GET)
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

		ModelAndView errorPage = new ModelAndView();
		String errorMsg = "";
		int httpErrorCode = getErrorCode(httpRequest);
System.out.println(" in error controller with status code: "+ httpErrorCode);
		switch (httpErrorCode) {
		case 400: {
			errorMsg = "Http Error Code: 400. Bad Request";
			errorPage.setViewName("notfound");
			break;
		}
		case 401: {
			errorMsg = "Http Error Code: 401. Unauthorized";
			errorPage.setViewName("ExceptionHandling");
			break;
		}
		case 404: {
			errorMsg = "Http Error Code: 404. Resource not found";
			errorPage.setViewName("notfound");
			break;
		}
		case 500: {
			errorMsg = "Http Error Code: 500. Internal Server Error";
			errorPage.setViewName("ExceptionHandling");
			break;
		}
		}
		errorPage.addObject("errorMsg", errorMsg);
		return errorPage;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest
				.getAttribute("javax.servlet.error.status_code");
	}
}
