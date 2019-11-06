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

import com.jtrack.model.Job;
import com.jtrack.model.JobPriority;
import com.jtrack.model.JobResolution;
import com.jtrack.model.JobSearchObj;
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
    private UserService userService;
    
    @Resource
    private TimesheetCodeService timesheetCodeService;
    
    @GetMapping("/job")
    public ModelAndView job(){
        
        ModelAndView modelAndView = new ModelAndView("job", "command", new JobSearchObj());
        modelAndView.addObject("jobList", jobService.getJobPage(0));
        numberOfPages = jobService.getNumberOfPages();
        modelAndView.addObject("pageNumber", 1);
        modelAndView.addObject("numberOfPages", numberOfPages);
        return modelAndView;
    }
    
    @GetMapping("/jobPage")
    public ModelAndView jobPage(@ModelAttribute("job") JobSearchObj jobSO, @RequestParam(value="pageNumber", required=true) int pageNumber, Model model){
        
        ModelAndView modelAndView = new ModelAndView("job", "command", jobSO);
        modelAndView.addObject("jobList", jobService.getJobPage(jobSO, pageNumber-1));
        numberOfPages = jobService.getNumberOfPages();
        modelAndView.addObject("pageNumber", pageNumber);
        modelAndView.addObject("numberOfPages", numberOfPages);
        return modelAndView;
    }
    
    @PostMapping("/job")
    public ModelAndView job(@ModelAttribute("job") JobSearchObj jobSO){
        ModelAndView modelAndView = new ModelAndView("job", "command", jobSO);
        modelAndView.addObject("jobList", jobService.getJobPage(jobSO, 0));
        numberOfPages = jobService.getNumberOfPages();
        modelAndView.addObject("pageNumber", 1);
        modelAndView.addObject("numberOfPages", numberOfPages);
        return modelAndView;
    }
    
    @GetMapping("/jobCreate")
    public ModelAndView jobCreate(Model model){
        return new ModelAndView("jobCreate", "command", new Job());
    }
    
    @PostMapping("/jobCreate")
    public String jobAdd(@ModelAttribute("jobCreate") Job job){
        jobService.addJob(job);
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
        jobService.deleteJob(jobNo);
        return "redirect:job";
    }
    
    @ModelAttribute("jobTypeList")
    public List<JobType> jobTypeList(){
        return jobTypeService.getJobTypeAll();
    }
    
    @ModelAttribute("jobStageList")
    public List<JobStage> jobStageList(){
        return jobStageService.getJobStageAll();
    }
    
    @ModelAttribute("jobPriorityList")
    public List<JobPriority> jobPriorityList(){
        return jobPriorityService.getJobPriorityAll();
    }
    
    @ModelAttribute("jobStatusList")
    public List<JobStatus> jobStatusList(){
        return jobStatusService.getJobStatusAll();
    }
    
    @ModelAttribute("jobResolutionList")
    public List<JobResolution> jobResolutionList(){
        return jobResolutionService.getJobResolutionAll();
    }
    
    @ModelAttribute("assignedToList")
    public List<User> assignedToList(){
        return userService.getUserAll();
    }
    
    @ModelAttribute("timesheetCodeList")
    public List<TimesheetCode> timesheetCodeList(){
        return timesheetCodeService.getTimesheetCodeAll();
    }
    
    @ModelAttribute("projectList")
    public List<Job> projectList(){
        return jobService.getJob("Project");
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return UserService.currentUser;
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
