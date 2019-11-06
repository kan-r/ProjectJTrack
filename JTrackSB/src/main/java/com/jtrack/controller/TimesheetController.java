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

import com.jtrack.model.Timesheet;
import com.jtrack.model.TimesheetSearchObj;
import com.jtrack.model.User;
import com.jtrack.service.JobService;
import com.jtrack.service.TimesheetCodeService;
import com.jtrack.service.TimesheetService;
import com.jtrack.service.UserService;

@Controller
public class TimesheetController {

	@Resource
    private TimesheetService timesheetService;
    
    @Resource
    private UserService userService;
    
    @Resource
    private JobService jobService;
    
    @Resource
    private TimesheetCodeService timesheetCodeService;
    
    @GetMapping("/timesheet")
    public ModelAndView timesheet(){
        TimesheetSearchObj timesheetSO = new TimesheetSearchObj();
        timesheetSO.setUserId(userService.currentUser.getUserId());
        timesheetSO.setWorkedDateFrom(new Date());
        timesheetSO.setWorkedDateTo(new Date());
        
        ModelAndView modelAndView = new ModelAndView("timesheet", "command", timesheetSO);
        modelAndView.addObject("timesheetList", timesheetService.getTimesheetAll());
        return modelAndView;
    }
    
    @PostMapping("/timesheet")
    public ModelAndView timesheet(@ModelAttribute("timesheet") TimesheetSearchObj timesheetSO){    
        ModelAndView modelAndView = new ModelAndView("timesheet", "command", timesheetSO);
        modelAndView.addObject("timesheetList", timesheetService.getTimesheetAll(timesheetSO.getUserId(), timesheetSO.getWorkedDateFrom(), timesheetSO.getWorkedDateTo()));
        return modelAndView;
    }
    
    @GetMapping(value="/timesheetCreate")
    public ModelAndView timesheetCreate(Model model){
        Timesheet timesheet = new Timesheet();
        timesheet.setUserId(userService.currentUser.getUserId());
        timesheet.setWorkedDate(new Date());
        return new ModelAndView("timesheetCreate", "command", timesheet);
    }
    
    @PostMapping("/timesheetCreate")
    public String timesheetAdd(@ModelAttribute("timesheetCreate") Timesheet timesheet){
        timesheetService.addTimesheet(timesheet);
        return "redirect:timesheet";
    }
    
    @GetMapping("/timesheetEdit")
    public ModelAndView timesheetEdit(@RequestParam(value="id", required=true) String timesheetId, Model model){
        return new ModelAndView("timesheetEdit", "command", timesheetService.getTimesheet(timesheetId));
    }
    
    @PostMapping("/timesheetEdit")
    public String timesheetUpdate(@ModelAttribute("timesheetEdit") Timesheet timesheet){
        timesheetService.updateTimesheet(timesheet);
        return "redirect:timesheet";
    }
    
    @GetMapping("/timesheetForJob")
    public ModelAndView timesheetForJob(@RequestParam(value="id", required=true) Long jobNo, Model model){
        Timesheet timesheet = timesheetService.getTimesheet(userService.currentUser.getUserId(), jobNo, new Date());
        if(timesheet == null){
            timesheet = new Timesheet();
            timesheet.setUserId(userService.currentUser.getUserId());
            timesheet.setJobNo(jobNo);
            timesheet.setWorkedDate(new Date());
            timesheet.setTimesheetCode(jobService.getJob(jobNo).getTimesheetCode());
            return new ModelAndView("timesheetCreate", "command", timesheet);
        }else{
            return new ModelAndView("timesheetEdit", "command", timesheet);
        }
    }
    
    @GetMapping(value="/timesheetDelete")
    public String timesheetDelete(@RequestParam(value="id", required=true) String timesheetId, Model model){
        timesheetService.deleteTimesheet(timesheetId);
        return "redirect:timesheet";
    }
    
    @ModelAttribute("userList")
    public List userList(){
        return userService.getUserAll();
    }
    
    @ModelAttribute("jobList")
    public List jobList(){
        return jobService.getJobAll();
    }
    
    @ModelAttribute("timesheetCodeList")
    public List timesheetCodeList(){
        return timesheetCodeService.getTimesheetCodeAll();
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
