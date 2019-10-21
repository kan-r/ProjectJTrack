/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.Job;
import com.jtrack.model.JobSO;
import com.jtrack.model.Users;
import com.jtrack.service.JobPriorityService;
import com.jtrack.service.JobResolutionService;
import com.jtrack.service.JobService;
import com.jtrack.service.JobStageService;
import com.jtrack.service.JobStatusService;
import com.jtrack.service.JobTypeService;
import com.jtrack.service.TimesheetCodeService;
import com.jtrack.service.UsersService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
public class JobController {
    
    private static int numberOfPages;
    
    @Resource
    private JobService jobService;
    
    @Resource
    private JobTypeService jobTypeService;
    
    @Resource
    private JobStageService jobStageService;
    
    @Resource
    private JobPriorityService jobPriorityService;
    
    @Resource
    private JobStatusService jobStatusService;
    
    @Resource
    private JobResolutionService jobResolutionService;
    
    @Resource
    private UsersService usersService;
    
    @Resource
    private TimesheetCodeService timesheetCodeService;
    
    
    @RequestMapping(value="/job", method = RequestMethod.GET)
    public ModelAndView job(){
//        return new ModelAndView("job", "jobList", jobService.getAll());
        numberOfPages = jobService.getNumberOfPages();
       
        ModelAndView modelAndView = new ModelAndView("job", "command", new JobSO());
        modelAndView.addObject("jobList", jobService.getPage(1));
        modelAndView.addObject("pageNumber", 1);
        modelAndView.addObject("numberOfPages", numberOfPages);
        return modelAndView;
        
//        ModelAndView modelAndView = new ModelAndView("job", "jobList", jobService.getPage(1));
//        modelAndView.addObject("pageNumber", 1);
//        modelAndView.addObject("numberOfPages", numberOfPages);
//        return modelAndView;
    }
    
    @RequestMapping(value="/job", method = RequestMethod.POST)
    public ModelAndView job(@ModelAttribute("job") JobSO jobSO){
        numberOfPages = jobService.getNumberOfPages();
        
        ModelAndView modelAndView = new ModelAndView("job", "command", jobSO);
        modelAndView.addObject("jobList", jobService.getPage(jobSO, 1));
        modelAndView.addObject("pageNumber", 1);
        modelAndView.addObject("numberOfPages", numberOfPages);
        return modelAndView;
    }
    
    @RequestMapping(value="/jobPage", method = RequestMethod.GET)
    public ModelAndView jobPage(@ModelAttribute("job") JobSO jobSO, @RequestParam(value="pageNumber", required=true) int pageNumber, Model model){
        
//        if(jobSO == null){
//            jobSO = new JobSO();
//        }
        
        ModelAndView modelAndView = new ModelAndView("job", "command", jobSO);
        modelAndView.addObject("jobList", jobService.getPage(jobSO, pageNumber));
//        ModelAndView modelAndView = new ModelAndView("job", "jobList", jobService.getPage(pageNumber));
        modelAndView.addObject("pageNumber", pageNumber);
        modelAndView.addObject("numberOfPages", numberOfPages);
        return modelAndView;
    }
    
    @RequestMapping(value="/jobCreate", method = RequestMethod.GET)
    public ModelAndView jobCreate(Model model){
        return new ModelAndView("jobCreate", "command", new Job());
    }
    
    @RequestMapping(value="/jobCreate", method = RequestMethod.POST)
    public String jobAdd(@ModelAttribute("jobCreate") Job job){
        jobService.add(job);
        return "redirect:job.htm";
    }
    
    @RequestMapping(value="/jobEdit", method = RequestMethod.GET)
    public ModelAndView jobEdit(@RequestParam(value="id", required=true) long jobNo, Model model){
        return new ModelAndView("jobEdit", "command", jobService.get(jobNo));
    }
    
    @RequestMapping(value="/jobEdit", method = RequestMethod.POST)
    public String jobUpdate(@ModelAttribute("jobEdit") Job job){
        jobService.update(job);
        return "redirect:job.htm";
    }
    
    @RequestMapping(value="/jobDelete", method = RequestMethod.GET)
    public String jobDelete(@RequestParam(value="id", required=true) long jobNo, Model model){
        jobService.delete(jobNo);
        return "redirect:job.htm";
    }
    
    @ModelAttribute("jobTypeList")
    public List jobTypeList(){
        return jobTypeService.getAll();
    }
    
    @ModelAttribute("jobStageList")
    public List jobStageList(){
        return jobStageService.getAll();
    }
    
    @ModelAttribute("jobPriorityList")
    public List jobPriorityList(){
        return jobPriorityService.getAll();
    }
    
    @ModelAttribute("jobStatusList")
    public List jobStatusList(){
        return jobStatusService.getAll();
    }
    
    @ModelAttribute("jobResolutionList")
    public List jobResolutionList(){
        return jobResolutionService.getAll();
    }
    
    @ModelAttribute("assignedToList")
    public List assignedToList(){
        return usersService.getAll();
    }
    
    @ModelAttribute("timesheetCodeList")
    public List timesheetCodeList(){
        return timesheetCodeService.getAll();
    }
    
    @ModelAttribute("projectList")
    public List projectList(){
        return jobService.get("Project");
    }
    
    @ModelAttribute("currentUser")
    public Users currentUser(){
        return UsersService.user;
    }
    
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }
}