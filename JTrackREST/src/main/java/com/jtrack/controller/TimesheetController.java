package com.jtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.model.Timesheet;
import com.jtrack.model.TimesheetSO;
import com.jtrack.service.TimesheetService;

@RestController
public class TimesheetController {

	@Autowired
	TimesheetService timesheetService;

	@GetMapping(path="/timesheet")
	public List<Timesheet> getTimesheetList(){
		return timesheetService.getTimesheetList();
	}
	
	@PostMapping(path="/timesheet/SO")
	public List<Timesheet> getTimesheetList(@RequestBody TimesheetSO timesheetSO){
		return timesheetService.getTimesheetList(
				timesheetSO.getUserId(), 
				timesheetSO.getWorkedDateFrom(), 
				timesheetSO.getWorkedDateTo());
	}
	
	@GetMapping(path="/timesheet/{id}")
	public ResponseEntity<Object> getTimesheet(@PathVariable String id){
		Timesheet timesheet = timesheetService.getTimesheet(id);
		
		if(timesheet == null) {
			return ResponseEntity.badRequest().body("Timesheet does not exist");
		}
		
		return ResponseEntity.ok(timesheet);
	}

	@PostMapping("/timesheet")
	public ResponseEntity<Object> addTimesheet(@RequestBody Timesheet timesheet) {
		if(timesheetService.timesheetExists(timesheet.getUserId(), timesheet.getJobNo(), timesheet.getWorkedDate())) {
			return ResponseEntity.badRequest().body("Timesheet already exists");
		}
		
		return ResponseEntity.ok(timesheetService.addTimesheet(timesheet));
	}
	
	@PutMapping("/timesheet")
	public ResponseEntity<Object> updateTimesheet(@RequestBody Timesheet timesheet) {
		if(!timesheetService.timesheetExists(timesheet.getTimesheetId())) {
			return ResponseEntity.badRequest().body("Timesheet does not exist");
		}
		
		return ResponseEntity.ok(timesheetService.updateTimesheet(timesheet));
	}
	
	@DeleteMapping("/timesheet/{id}")
	public ResponseEntity<Object> deleteTimesheet(@PathVariable String id) {
		if(!timesheetService.timesheetExists(id)) {
			return ResponseEntity.badRequest().body("Timesheet does not exist");
		}
		
		timesheetService.deleteTimesheet(id);
		
		return ResponseEntity.ok().build();
	}
}
