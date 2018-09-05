package com.shoppingcart.loginController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingcart.DAO.impl.UserDaoImpl;
import com.shoppingcart.Model.LoginBean;
import com.shoppingcart.Validator.LoginValidator;

@Controller
public class LoginController {

	// private Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	LoginValidator loginValidator;
	@Autowired
	UserDaoImpl userDaoImpl;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(loginValidator);
	}

	/**
	 * This method used to redirect the login page from request.
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/loginPage" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.addObject("loginbean", new LoginBean());
		model.setViewName("Login");
		return model;
	}

	/**
	 * Method used to login the users
	 * 
	 * @param loginBean
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/login")
	public ModelAndView userLogin(
			@ModelAttribute("loginbean") @Validated LoginBean loginBean,
			BindingResult result, Model model, HttpServletRequest request) {
		// logger.info("Execution:: userLogin");
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		if (result.hasErrors()) {
			mv.setViewName("Login");
			return mv;
		} else {
			UserDetailsBean details = userDaoImpl.getPassword(loginBean);
			if (details != null) {
				mv.setViewName("home");
				mv.addObject("exist");
				// logger.info("successfull login ");
				session.setAttribute("userstatus", "auth");
				session.setAttribute("userdetails", details);

			} else {
				// logger.info("user not exist ");
				mv.setViewName("Login");
				mv.addObject("notexist", "User's user name does not exist :) ");
				session.setAttribute("userstatus", "unauth");

			}
		}
		return mv;
	}*/

	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

		System.out.println(" login controller : error: "+ error + " logout: "+ logout);
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("customLoginForm");

		return model;

	}*/
	/*// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

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

	}*/

}
