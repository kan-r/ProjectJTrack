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

import com.jtrack.model.JobPriority;
import com.jtrack.service.JobPriorityService;

@RestController
public class JobPriorityController {

	@Autowired
	JobPriorityService jobPriorityService;

	@GetMapping(path="/jobPriority")
	public List<JobPriority> getJobPriorityList(){
		return jobPriorityService.getJobPriorityList();
	}
	
	@GetMapping(path="/jobPriority/{id}")
	public ResponseEntity<Object> getJobPriority(@PathVariable String id){
		JobPriority jobPriority = jobPriorityService.getJobPriority(id);
		
		if(jobPriority == null) {
			return ResponseEntity.badRequest().body("JobPriority does not exist");
		}
		
		return ResponseEntity.ok(jobPriority);
	}

	@PostMapping("/jobPriority")
	public ResponseEntity<Object> addJobPriority(@RequestBody JobPriority jobPriority) {
		if(jobPriorityService.jobPriorityExists(jobPriority.getJobPriority())) {
			return ResponseEntity.badRequest().body("JobPriority already exists");
		}
		
		return ResponseEntity.ok(jobPriorityService.addJobPriority(jobPriority));
	}
	
	@PutMapping("/jobPriority")
	public ResponseEntity<Object> updateJobPriority(@RequestBody JobPriority jobPriority) {
		if(!jobPriorityService.jobPriorityExists(jobPriority.getJobPriority())) {
			return ResponseEntity.badRequest().body("JobPriority does not exist");
		}
		
		return ResponseEntity.ok(jobPriorityService.updateJobPriority(jobPriority));
	}
	
	@DeleteMapping("/jobPriority/{id}")
	public ResponseEntity<Object> deleteJobPriority(@PathVariable String id) {
		if(!jobPriorityService.jobPriorityExists(id)) {
			return ResponseEntity.badRequest().body("JobPriority does not exist");
		}
		
		jobPriorityService.deleteJobPriority(id);
		
		return ResponseEntity.ok().build();
	}
}
