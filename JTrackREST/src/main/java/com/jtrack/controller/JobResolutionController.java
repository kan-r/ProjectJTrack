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

import com.jtrack.model.JobResolution;
import com.jtrack.service.JobResolutionService;

@RestController
public class JobResolutionController {

	@Autowired
	JobResolutionService jobResolutionService;

	@GetMapping(path="/jobResolution")
	public List<JobResolution> getJobResolutionList(){
		return jobResolutionService.getJobResolutionList();
	}
	
	@GetMapping(path="/jobResolution/{id}")
	public ResponseEntity<Object> getJobResolution(@PathVariable String id){
		JobResolution jobResolution = jobResolutionService.getJobResolution(id);
		
		if(jobResolution == null) {
			return ResponseEntity.badRequest().body("JobResolution does not exist");
		}
		
		return ResponseEntity.ok(jobResolution);
	}

	@PostMapping("/jobResolution")
	public ResponseEntity<Object> addJobResolution(@RequestBody JobResolution jobResolution) {
		if(jobResolutionService.jobResolutionExists(jobResolution.getJobResolution())) {
			return ResponseEntity.badRequest().body("JobResolution already exists");
		}
		
		return ResponseEntity.ok(jobResolutionService.addJobResolution(jobResolution));
	}
	
	@PutMapping("/jobResolution")
	public ResponseEntity<Object> updateJobResolution(@RequestBody JobResolution jobResolution) {
		if(!jobResolutionService.jobResolutionExists(jobResolution.getJobResolution())) {
			return ResponseEntity.badRequest().body("JobResolution does not exist");
		}
		
		return ResponseEntity.ok(jobResolutionService.updateJobResolution(jobResolution));
	}
	
	@DeleteMapping("/jobResolution/{id}")
	public ResponseEntity<Object> deleteJobResolution(@PathVariable String id) {
		if(!jobResolutionService.jobResolutionExists(id)) {
			return ResponseEntity.badRequest().body("JobResolution does not exist");
		}
		
		jobResolutionService.deleteJobResolution(id);
		
		return ResponseEntity.ok().build();
	}
}
