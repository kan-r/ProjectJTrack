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
import com.jtrack.model.JobType;
import com.jtrack.service.JobTypeService;

@RestController
@RequestMapping("/jobTypes")
public class JobTypeController {

	@Autowired
	JobTypeService jobTypeService;

	@GetMapping("")
	public List<JobType> getJobTypeList(){
		return jobTypeService.getJobTypeList();
	}
	
	@GetMapping("/{id}")
	public JobType getJobType(@PathVariable String id){
		return jobTypeService.getJobType(id);
	}

	@PostMapping("")
	public JobType addJobType(@RequestBody JobType jobType) throws InvalidDataException {
		return jobTypeService.addJobType(jobType);
	}
	
	@PutMapping("/{id}")
	public JobType updateJobType(@PathVariable String id, @RequestBody JobType jobType) throws InvalidDataException {
		jobType.setJobType(id);
		return jobTypeService.updateJobType(jobType);
	}
	
	@DeleteMapping("/{id}")
	public String deleteJobType(@PathVariable String id) throws InvalidDataException {
		jobTypeService.deleteJobType(id);
		return "";
	}
}
