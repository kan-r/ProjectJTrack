package com.jtrack.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.Job;
import com.jtrack.model.JobSO;
import com.jtrack.service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {
	
	@Autowired
	JobService jobService;

	@GetMapping("")
	public List<Job> getJobList(){
		return jobService.getJobList();
	}
	
	@GetMapping(path="", params = "type")
	public List<Job> getParentJobList(@RequestParam String type){
		return jobService.getJobList(type);
	}
	
	@GetMapping(path = "", 
				params = {
					"name", 
					"type", 
					"status", 
					"assignedTo",
					"includeChild",
					"nameC", 
					"typeC", 
					"statusC", 
					"assignedToC"
				})
	public List<Job> getJobList(@RequestParam Map<String,String> params){
		
		JobSO jobSO = new JobSO();
		
		jobSO.setJobName(params.get("name"));
		jobSO.setJobType(params.get("type"));
		jobSO.setJobStatus(params.get("status"));
		jobSO.setAssignedTo(params.get("assignedTo"));
		jobSO.setIncludeChildJobs(toBoolean(params.get("includeChild")));
		jobSO.setJobNameChild(params.get("nameC"));
		jobSO.setJobTypeChild(params.get("typeC"));
		jobSO.setJobStatusChild(params.get("statusC"));
		jobSO.setAssignedToChild(params.get("assignedToC"));
		
		return jobService.getJobList(jobSO);
	}
	
	@GetMapping("/{id}")
	public Job getJob(@PathVariable long id){
		return jobService.getJob(id);
	}

	@PostMapping("")
	public Job addJob(@RequestBody Job job) throws InvalidDataException {
		return jobService.addJob(job);
	}
	
	@PutMapping("/{id}")
	public Job updateJob(@PathVariable long id, @RequestBody Job job) throws InvalidDataException {
		job.setJobNo(id);
		return jobService.updateJob(job);
	}
	
	@DeleteMapping("/{id}")
	public String deleteJob(@PathVariable long id) throws InvalidDataException {
		jobService.deleteJob(id);
		return "";
	}
	
	
	private boolean toBoolean(String val) {
		
		if(val == null || val.isBlank() || val.equals("0") || val.equalsIgnoreCase("false")) {
			return false;
		}
		
		return true;
	}
}
