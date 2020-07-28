package com.jtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.TimesheetCode;
import com.jtrack.service.TimesheetCodeService;

@RestController
@RequestMapping("/timesheetCodes")
public class TimesheetCodeController {
	
	@Autowired
	TimesheetCodeService timesheetCodeService;

	@GetMapping("")
	public List<TimesheetCode> getTimesheetCodeList(){
		return timesheetCodeService.getTimesheetCodeList();
	}
	
	@GetMapping("/{id}")
	public TimesheetCode getTimesheetCode(@PathVariable String id){
		return timesheetCodeService.getTimesheetCode(id);
	}

	@PostMapping("")
	public TimesheetCode addTimesheetCode(@RequestBody TimesheetCode timesheetCode) throws InvalidDataException {
		return timesheetCodeService.addTimesheetCode(timesheetCode);
	}
	
	@PutMapping("/{id}")
	public TimesheetCode updateTimesheetCode(@PathVariable String id, @RequestBody TimesheetCode timesheetCode) throws InvalidDataException {
		timesheetCode.setTimesheetCode(id);
		return timesheetCodeService.updateTimesheetCode(timesheetCode);
	}
	
	@DeleteMapping("/{id}")
	public String deleteTimesheetCode(@PathVariable String id) throws InvalidDataException {
		timesheetCodeService.deleteTimesheetCode(id);
		return "";
	}
}
