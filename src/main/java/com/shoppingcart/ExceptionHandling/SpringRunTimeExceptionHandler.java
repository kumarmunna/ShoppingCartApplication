package com.shoppingcart.ExceptionHandling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
//Not in user in this application ...
@Controller
public class SpringRunTimeExceptionHandler {

	
	@ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException() {
		System.out.println(" in not found controller .......");
        return "notfound";
    }
}
