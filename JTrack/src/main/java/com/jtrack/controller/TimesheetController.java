/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.Timesheet;
import com.jtrack.model.TimesheetSO;
import com.jtrack.model.Users;
import com.jtrack.service.JobService;
import com.jtrack.service.TimesheetCodeService;
import com.jtrack.service.TimesheetService;
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
public class TimesheetController {
    
    @Resource
    private TimesheetService timesheetService;
    
    @Resource
    private UsersService usersService;
    
    @Resource
    private JobService jobService;
    
    @Resource
    private TimesheetCodeService timesheetCodeService;
    
    @RequestMapping(value="/timesheet", method = RequestMethod.GET)
    public ModelAndView timesheet(){
        TimesheetSO timesheetSO = new TimesheetSO();
        timesheetSO.setUserId(usersService.user.getUserId());
        timesheetSO.setWorkedDateFrom(new Date());
        timesheetSO.setWorkedDateTo(new Date());
        
        ModelAndView modelAndView = new ModelAndView("timesheet", "command", timesheetSO);
        modelAndView.addObject("timesheetList", timesheetService.getAll());
        return modelAndView;
    }
    
    @RequestMapping(value="/timesheet", method = RequestMethod.POST)
    public ModelAndView timesheet(@ModelAttribute("timesheet") TimesheetSO timesheetSO){    
        ModelAndView modelAndView = new ModelAndView("timesheet", "command", timesheetSO);
        modelAndView.addObject("timesheetList", timesheetService.getAll(timesheetSO.getUserId(), timesheetSO.getWorkedDateFrom(), timesheetSO.getWorkedDateTo()));
        return modelAndView;
    }
    
    @RequestMapping(value="/timesheetCreate", method = RequestMethod.GET)
    public ModelAndView timesheetCreate(Model model){
        Timesheet timesheet = new Timesheet();
        timesheet.setUserId(usersService.user.getUserId());
        timesheet.setWorkedDate(new Date());
        return new ModelAndView("timesheetCreate", "command", timesheet);
    }
    
    @RequestMapping(value="/timesheetCreate", method = RequestMethod.POST)
    public String timesheetAdd(@ModelAttribute("timesheetCreate") Timesheet timesheet){
        timesheetService.add(timesheet);
        return "redirect:timesheet.htm";
    }
    
    @RequestMapping(value="/timesheetEdit", method = RequestMethod.GET)
    public ModelAndView timesheetEdit(@RequestParam(value="id", required=true) String timesheetId, Model model){
        return new ModelAndView("timesheetEdit", "command", timesheetService.get(timesheetId));
    }
    
    @RequestMapping(value="/timesheetEdit", method = RequestMethod.POST)
    public String timesheetUpdate(@ModelAttribute("timesheetEdit") Timesheet timesheet){
        timesheetService.update(timesheet);
        return "redirect:timesheet.htm";
    }
    
    @RequestMapping(value="/timesheetForJob", method = RequestMethod.GET)
    public ModelAndView timesheetForJob(@RequestParam(value="id", required=true) Long jobNo, Model model){
        Timesheet timesheet = timesheetService.get(usersService.user.getUserId(), jobNo, new Date());
        if(timesheet == null){
            timesheet = new Timesheet();
            timesheet.setUserId(usersService.user.getUserId());
            timesheet.setJobNo(jobNo);
            timesheet.setWorkedDate(new Date());
            timesheet.setTimesheetCode(jobService.get(jobNo).getTimesheetCode());
            return new ModelAndView("timesheetCreate", "command", timesheet);
        }else{
            return new ModelAndView("timesheetEdit", "command", timesheet);
        }
    }
    
    @RequestMapping(value="/timesheetDelete", method = RequestMethod.GET)
    public String timesheetDelete(@RequestParam(value="id", required=true) String timesheetId, Model model){
        timesheetService.delete(timesheetId);
        return "redirect:timesheet.htm";
    }
    
    @ModelAttribute("userList")
    public List userList(){
        return usersService.getAll();
    }
    
    @ModelAttribute("jobList")
    public List jobList(){
        return jobService.getAll();
    }
    
    @ModelAttribute("timesheetCodeList")
    public List timesheetCodeList(){
        return timesheetCodeService.getAll();
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
