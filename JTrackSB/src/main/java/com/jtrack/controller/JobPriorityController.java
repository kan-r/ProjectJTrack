package com.jtrack.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jtrack.model.JobPriority;
import com.jtrack.model.User;
import com.jtrack.service.JobPriorityService;
import com.jtrack.service.UserService;

@Controller
public class JobPriorityController {
	
	@Resource
    private JobPriorityService jobPriorityService;
    
    @GetMapping("/jobPriority")
    public ModelAndView jobPriority(){
        return new ModelAndView("jobPriority", "jobPriorityList", jobPriorityService.getJobPriorityAll());
    }
    
    @GetMapping("/jobPriorityCreate")
    public ModelAndView jobPriorityCreate(Model model){
        return new ModelAndView("jobPriorityCreate", "command", new JobPriority());
    }
    
    @PostMapping("/jobPriorityCreate")
    public String jobPriorityAdd(@ModelAttribute("jobPriorityCreate") JobPriority jobPriority){
        jobPriorityService.addJobPriority(jobPriority);
        return "redirect:jobPriority";
    }
    
    @GetMapping("/jobPriorityEdit")
    public ModelAndView jobPriorityEdit(@RequestParam(value="id", required=true) String jobPriorityId, Model model){
        return new ModelAndView("jobPriorityEdit", "command", jobPriorityService.getJobPriority(jobPriorityId));
    }
    
    @PostMapping("/jobPriorityEdit")
    public String jobPriorityUpdate(@ModelAttribute("jobPriorityEdit") JobPriority jobPriority){
        jobPriorityService.updateJobPriority(jobPriority);
        return "redirect:jobPriority";
    }
    
    @GetMapping("/jobPriorityDelete")
    public String jobPriorityDelete(@RequestParam(value="id", required=true) String jobPriorityId, Model model){
        jobPriorityService.deleteJobPriority(jobPriorityId);
        return "redirect:jobPriority";
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return UserService.currentUser;
    }

}
