/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.JobStatus;
import com.jtrack.model.Users;
import com.jtrack.service.JobStatusService;
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
public class JobStatusController {
    
    @Resource
    private JobStatusService jobStatusService;
    
    @RequestMapping(value="/jobStatus", method = RequestMethod.GET)
    public ModelAndView jobStatus(){
        return new ModelAndView("jobStatus", "jobStatusList", jobStatusService.getAll());
    }
    
    @RequestMapping(value="/jobStatusCreate", method = RequestMethod.GET)
    public ModelAndView jobStatusCreate(Model model){
        return new ModelAndView("jobStatusCreate", "command", new JobStatus());
    }
    
    @RequestMapping(value="/jobStatusCreate", method = RequestMethod.POST)
    public String jobStatusAdd(@ModelAttribute("jobStatusCreate") JobStatus jobStatus){
        jobStatusService.add(jobStatus);
        return "redirect:jobStatus.htm";
    }
    
    @RequestMapping(value="/jobStatusEdit", method = RequestMethod.GET)
    public ModelAndView jobStatusEdit(@RequestParam(value="id", required=true) String jobStatus, Model model){
        return new ModelAndView("jobStatusEdit", "command", jobStatusService.get(jobStatus));
    }
    
    @RequestMapping(value="/jobStatusEdit", method = RequestMethod.POST)
    public String jobStatusUpdate(@ModelAttribute("jobStatusEdit") JobStatus jobStatus){
        jobStatusService.update(jobStatus);
        return "redirect:jobStatus.htm";
    }
    
    @RequestMapping(value="/jobStatusDelete", method = RequestMethod.GET)
    public String jobStatusDelete(@RequestParam(value="id", required=true) String jobStatus, Model model){
        jobStatusService.delete(jobStatus);
        return "redirect:jobStatus.htm";
    }
    
    @ModelAttribute("currentUser")
    public Users currentUser(){
        return UsersService.user;
    }
}