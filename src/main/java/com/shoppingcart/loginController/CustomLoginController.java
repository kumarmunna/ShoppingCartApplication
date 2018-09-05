package com.shoppingcart.loginController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingcart.Beans.UserDetailsBean;
import com.shoppingcart.DAO.UserDao;
import com.shoppingcart.DAO.impl.UserDaoImpl;
import com.shoppingcart.Model.LoginBean;
 
@Controller
public class CustomLoginController {
 
	@Autowired
	UserDao userDao;
     
    @RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
    	System.out.println(" in welcome mapping ........");
        model.addAttribute("greeting", "Hi, Welcome to mysite");
        return "welcome";
    }
    
    @RequestMapping("/afterDefaultLogin")
    public String defaultLoginSuccess(HttpServletRequest request) {
		
    	User authUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
    	UserDetailsBean bean = new UserDetailsBean();
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
		System.out.println(" default user deatils; " + bean);
		HttpSession session = request.getSession();
		session.setAttribute("userstatus", "auth");
		session.setAttribute("userdetails", bean);
		session.setAttribute("userrole", role);
    	return "/productList";
	}
 
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(HttpServletRequest request,ModelMap model) {
        
    	//setUserDetils(request);
    	model.addAttribute("user", getPrincipal());
        
        return "admin";
    }
     
    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public String dbaPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "dba";
    }
 
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public ModelAndView accessDeniedPage(ModelMap modelmap) {
    	
    	ModelAndView model = new ModelAndView();
		System.out.println("checking auth: ");
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		System.out.println("auth: "+ auth);
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User userDetail = (User) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;
    }
 
    @RequestMapping(value = "/login**", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
    	System.out.println(" in login customer controller ......");
    	HttpSession session = request.getSession();
    	String url_prior_login = request.getHeader("Referer");
    	if(url_prior_login != null && !url_prior_login.contains("login")){
    		System.out.println("going to set prior login url.. ");
    		session.setAttribute("url_prior_login",url_prior_login);
    	}
    	System.out.println("url_prior_login: "+ url_prior_login);
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
			model.setViewName("customLoginForm");
			System.out.println(" in customerLoginController with prior url: "+ session.getAttribute("url_prior_login"));
			session.invalidate();
			return model;
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
			model.setViewName("customLoginForm");
			session.invalidate();
			return model;
		}
		
		//setUserDetils(request);
		model.setViewName("customLoginForm");
		
		return model;

	}
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
    	System.out.println(" in logout customLoginController");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        request.getSession().invalidate();
        return "redirect:/login?logout";
    }
    
    @RequestMapping(value="/error", method = RequestMethod.GET)
    public String errorPage (HttpServletRequest request, HttpServletResponse response) {
    	System.out.println(" in error customLoginController");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?error";
    }
 
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}