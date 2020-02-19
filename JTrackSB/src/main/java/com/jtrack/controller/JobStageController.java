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
import com.jtrack.model.JobStage;
import com.jtrack.model.User;
import com.jtrack.service.JobStageService;
import com.jtrack.service.UserService;

@Controller
public class JobStageController {
	
	@Resource
    private JobStageService jobStageService;
	
	@Resource
	private UserService userService;
    
    @GetMapping("/jobStage")
    public ModelAndView jobStage(){
        return new ModelAndView("jobStage", "jobStageList", jobStageService.getJobStageList());
    }
    
    @GetMapping("/jobStageCreate")
    public ModelAndView jobStageCreate(Model model, String error){
    	ModelAndView modelAndView = new ModelAndView("jobStageCreate", "command", new JobStage());
    	modelAndView.addObject("error", error);
        return modelAndView;
    }
    
    @PostMapping("/jobStageCreate")
    public String jobStageAdd(@ModelAttribute("jobStageCreate") JobStage jobStage){
    	
        try {
			jobStageService.addJobStage(jobStage);
		} catch (InvalidDataException e) {
			return "redirect:jobStageCreate?error=" + e.getMessage();
		}
        
        return "redirect:jobStage";
    }
    
    @GetMapping("/jobStageEdit")
    public ModelAndView jobStageEdit(@RequestParam(value="id", required=true) String jobStageId, Model model){
        return new ModelAndView("jobStageEdit", "command", jobStageService.getJobStage(jobStageId));
    }
    
    @PostMapping("/jobStageEdit")
    public String jobStageUpdate(@ModelAttribute("jobStageEdit") JobStage jobStage){
        jobStageService.updateJobStage(jobStage);
        return "redirect:jobStage";
    }
    
    @GetMapping("/jobStageDelete")
    public String jobStageDelete(@RequestParam(value="id", required=true) String jobStageId, Model model){
        jobStageService.deleteJobStage(jobStageId);
        return "redirect:jobStage";
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return userService.getCurrentUser();
    }

}
