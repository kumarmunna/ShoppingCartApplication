package com.shoppingcart.UrlHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shoppingcart.Beans.UserDetailsBean;
import com.shoppingcart.DAO.UserDao;
import com.shoppingcart.DAO.impl.UserDaoImpl;
import com.shoppingcart.Services.UserDetailsHandlerService;
import com.shoppingcart.loginController.CustomLoginController;

@Component
public class LoginSuccessUrlHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private UserDaoImpl userDaoImpl;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// Checking the service is null or not.
		if(userDaoImpl == null){
			ServletContext context = request.getServletContext();
			WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(context);
			userDaoImpl = (UserDaoImpl) webContext.getBean("userDaoImpl");
		}
		System.out.println(" userDaoImpl "+ userDaoImpl);
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		System.out.println("auth: "+ auth);
		
		if (session != null) {
			String redirectUrl = (String) session
					.getAttribute("url_prior_login");
			System.out.println(" redirect: "+ redirectUrl);
			if (redirectUrl != null) {
				// we do not forget to clean this attribute from session
				session.removeAttribute("url_prior_login");
				// setting the user profile in the session
				setUserDetils(request, authentication,userDaoImpl);
				// then we redirect
				getRedirectStrategy().sendRedirect(request, response,
						redirectUrl);
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
				setUserDetils(request, authentication,userDaoImpl);
			}
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
			setUserDetils(request, authentication,userDaoImpl);
		}
	}

	private void setUserDetils(HttpServletRequest request,
			Authentication authentication,UserDaoImpl userDaoImpl) {

		UserDetailsBean bean = new UserDetailsBean();
		User authUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		bean.setUsername(authUser.getUsername());
		bean.setPassword(request.getParameter("password"));
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authUser.getAuthorities()) {
			roles.add(a.getAuthority());
		}
		System.out.println(" role of user: "+ roles.toString());
		String role = null;
		if (roles.contains("EMPLOYEE")) {
			role = "ADMIN";
		} else if (roles.contains("MANAGER")) {
			role = "MANAGER";
		} else {
			role = "USER";
		}
		bean.setUserrole(role);
		bean = userDaoImpl.getUserDetailsFromDB(authUser.getUsername(), role);
		System.out.println(" user deatils in LoginSuccessUrlHandler: " + bean);
		HttpSession session = request.getSession();
		session.setAttribute("userstatus", "auth");
		session.setAttribute("userdetails", bean);
		session.setAttribute("userrole", role);
	}
}
