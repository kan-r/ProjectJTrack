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

import com.jtrack.model.JobStage;
import com.jtrack.service.JobStageService;

@RestController
public class JobStageController {

	@Autowired
	JobStageService jobStageService;

	@GetMapping(path="/jobStage")
	public List<JobStage> getJobStageList(){
		return jobStageService.getJobStageList();
	}
	
	@GetMapping(path="/jobStage/{id}")
	public ResponseEntity<Object> getJobStage(@PathVariable String id){
		JobStage jobStage = jobStageService.getJobStage(id);
		
		if(jobStage == null) {
			return ResponseEntity.badRequest().body("JobStage does not exist");
		}
		
		return ResponseEntity.ok(jobStage);
	}

	@PostMapping("/jobStage")
	public ResponseEntity<Object> addJobStage(@RequestBody JobStage jobStage) {
		if(jobStageService.jobStageExists(jobStage.getJobStage())) {
			return ResponseEntity.badRequest().body("JobStage already exists");
		}
		
		return ResponseEntity.ok(jobStageService.addJobStage(jobStage));
	}
	
	@PutMapping("/jobStage")
	public ResponseEntity<Object> updateJobStage(@RequestBody JobStage jobStage) {
		if(!jobStageService.jobStageExists(jobStage.getJobStage())) {
			return ResponseEntity.badRequest().body("JobStage does not exist");
		}
		
		return ResponseEntity.ok(jobStageService.updateJobStage(jobStage));
	}
	
	@DeleteMapping("/jobStage/{id}")
	public ResponseEntity<Object> deleteJobStage(@PathVariable String id) {
		if(!jobStageService.jobStageExists(id)) {
			return ResponseEntity.badRequest().body("JobStage does not exist");
		}
		
		jobStageService.deleteJobStage(id);
		
		return ResponseEntity.ok().build();
	}
}
