package com.shoppingcart.ExceptionHandling;

import java.net.BindException;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
// Not in user in application ...........
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
