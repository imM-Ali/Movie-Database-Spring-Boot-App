package com.web.dssapp.controller;

import com.web.dssapp.dto.UserDto;
import com.web.dssapp.model.User;
import com.web.dssapp.service.UserService;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String loginForm(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("user",new UserDto());
			return"loginpage";
		}

		return "redirect:/movies/1";
	}	

	// handler method to handle user registration request

	@GetMapping("/signup")
	public String showRegistrationForm(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "signuppage";
	}

	// handler method to handle register user form submit request

	@PostMapping("/signup")
	public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
		// by default, a new user has 'user' privilege
		user.setRole_id(2);
		// checks if there is any field is invalid
		if (result.hasErrors()) {
			return "signuppage";
		}
		User existingByEmail = userService.findByEmail(user.getEmail());
		User existingByUserName = userService.findByusername(user.getUsername());
		if (existingByEmail != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
			return "signuppage";
		}else if(existingByUserName!=null) {
			result.rejectValue("username", null, "There is already an account registered with that username");
			return "signuppage";
		}
		

		userService.saveUser(user);
		model.addAttribute("user", user);

		return "redirect:/login";
	}

}
