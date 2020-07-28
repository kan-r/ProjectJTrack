package com.jtrack.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.Timesheet;
import com.jtrack.service.TimesheetService;

@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

	@Autowired
	TimesheetService timesheetService;

	@GetMapping("")
	public List<Timesheet> getTimesheetList(){
		return timesheetService.getTimesheetList();
	}
	
	@GetMapping(path = "", params = {"userId", "workedDateFrom", "workedDateTo"})
	public List<Timesheet> getTimesheetList(@RequestParam Map<String,String> params) throws InvalidDataException{
		
		String userId = params.get("userId"); 
		Date workedDateFrom = toDate(params.get("workedDateFrom")); 
		Date workedDateTo = toDate(params.get("workedDateTo")); 
		
		return timesheetService.getTimesheetList(userId, workedDateFrom, workedDateTo);
	}
	
	@GetMapping("/{id}")
	public Timesheet getTimesheet(@PathVariable String id){
		return timesheetService.getTimesheet(id);
	}

	@PostMapping("")
	public Timesheet addTimesheet(@RequestBody Timesheet timesheet) throws InvalidDataException {
		return timesheetService.addTimesheet(timesheet);
	}
	
	@PutMapping("/{id}")
	public Timesheet updateTimesheet(@PathVariable String id, @RequestBody Timesheet timesheet) throws InvalidDataException {
		timesheet.setTimesheetId(id);
		return timesheetService.updateTimesheet(timesheet);
	}
	
	@DeleteMapping("/{id}")
	public String deleteTimesheet(@PathVariable String id) throws InvalidDataException {
		timesheetService.deleteTimesheet(id);
		return "";
	}
	
	
	private Date toDate(String dt) throws InvalidDataException {
		
		if(dt == null || dt.isBlank()) {
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return (Date) formatter.parse(dt);
		} catch (ParseException e) {
			throw new InvalidDataException(e.getMessage() + ", expected date format yyyy-MM-dd");
		}
	}
}
