/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.JobResolution;
import com.jtrack.model.Users;
import com.jtrack.service.JobResolutionService;
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
public class JobResolutionController {
    
    @Resource
    private JobResolutionService jobResolutionService;
    
    @RequestMapping(value="/jobResolution", method = RequestMethod.GET)
    public ModelAndView jobResolution(){
        return new ModelAndView("jobResolution", "jobResolutionList", jobResolutionService.getAll());
    }
    
    @RequestMapping(value="/jobResolutionCreate", method = RequestMethod.GET)
    public ModelAndView jobResolutionCreate(Model model){
        return new ModelAndView("jobResolutionCreate", "command", new JobResolution());
    }
    
    @RequestMapping(value="/jobResolutionCreate", method = RequestMethod.POST)
    public String jobResolutionAdd(@ModelAttribute("jobResolutionCreate") JobResolution jobResolution){
        jobResolutionService.add(jobResolution);
        return "redirect:jobResolution.htm";
    }
    
    @RequestMapping(value="/jobResolutionEdit", method = RequestMethod.GET)
    public ModelAndView jobResolutionEdit(@RequestParam(value="id", required=true) String jobResolution, Model model){
        return new ModelAndView("jobResolutionEdit", "command", jobResolutionService.get(jobResolution));
    }
    
    @RequestMapping(value="/jobResolutionEdit", method = RequestMethod.POST)
    public String jobResolutionUpdate(@ModelAttribute("jobResolutionEdit") JobResolution jobResolution){
        jobResolutionService.update(jobResolution);
        return "redirect:jobResolution.htm";
    }
    
    @RequestMapping(value="/jobResolutionDelete", method = RequestMethod.GET)
    public String jobResolutionDelete(@RequestParam(value="id", required=true) String jobResolution, Model model){
        jobResolutionService.delete(jobResolution);
        return "redirect:jobResolution.htm";
    }
    
    @ModelAttribute("currentUser")
    public Users currentUser(){
        return UsersService.user;
    }
}