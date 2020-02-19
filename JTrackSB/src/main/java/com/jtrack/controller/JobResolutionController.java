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
import com.jtrack.model.JobResolution;
import com.jtrack.model.User;
import com.jtrack.service.JobResolutionService;
import com.jtrack.service.UserService;

@Controller
public class JobResolutionController {
	
	@Resource
    private JobResolutionService jobResolutionService;
	
	@Resource
	private UserService userService;
    
    @GetMapping("/jobResolution")
    public ModelAndView jobResolution(){
        return new ModelAndView("jobResolution", "jobResolutionList", jobResolutionService.getJobResolutionList());
    }
    
    @GetMapping("/jobResolutionCreate")
    public ModelAndView jobResolutionCreate(Model model, String error){
    	ModelAndView modelAndView = new ModelAndView("jobResolutionCreate", "command", new JobResolution());
    	modelAndView.addObject("error", error);
        return modelAndView;
    }
    
    @PostMapping("/jobResolutionCreate")
    public String jobResolutionAdd(@ModelAttribute("jobResolutionCreate") JobResolution jobResolution){
    	
        try {
			jobResolutionService.addJobResolution(jobResolution);
		} catch (InvalidDataException e) {
			return "redirect:jobResolutionCreate?error=" + e.getMessage();
		}
        
        return "redirect:jobResolution";
    }
    
    @GetMapping("/jobResolutionEdit")
    public ModelAndView jobResolutionEdit(@RequestParam(value="id", required=true) String jobResolutionId, Model model){
        return new ModelAndView("jobResolutionEdit", "command", jobResolutionService.getJobResolution(jobResolutionId));
    }
    
    @PostMapping("/jobResolutionEdit")
    public String jobResolutionUpdate(@ModelAttribute("jobResolutionEdit") JobResolution jobResolution){
        jobResolutionService.updateJobResolution(jobResolution);
        return "redirect:jobResolution";
    }
    
    @GetMapping("/jobResolutionDelete")
    public String jobResolutionDelete(@RequestParam(value="id", required=true) String jobResolutionId, Model model){
        jobResolutionService.deleteJobResolution(jobResolutionId);
        return "redirect:jobResolution";
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return userService.getCurrentUser();
    }

}
