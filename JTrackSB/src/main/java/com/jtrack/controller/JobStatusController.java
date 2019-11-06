package com.jtrack.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jtrack.model.JobStatus;
import com.jtrack.model.User;
import com.jtrack.service.JobStatusService;
import com.jtrack.service.UserService;

@Controller
public class JobStatusController {
	
	@Resource
    private JobStatusService jobStatusService;
    
    @GetMapping("/jobStatus")
    public ModelAndView jobStatus(){
        return new ModelAndView("jobStatus", "jobStatusList", jobStatusService.getJobStatusAll());
    }
    
    @GetMapping("/jobStatusCreate")
    public ModelAndView jobStatusCreate(Model model){
        return new ModelAndView("jobStatusCreate", "command", new JobStatus());
    }
    
    @PostMapping("/jobStatusCreate")
    public String jobStatusAdd(@ModelAttribute("jobStatusCreate") JobStatus jobStatus){
        jobStatusService.addJobStatus(jobStatus);
        return "redirect:jobStatus";
    }
    
    @GetMapping("/jobStatusEdit")
    public ModelAndView jobStatusEdit(@RequestParam(value="id", required=true) String jobStatusId, Model model){
        return new ModelAndView("jobStatusEdit", "command", jobStatusService.getJobStatus(jobStatusId));
    }
    
    @PostMapping("/jobStatusEdit")
    public String jobStatusUpdate(@ModelAttribute("jobStatusEdit") JobStatus jobStatus){
        jobStatusService.updateJobStatus(jobStatus);
        return "redirect:jobStatus";
    }
    
    @GetMapping("/jobStatusDelete")
    public String jobStatusDelete(@RequestParam(value="id", required=true) String jobStatus, Model model){
        jobStatusService.deleteJobStatus(jobStatus);
        return "redirect:jobStatus";
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return UserService.currentUser;
    }

}
