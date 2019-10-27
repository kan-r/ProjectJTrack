/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.Users;
import com.jtrack.service.UsersService;
import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Kan
 */
@Controller
public class UsersController {
    
    @Resource
    private UsersService usersService;
    
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ModelAndView users(){
        return new ModelAndView("users", "usersList", usersService.getAll());
    }
    
    @RequestMapping(value="/usersCreate", method = RequestMethod.GET)
    public ModelAndView usersCreate(Model model){
        return new ModelAndView("usersCreate", "command", new Users());
    }
    
    @RequestMapping(value="/usersCreate", method = RequestMethod.POST)
    public String usersAdd(@ModelAttribute("usersCreate") Users users){
        usersService.add(users);
        return "redirect:users.htm";
    }
    
    @RequestMapping(value="/usersEdit", method = RequestMethod.GET)
    public ModelAndView usersEdit(@RequestParam(value="id", required=true) String users, Model model){
        return new ModelAndView("usersEdit", "command", usersService.get(users));
    }
    
    @RequestMapping(value="/usersEdit", method = RequestMethod.POST)
    public String usersUpdate(@ModelAttribute("usersEdit") Users users){
        usersService.update(users);
        return "redirect:users.htm";
    }
    
    @RequestMapping(value="/usersDelete", method = RequestMethod.GET)
    public String usersDelete(@RequestParam(value="id", required=true) String users, Model model){
        usersService.delete(users);
        return "redirect:users.htm";
    }
    
    @ModelAttribute("currentUser")
    public Users currentUser(){
        return UsersService.user;
    }
}