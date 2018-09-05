package com.shoppingcart.ExceptionHandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@ControllerAdvice
public class GlobalExceptionHandler {

	
/*	@ExceptionHandler(CustomGenericException.class)
	public ModelAndView handleCustomException(CustomGenericException ex) {

		ModelAndView model = new ModelAndView("generic_error");
		model.addObject("errCode", ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());

		return model;

	}*/
	
	/*@ExceptionHandler(CustomGenericException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView resolveException() {
		System.out.println(" in bad request........");
		ModelAndView model = new ModelAndView("error/generic_error");
		model.setViewName("ExceptionHandling");
		return model;
	}*/
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
	@ExceptionHandler(ResourceNotFoundException.class)
	public void handleIOException() {
		System.out.println(" handle IOException ..............");
		// returning 404 error code
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
		
		ModelAndView model = new ModelAndView("generic_error");
		model.addObject("errMsg", "Error occured during the application execution. Please login again.");
		model.addObject("errName", ex);
		ex.printStackTrace();
		return model;

	}
	
	/*@ExceptionHandler(value = { ResourceNotFoundException.class,
			IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex,
			WebRequest request) {
		String bodyOfResponse = "This should be application specific";
		System.out.println(bodyOfResponse);
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
				HttpStatus.CONFLICT, request);
	}
*/
	/*@ExceptionHandler(Exception.class)
	@RequestMapping("**")
	public ModelAndView handleAllException(Exception ex) {

		System.out.println(" in handle all exception....");
		ModelAndView model = new ModelAndView("error/generic_error");
		ex.printStackTrace();
		model.addObject("errMsg", ex);
		model.setViewName("ExceptionHandling");
		return model;

	}*/
}
