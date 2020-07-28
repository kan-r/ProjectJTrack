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
import com.jtrack.model.JobStatus;
import com.jtrack.service.JobStatusService;

@RestController
@RequestMapping("/jobStatuses")
public class JobStatusController {

	@Autowired
	JobStatusService jobStatusService;

	@GetMapping("")
	public List<JobStatus> getJobStatusList(){
		return jobStatusService.getJobStatusList();
	}
	
	@GetMapping("/{id}")
	public JobStatus getJobStatus(@PathVariable String id){
		return jobStatusService.getJobStatus(id);
	}

	@PostMapping("")
	public JobStatus addJobStatus(@RequestBody JobStatus jobStatus) throws InvalidDataException {
		return jobStatusService.addJobStatus(jobStatus);
	}
	
	@PutMapping("/{id}")
	public JobStatus updateJobStatus(@PathVariable String id, @RequestBody JobStatus jobStatus) throws InvalidDataException {
		jobStatus.setJobStatus(id);
		return jobStatusService.updateJobStatus(jobStatus);
	}
	
	@DeleteMapping("/{id}")
	public String deleteJobStatus(@PathVariable String id) throws InvalidDataException {
		jobStatusService.deleteJobStatus(id);
		return "";
	}
}
