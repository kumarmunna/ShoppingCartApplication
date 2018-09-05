package com.shoppingcart.Services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.shoppingcart.Beans.UserDetailsBean;
import com.shoppingcart.DAO.UserDao;
import com.shoppingcart.DAO.impl.UserDaoImpl;

/**
 * This class is used to get the user details from the database and load the details in session for access on various location.
 * 
 * @author santoshkumar_si
 *
 */
public class UserDetailsHandlerService {

	@Autowired
	UserDao userDao;
	
	public void setUserDetails(HttpServletRequest request,Authentication authentication){


		UserDetailsBean bean = new UserDetailsBean();
		User authUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		bean.setUsername(authUser.getUsername());
		bean.setPassword(request.getParameter("password"));
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authUser.getAuthorities()) {
			roles.add(a.getAuthority());
		}
		String role = null;
		if (roles.contains("ROLE_ADMIN")) {
			role = "ADMIN";
		} else if (roles.contains("ROLE_MANAGER")) {
			role = "MANAGER";
		} else if (roles.contains("ROLE_DBA")) {
			role = "DBA";
		} else {
			role = "USER";
		}
		bean.setUserrole(role);
		System.out.println(" user deatils; " + bean);
		HttpSession session = request.getSession();
		session.setAttribute("userstatus", "auth");
		session.setAttribute("userdetails", bean);
		session.setAttribute("userrole", role);
	
		
		
		
	}
	
	/**
	 * For each and user, it must have only one role in database.
	 * @return
	 */
	public UserDetailsBean getUserAllDetails(){
		UserDetailsBean details = new UserDetailsBean();
		User authUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String username = authUser.getUsername();
		String role = null;
		for (GrantedAuthority a : authUser.getAuthorities()) {
			role = a.getAuthority();
		}
		details = userDao.getUserDetailsFromDB(username, role);
		return details;
	}
}
