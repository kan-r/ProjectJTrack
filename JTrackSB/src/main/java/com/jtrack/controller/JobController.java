package com.jtrack.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.Job;
import com.jtrack.model.JobPriority;
import com.jtrack.model.JobResolution;
import com.jtrack.model.JobSO;
import com.jtrack.model.JobStage;
import com.jtrack.model.JobStatus;
import com.jtrack.model.JobType;
import com.jtrack.model.TimesheetCode;
import com.jtrack.model.User;
import com.jtrack.service.JobPriorityService;
import com.jtrack.service.JobResolutionService;
import com.jtrack.service.JobService;
import com.jtrack.service.JobStageService;
import com.jtrack.service.JobStatusService;
import com.jtrack.service.JobTypeService;
import com.jtrack.service.TimesheetCodeService;
import com.jtrack.service.UserService;

@Controller
public class JobController {
    
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
    private UserService userService;
    
    @Resource
    private TimesheetCodeService timesheetCodeService;
    
    @GetMapping("/job")
    public ModelAndView job(String error){
        
    	JobSO jobSO = new JobSO();
    	
        ModelAndView modelAndView = new ModelAndView("job", "command", jobSO);
        
        modelAndView.addObject("jobList", jobService.getJobList(jobSO));
        modelAndView.addObject("error", error);
        
        return modelAndView;
    }
    
    @PostMapping("/job")
    public ModelAndView job(@ModelAttribute("job") JobSO jobSO){
        ModelAndView modelAndView = new ModelAndView("job", "command", jobSO);
        modelAndView.addObject("jobList", jobService.getJobList(jobSO));
        return modelAndView;
    }
    
    @GetMapping("/jobCreate")
    public ModelAndView jobCreate(Model model, String error){
    	ModelAndView modelAndView = new ModelAndView("jobCreate", "command", new Job());
    	modelAndView.addObject("error", error);
        return modelAndView;
    }
    
    @PostMapping("/jobCreate")
    public String jobAdd(@ModelAttribute("jobCreate") Job job){
    	
        try {
			jobService.addJob(job);
		} catch (InvalidDataException e) {
			return "redirect:jobCreate?error=" + e.getMessage();
		}
        
        return "redirect:job";
    }
    
    @GetMapping("/jobEdit")
    public ModelAndView jobEdit(@RequestParam(value="id", required=true) long jobNo, Model model){
        return new ModelAndView("jobEdit", "command", jobService.getJob(jobNo));
    }
    
    @PostMapping("/jobEdit")
    public String jobUpdate(@ModelAttribute("jobEdit") Job job){
        jobService.updateJob(job);
        return "redirect:job";
    }
    
    @GetMapping("/jobDelete")
    public String jobDelete(@RequestParam(value="id", required=true) long jobNo, Model model){
    	
        try {
			jobService.deleteJob(jobNo);
		} catch (InvalidDataException e) {
			return "redirect:job?error=" + e.getMessage();
		}
        
        return "redirect:job";
    }
    
    @ModelAttribute("jobTypeList")
    public List<JobType> jobTypeList(){
        return jobTypeService.getJobTypeList();
    }
    
    @ModelAttribute("jobStageList")
    public List<JobStage> jobStageList(){
        return jobStageService.getJobStageList();
    }
    
    @ModelAttribute("jobPriorityList")
    public List<JobPriority> jobPriorityList(){
        return jobPriorityService.getJobPriorityList();
    }
    
    @ModelAttribute("jobStatusList")
    public List<JobStatus> jobStatusList(){
        return jobStatusService.getJobStatusList();
    }
    
    @ModelAttribute("jobResolutionList")
    public List<JobResolution> jobResolutionList(){
        return jobResolutionService.getJobResolutionList();
    }
    
    @ModelAttribute("assignedToList")
    public List<User> assignedToList(){
        return userService.getUserList();
    }
    
    @ModelAttribute("timesheetCodeList")
    public List<TimesheetCode> timesheetCodeList(){
        return timesheetCodeService.getTimesheetCodeList();
    }
    
    @ModelAttribute("projectList")
    public List<Job> projectList(){
        return jobService.getJobList("Project");
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return userService.getCurrentUser();
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
