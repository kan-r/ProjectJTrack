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
import com.jtrack.model.JobResolution;
import com.jtrack.service.JobResolutionService;

@RestController
@RequestMapping("/jobResolutions")
public class JobResolutionController {

	@Autowired
	JobResolutionService jobResolutionService;

	@GetMapping("")
	public List<JobResolution> getJobResolutionList(){
		return jobResolutionService.getJobResolutionList();
	}
	
	@GetMapping("/{id}")
	public JobResolution getJobResolution(@PathVariable String id){
		return jobResolutionService.getJobResolution(id);
	}

	@PostMapping("")
	public JobResolution addJobResolution(@RequestBody JobResolution jobResolution) throws InvalidDataException {
		return jobResolutionService.addJobResolution(jobResolution);
	}
	
	@PutMapping("/{id}")
	public JobResolution updateJobResolution(@PathVariable String id, @RequestBody JobResolution jobResolution) throws InvalidDataException {
		jobResolution.setJobResolution(id);
		return jobResolutionService.updateJobResolution(jobResolution);
	}
	
	@DeleteMapping("/{id}")
	public String deleteJobResolution(@PathVariable String id) throws InvalidDataException {
		jobResolutionService.deleteJobResolution(id);
		return "";
	}
}
