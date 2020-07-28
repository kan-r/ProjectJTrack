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
import com.jtrack.model.JobStage;
import com.jtrack.service.JobStageService;

@RestController
@RequestMapping("/jobStages")
public class JobStageController {

	@Autowired
	JobStageService jobStageService;

	@GetMapping("")
	public List<JobStage> getJobStageList(){
		return jobStageService.getJobStageList();
	}
	
	@GetMapping("/{id}")
	public JobStage getJobStage(@PathVariable String id){
		return jobStageService.getJobStage(id);
	}

	@PostMapping("")
	public JobStage addJobStage(@RequestBody JobStage jobStage) throws InvalidDataException {
		return jobStageService.addJobStage(jobStage);
	}
	
	@PutMapping("/{id}")
	public JobStage updateJobStage(@PathVariable String id, @RequestBody JobStage jobStage) throws InvalidDataException {
		jobStage.setJobStage(id);
		return jobStageService.updateJobStage(jobStage);
	}
	
	@DeleteMapping("/{id}")
	public String deleteJobStage(@PathVariable String id) throws InvalidDataException {
		jobStageService.deleteJobStage(id);
		return "";
	}
}
