package com.shoppingcart.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


public class RequestFilterConfig extends GenericFilterBean {

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println(" in filter distroy..........");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		try {
			System.out.println(" in do filter ..............");
			chain.doFilter(request, response);
			System.out.println(" in do hhhfilter ..............");
		} catch (Exception ex) {
			request.setAttribute("errMsg", ex);
			System.out.println(" in do filter catch..............");
			request.getRequestDispatcher("/WEB-INF/views/ExceptionHandling.jsp")
                               .forward(request, response);
		}
	}

}
