package com.jtrack.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.User;
import com.jtrack.service.UserService;

@Controller
public class UsersController {
	
	Logger logger = LogManager.getLogger(UsersController.class);

	@Resource
    private UserService userService;
    
    @GetMapping(value="/users")
    public ModelAndView users(){
    	logger.info("/users");
        return new ModelAndView("users", "usersList", userService.getUserList());
    }
    
    @GetMapping(value="/usersNoAccess")
    public ModelAndView usersNoAccess(String error){
    	logger.info("/usersNoAccess");
    	ModelAndView modelAndView = new ModelAndView("usersNoAccess");
    	modelAndView.addObject("error", error);
        return modelAndView;
    }
    
    @GetMapping(value="/usersCreate")
    public ModelAndView usersCreate(Model model, String error){
    	logger.info("/usersCreate");
    	
    	if(!userService.isCurrentUserAdmin()) {
    		return usersNoAccess("No acess to Create User");
    	}
    	
    	ModelAndView modelAndView = new ModelAndView("usersCreate", "command", new User());
    	modelAndView.addObject("error", error);
        return modelAndView;
    }
    
    @PostMapping(value="/usersCreate")
    public String usersAdd(@ModelAttribute("usersCreate") User user){
    	logger.info("/usersCreate-Post");
    	
    	if(!userService.isCurrentUserAdmin()) {
    		return "redirect:usersNoAccess?error=No acess to Create User";
    	}
    	
        try {
			userService.addUser(user);
		} catch (InvalidDataException e) {
			return "redirect:usersCreate?error=" + e.getMessage();
		}
        
        return "redirect:users";
    }
    
    @GetMapping(value="/usersEdit")
    public ModelAndView usersEdit(@RequestParam(value="id", required=true) String users, Model model){
    	logger.info("/usersEdit");
    	
    	if(!userService.isCurrentUserAdmin()) {
    		return usersNoAccess("No acess to Edit User");
    	}
    	
        return new ModelAndView("usersEdit", "command", userService.getUser(users));
    }
    
    @PostMapping(value="/usersEdit")
    public String usersUpdate(@ModelAttribute("usersEdit") User user){
    	logger.info("/usersEdit-Post");
    	
    	if(!userService.isCurrentUserAdmin()) {
    		return "redirect:usersNoAccess?error=No acess to Edit User";
    	}
    	
        userService.updateUser(user);
        return "redirect:users";
    }
    
    @GetMapping(value="/usersDelete")
    public String usersDelete(@RequestParam(value="id", required=true) String userId, Model model){
    	logger.info("/usersDelete");
    	
    	if(!userService.isCurrentUserAdmin()) {
    		return "redirect:usersNoAccess?error=No acess to Delete User";
    	}
    	
        userService.deleteUser(userId);
        return "redirect:users";
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return userService.getCurrentUser();
    }
}
