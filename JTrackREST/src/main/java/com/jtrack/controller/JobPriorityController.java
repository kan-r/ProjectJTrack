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
import com.jtrack.model.JobPriority;
import com.jtrack.service.JobPriorityService;

@RestController
@RequestMapping("/jobPriorities")
public class JobPriorityController {

	@Autowired
	JobPriorityService jobPriorityService;

	@GetMapping("")
	public List<JobPriority> getJobPriorityList(){
		return jobPriorityService.getJobPriorityList();
	}
	
	@GetMapping("/{id}")
	public JobPriority getJobPriority(@PathVariable String id){
		return jobPriorityService.getJobPriority(id);
	}

	@PostMapping("")
	public JobPriority addJobPriority(@RequestBody JobPriority jobPriority) throws InvalidDataException {
		return jobPriorityService.addJobPriority(jobPriority);
	}
	
	@PutMapping("/{id}")
	public JobPriority updateJobPriority(@PathVariable String id, @RequestBody JobPriority jobPriority) throws InvalidDataException {
		jobPriority.setJobPriority(id);
		return jobPriorityService.updateJobPriority(jobPriority);
	}
	
	@DeleteMapping("/{id}")
	public String deleteJobPriority(@PathVariable String id) throws InvalidDataException {
		jobPriorityService.deleteJobPriority(id);
		return "";
	}
}
