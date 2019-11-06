package com.jtrack.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	Logger logger = LogManager.getLogger(LoginController.class);

	@GetMapping(path="/")
	public String home() {
		 return "redirect:login";
	}
	
	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		logger.info("/login");
        return "login";
    }
	
	@GetMapping("/loginFailure")
    public ModelAndView invalidLogin() {
		logger.info("/loginFailure");
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("error", true);
        return modelAndView;
    }
	
	@GetMapping("/loginSuccess")
    public String successLogin(Model model) {
		logger.info("/loginSuccess");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        return "redirect:job";
    }
}
