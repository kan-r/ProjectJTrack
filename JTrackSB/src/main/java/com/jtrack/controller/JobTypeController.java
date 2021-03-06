package com.jtrack.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.JobType;
import com.jtrack.model.User;
import com.jtrack.service.JobTypeService;
import com.jtrack.service.UserService;

@Controller
public class JobTypeController {

	@Resource
    private JobTypeService jobTypeService;
	
	@Resource
	private UserService userService;
    
    @GetMapping("/jobType")
    public ModelAndView jobType(){
        return new ModelAndView("jobType", "jobTypeList", jobTypeService.getJobTypeList());
    }
    
    @GetMapping("/jobTypeCreate")
    public ModelAndView jobTypeCreate(Model model, String error){
    	ModelAndView modelAndView = new ModelAndView("jobTypeCreate", "command", new JobType());
    	modelAndView.addObject("error", error);
        return modelAndView;
    }
    
    @PostMapping("/jobTypeCreate")
    public String jobTypeAdd(@ModelAttribute("jobTypeCreate") JobType jobType){
    	
        try {
			jobTypeService.addJobType(jobType);
		} catch (InvalidDataException e) {
			return "redirect:jobTypeCreate?error=" + e.getMessage();
		}
        
        return "redirect:jobType";
    }
    
    @GetMapping("/jobTypeEdit")
    public ModelAndView jobTypeEdit(@RequestParam(value="id", required=true) String jobTypeId, Model model){
        return new ModelAndView("jobTypeEdit", "command", jobTypeService.getJobType(jobTypeId));
    }
    
    @PostMapping("/jobTypeEdit")
    public String jobTypeUpdate(@ModelAttribute("jobTypeEdit") JobType jobType){
        jobTypeService.updateJobType(jobType);
        return "redirect:jobType";
    }
    
    @GetMapping("/jobTypeDelete")
    public String jobTypeDelete(@RequestParam(value="id", required=true) String jobTypeId, Model model){
        jobTypeService.deleteJobType(jobTypeId);
        return "redirect:jobType";
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return userService.getCurrentUser();
    }
}
