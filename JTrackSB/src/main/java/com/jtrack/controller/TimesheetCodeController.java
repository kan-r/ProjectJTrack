package com.jtrack.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.TimesheetCode;
import com.jtrack.model.User;
import com.jtrack.service.TimesheetCodeService;
import com.jtrack.service.UserService;

@Controller
public class TimesheetCodeController {

	@Resource
    private TimesheetCodeService timesheetCodeService;
	
	@Resource
	private UserService userService;
    
    @GetMapping("/timesheetCode")
    public ModelAndView timesheetCode(){
        return new ModelAndView("timesheetCode", "timesheetCodeList", timesheetCodeService.getTimesheetCodeList());
    }
    
    @GetMapping("/timesheetCodeCreate")
    public ModelAndView timesheetCodeCreate(Model model, String error){
    	ModelAndView modelAndView = new ModelAndView("timesheetCodeCreate", "command", new TimesheetCode());
    	modelAndView.addObject("error", error);
        return modelAndView;
    }
    
    @PostMapping("/timesheetCodeCreate")
    public String timesheetCodeAdd(@ModelAttribute("timesheetCodeCreate") TimesheetCode timesheetCode){
    	
        try {
			timesheetCodeService.addTimesheetCode(timesheetCode);
		} catch (InvalidDataException e) {
			return "redirect:timesheetCodeCreate?error=" + e.getMessage();
		}
        
        return "redirect:timesheetCode";
    }
    
    @GetMapping("/timesheetCodeEdit")
    public ModelAndView timesheetCodeEdit(@RequestParam(value="id", required=true) String timesheetCodeId, Model model){
        return new ModelAndView("timesheetCodeEdit", "command", timesheetCodeService.getTimesheetCode(timesheetCodeId));
    }
    
    @PostMapping("/timesheetCodeEdit")
    public String timesheetCodeUpdate(@ModelAttribute("timesheetCodeEdit") TimesheetCode timesheetCode){
        timesheetCodeService.updateTimesheetCode(timesheetCode);
        return "redirect:timesheetCode";
    }
    
    @GetMapping("/timesheetCodeDelete")
    public String timesheetCodeDelete(@RequestParam(value="id", required=true) String timesheetCodeId, Model model){
        timesheetCodeService.deleteTimesheetCode(timesheetCodeId);
        return "redirect:timesheetCode";
    }
    
    @ModelAttribute("currentUser")
    public User currentUser(){
        return userService.getCurrentUser();
    }
}
