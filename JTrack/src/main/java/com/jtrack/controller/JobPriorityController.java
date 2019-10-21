/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.JobPriority;
import com.jtrack.model.Users;
import com.jtrack.service.JobPriorityService;
import com.jtrack.service.UsersService;
import javax.annotation.Resource;
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
public class JobPriorityController {
    
    @Resource
    private JobPriorityService jobPriorityService;
    
    @RequestMapping(value="/jobPriority", method = RequestMethod.GET)
    public ModelAndView jobPriority(){
        return new ModelAndView("jobPriority", "jobPriorityList", jobPriorityService.getAll());
    }
    
    @RequestMapping(value="/jobPriorityCreate", method = RequestMethod.GET)
    public ModelAndView jobPriorityCreate(Model model){
        return new ModelAndView("jobPriorityCreate", "command", new JobPriority());
    }
    
    @RequestMapping(value="/jobPriorityCreate", method = RequestMethod.POST)
    public String jobPriorityAdd(@ModelAttribute("jobPriorityCreate") JobPriority jobPriority){
        jobPriorityService.add(jobPriority);
        return "redirect:jobPriority.htm";
    }
    
    @RequestMapping(value="/jobPriorityEdit", method = RequestMethod.GET)
    public ModelAndView jobPriorityEdit(@RequestParam(value="id", required=true) String jobPriority, Model model){
        return new ModelAndView("jobPriorityEdit", "command", jobPriorityService.get(jobPriority));
    }
    
    @RequestMapping(value="/jobPriorityEdit", method = RequestMethod.POST)
    public String jobPriorityUpdate(@ModelAttribute("jobPriorityEdit") JobPriority jobPriority){
        jobPriorityService.update(jobPriority);
        return "redirect:jobPriority.htm";
    }
    
    @RequestMapping(value="/jobPriorityDelete", method = RequestMethod.GET)
    public String jobPriorityDelete(@RequestParam(value="id", required=true) String jobPriority, Model model){
        jobPriorityService.delete(jobPriority);
        return "redirect:jobPriority.htm";
    }
    
    @ModelAttribute("currentUser")
    public Users currentUser(){
        return UsersService.user;
    }
}
