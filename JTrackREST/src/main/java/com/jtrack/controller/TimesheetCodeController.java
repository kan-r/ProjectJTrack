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

import com.jtrack.model.TimesheetCode;
import com.jtrack.service.TimesheetCodeService;

@RestController
public class TimesheetCodeController {
	
	@Autowired
	TimesheetCodeService timesheetCodeService;

	@GetMapping(path="/timesheetCode")
	public List<TimesheetCode> getTimesheetCodeList(){
		return timesheetCodeService.getTimesheetCodeList();
	}
	
	@GetMapping(path="/timesheetCode/{id}")
	public ResponseEntity<Object> getTimesheetCode(@PathVariable String id){
		TimesheetCode timesheetCode = timesheetCodeService.getTimesheetCode(id);
		
		if(timesheetCode == null) {
			return ResponseEntity.badRequest().body("TimesheetCode does not exist");
		}
		
		return ResponseEntity.ok(timesheetCode);
	}

	@PostMapping("/timesheetCode")
	public ResponseEntity<Object> addTimesheetCode(@RequestBody TimesheetCode timesheetCode) {
		if(timesheetCodeService.timesheetCodeExists(timesheetCode.getTimesheetCode())) {
			return ResponseEntity.badRequest().body("TimesheetCode already exists");
		}
		
		return ResponseEntity.ok(timesheetCodeService.addTimesheetCode(timesheetCode));
	}
	
	@PutMapping("/timesheetCode")
	public ResponseEntity<Object> updateTimesheetCode(@RequestBody TimesheetCode timesheetCode) {
		if(!timesheetCodeService.timesheetCodeExists(timesheetCode.getTimesheetCode())) {
			return ResponseEntity.badRequest().body("TimesheetCode does not exist");
		}
		
		return ResponseEntity.ok(timesheetCodeService.updateTimesheetCode(timesheetCode));
	}
	
	@DeleteMapping("/timesheetCode/{id}")
	public ResponseEntity<Object> deleteTimesheetCode(@PathVariable String id) {
		if(!timesheetCodeService.timesheetCodeExists(id)) {
			return ResponseEntity.badRequest().body("TimesheetCode does not exist");
		}
		
		timesheetCodeService.deleteTimesheetCode(id);
		
		return ResponseEntity.ok().build();
	}
}
