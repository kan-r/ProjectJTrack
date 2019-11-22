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

import com.jtrack.model.JobStatus;
import com.jtrack.service.JobStatusService;

@RestController
public class JobStatusController {

	@Autowired
	JobStatusService jobStatusService;

	@GetMapping(path="/jobStatus")
	public List<JobStatus> getJobStatusList(){
		return jobStatusService.getJobStatusList();
	}
	
	@GetMapping(path="/jobStatus/{id}")
	public ResponseEntity<Object> getJobStatus(@PathVariable String id){
		JobStatus jobStatus = jobStatusService.getJobStatus(id);
		
		if(jobStatus == null) {
			return ResponseEntity.badRequest().body("JobStatus does not exist");
		}
		
		return ResponseEntity.ok(jobStatus);
	}

	@PostMapping("/jobStatus")
	public ResponseEntity<Object> addJobStatus(@RequestBody JobStatus jobStatus) {
		if(jobStatusService.jobStatusExists(jobStatus.getJobStatus())) {
			return ResponseEntity.badRequest().body("JobStatus already exists");
		}
		
		return ResponseEntity.ok(jobStatusService.addJobStatus(jobStatus));
	}
	
	@PutMapping("/jobStatus")
	public ResponseEntity<Object> updateJobStatus(@RequestBody JobStatus jobStatus) {
		if(!jobStatusService.jobStatusExists(jobStatus.getJobStatus())) {
			return ResponseEntity.badRequest().body("JobStatus does not exist");
		}
		
		return ResponseEntity.ok(jobStatusService.updateJobStatus(jobStatus));
	}
	
	@DeleteMapping("/jobStatus/{id}")
	public ResponseEntity<Object> deleteJobStatus(@PathVariable String id) {
		if(!jobStatusService.jobStatusExists(id)) {
			return ResponseEntity.badRequest().body("JobStatus does not exist");
		}
		
		jobStatusService.deleteJobStatus(id);
		
		return ResponseEntity.ok().build();
	}
}
