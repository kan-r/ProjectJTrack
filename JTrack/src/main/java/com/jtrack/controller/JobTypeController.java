/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.JobType;
import com.jtrack.model.Users;
import com.jtrack.service.JobTypeService;
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
public class JobTypeController {
    
    @Resource
    private JobTypeService jobTypeService;
    
    @RequestMapping(value="/jobType", method = RequestMethod.GET)
    public ModelAndView jobType(){
        return new ModelAndView("jobType", "jobTypeList", jobTypeService.getAll());
    }
    
    @RequestMapping(value="/jobTypeCreate", method = RequestMethod.GET)
    public ModelAndView jobTypeCreate(Model model){
        return new ModelAndView("jobTypeCreate", "command", new JobType());
    }
    
    @RequestMapping(value="/jobTypeCreate", method = RequestMethod.POST)
    public String jobTypeAdd(@ModelAttribute("jobTypeCreate") JobType jobType){
        jobTypeService.add(jobType);
        return "redirect:jobType.htm";
    }
    
    @RequestMapping(value="/jobTypeEdit", method = RequestMethod.GET)
    public ModelAndView jobTypeEdit(@RequestParam(value="id", required=true) String jobType, Model model){
        return new ModelAndView("jobTypeEdit", "command", jobTypeService.get(jobType));
    }
    
    @RequestMapping(value="/jobTypeEdit", method = RequestMethod.POST)
    public String jobTypeUpdate(@ModelAttribute("jobTypeEdit") JobType jobType){
        jobTypeService.update(jobType);
        return "redirect:jobType.htm";
    }
    
    @RequestMapping(value="/jobTypeDelete", method = RequestMethod.GET)
    public String jobTypeDelete(@RequestParam(value="id", required=true) String jobType, Model model){
        jobTypeService.delete(jobType);
        return "redirect:jobType.htm";
    }
    
    @ModelAttribute("currentUser")
    public Users currentUser(){
        return UsersService.user;
    }
}
