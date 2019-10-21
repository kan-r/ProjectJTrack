/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.JobStage;
import com.jtrack.model.Users;
import com.jtrack.service.JobStageService;
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
public class JobStageController {
    
    @Resource
    private JobStageService jobStageService;
    
    @RequestMapping(value="/jobStage", method = RequestMethod.GET)
    public ModelAndView jobStage(){
        return new ModelAndView("jobStage", "jobStageList", jobStageService.getAll());
    }
    
    @RequestMapping(value="/jobStageCreate", method = RequestMethod.GET)
    public ModelAndView jobStageCreate(Model model){
        return new ModelAndView("jobStageCreate", "command", new JobStage());
    }
    
    @RequestMapping(value="/jobStageCreate", method = RequestMethod.POST)
    public String jobStageAdd(@ModelAttribute("jobStageCreate") JobStage jobStage){
        jobStageService.add(jobStage);
        return "redirect:jobStage.htm";
    }
    
    @RequestMapping(value="/jobStageEdit", method = RequestMethod.GET)
    public ModelAndView jobStageEdit(@RequestParam(value="id", required=true) String jobStage, Model model){
        return new ModelAndView("jobStageEdit", "command", jobStageService.get(jobStage));
    }
    
    @RequestMapping(value="/jobStageEdit", method = RequestMethod.POST)
    public String jobStageUpdate(@ModelAttribute("jobStageEdit") JobStage jobStage){
        jobStageService.update(jobStage);
        return "redirect:jobStage.htm";
    }
    
    @RequestMapping(value="/jobStageDelete", method = RequestMethod.GET)
    public String jobStageDelete(@RequestParam(value="id", required=true) String jobStage, Model model){
        jobStageService.delete(jobStage);
        return "redirect:jobStage.htm";
    }
    
    @ModelAttribute("currentUser")
    public Users currentUser(){
        return UsersService.user;
    }
}