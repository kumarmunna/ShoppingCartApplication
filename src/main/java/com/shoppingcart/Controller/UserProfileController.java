package com.shoppingcart.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingcart.Beans.UserDetailsBean;
import com.shoppingcart.DAO.UserDao;
import com.shoppingcart.Services.UserDetailsHandlerService;

@Controller
public class UserProfileController {

	@Autowired
	UserDao userDao;
	@Autowired
	UserDetailsHandlerService userService;

	/**
	 * This method is used to get the user profile details.
	 * 
	 * @return view
	 */
	@RequestMapping("/GetProfile")
	public ModelAndView getProfile(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		UserDetailsBean details = userService.getUserAllDetails();
		System.out.println(" User details in UserProfileController from DB: "
				+ details);
		mv.addObject("userdetails", details);
		mv.setViewName("UserProfile");
		return mv;
	}

	@RequestMapping("UpdateAddress")
	public ModelAndView updateAddress(
			@ModelAttribute("userdetails") @Validated UserDetailsBean userBean,
			BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			mv.setViewName("UserDetails");
		} else {
			System.out.println("bean: " + userBean);
			userDao.updateUserAddress(userBean);
			mv.setViewName("UserProfile");
			mv.addObject("success", "You have successfully updated address..");
		}
		return mv;
	}
}
