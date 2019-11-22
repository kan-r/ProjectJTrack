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

import com.jtrack.model.JobType;
import com.jtrack.service.JobTypeService;

@RestController
public class JobTypeController {

	@Autowired
	JobTypeService jobTypeService;

	@GetMapping(path="/jobType")
	public List<JobType> getJobTypeList(){
		return jobTypeService.getJobTypeList();
	}
	
	@GetMapping(path="/jobType/{id}")
	public ResponseEntity<Object> getJobType(@PathVariable String id){
		JobType jobType = jobTypeService.getJobType(id);
		
		if(jobType == null) {
			return ResponseEntity.badRequest().body("JobType does not exist");
		}
		
		return ResponseEntity.ok(jobType);
	}

	@PostMapping("/jobType")
	public ResponseEntity<Object> addJobType(@RequestBody JobType jobType) {
		if(jobTypeService.jobTypeExists(jobType.getJobType())) {
			return ResponseEntity.badRequest().body("JobType already exists");
		}
		
		return ResponseEntity.ok(jobTypeService.addJobType(jobType));
	}
	
	@PutMapping("/jobType")
	public ResponseEntity<Object> updateJobType(@RequestBody JobType jobType) {
		if(!jobTypeService.jobTypeExists(jobType.getJobType())) {
			return ResponseEntity.badRequest().body("JobType does not exist");
		}
		
		return ResponseEntity.ok(jobTypeService.updateJobType(jobType));
	}
	
	@DeleteMapping("/jobType/{id}")
	public ResponseEntity<Object> deleteJobType(@PathVariable String id) {
		if(!jobTypeService.jobTypeExists(id)) {
			return ResponseEntity.badRequest().body("JobType does not exist");
		}
		
		jobTypeService.deleteJobType(id);
		
		return ResponseEntity.ok().build();
	}
}
