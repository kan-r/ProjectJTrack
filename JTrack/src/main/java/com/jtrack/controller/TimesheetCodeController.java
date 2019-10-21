/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.controller;

import com.jtrack.model.TimesheetCode;
import com.jtrack.model.Users;
import com.jtrack.service.TimesheetCodeService;
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
public class TimesheetCodeController {
    
    @Resource
    private TimesheetCodeService timesheetCodeService;
    
    @RequestMapping(value="/timesheetCode", method = RequestMethod.GET)
    public ModelAndView timesheetCode(){
        return new ModelAndView("timesheetCode", "timesheetCodeList", timesheetCodeService.getAll());
    }
    
    @RequestMapping(value="/timesheetCodeCreate", method = RequestMethod.GET)
    public ModelAndView timesheetCodeCreate(Model model){
        return new ModelAndView("timesheetCodeCreate", "command", new TimesheetCode());
    }
    
    @RequestMapping(value="/timesheetCodeCreate", method = RequestMethod.POST)
    public String timesheetCodeAdd(@ModelAttribute("timesheetCodeCreate") TimesheetCode timesheetCode){
        timesheetCodeService.add(timesheetCode);
        return "redirect:timesheetCode.htm";
    }
    
    @RequestMapping(value="/timesheetCodeEdit", method = RequestMethod.GET)
    public ModelAndView timesheetCodeEdit(@RequestParam(value="id", required=true) String timesheetCode, Model model){
        return new ModelAndView("timesheetCodeEdit", "command", timesheetCodeService.get(timesheetCode));
    }
    
    @RequestMapping(value="/timesheetCodeEdit", method = RequestMethod.POST)
    public String timesheetCodeUpdate(@ModelAttribute("timesheetCodeEdit") TimesheetCode timesheetCode){
        timesheetCodeService.update(timesheetCode);
        return "redirect:timesheetCode.htm";
    }
    
    @RequestMapping(value="/timesheetCodeDelete", method = RequestMethod.GET)
    public String timesheetCodeDelete(@RequestParam(value="id", required=true) String timesheetCode, Model model){
        timesheetCodeService.delete(timesheetCode);
        return "redirect:timesheetCode.htm";
    }
    
    @ModelAttribute("currentUser")
    public Users currentUser(){
        return UsersService.user;
    }
}