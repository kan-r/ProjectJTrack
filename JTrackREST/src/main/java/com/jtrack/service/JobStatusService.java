package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.JobStatusDao;
import com.jtrack.model.JobStatus;

@Service
@Transactional
public class JobStatusService {
	
	Logger logger = LogManager.getLogger(JobStatusService.class);
	
	@Autowired
	private JobStatusDao jobStatusDao;
	
	@Autowired
	private UserService userService;

	public List<JobStatus> getJobStatusList(){
		logger.info("getJobStatusList()");
		return jobStatusDao.findAll(Sort.by("jobStatus"));
	}
	
	public JobStatus getJobStatus(String jobStatusId){
		logger.info("getJobStatus({})", jobStatusId);
		Optional<JobStatus> jobStatus = jobStatusDao.findById(jobStatusId);
		if(jobStatus.isPresent()) {
			return jobStatus.get();
		}
		
		return null;
	}
	
	public boolean jobStatusExists(String jobStatusId) {
		JobStatus jobStatusExisting = getJobStatus(jobStatusId);
		return (jobStatusExisting != null);
	}
	
	public JobStatus addJobStatus(JobStatus jobStatus) {
		logger.info("addJobStatus({})", jobStatus);
		
		jobStatus.setUserCrt(userService.getCurrentUserId());
		jobStatus.setDateCrt(new Date());
		 
	    return jobStatusDao.save(jobStatus);
	}
	
	public void deleteJobStatus(String jobStatusId) {
		logger.info("deleteJobStatus({})", jobStatusId);
		jobStatusDao.deleteById(jobStatusId);
	}
	
	public JobStatus updateJobStatus(JobStatus jobStatus) {
		logger.info("updateJobStatus({})", jobStatus);
		
		jobStatus.setUserMod(userService.getCurrentUserId());
		jobStatus.setDateMod(new Date());
		
		return jobStatusDao.save(jobStatus);
	}
}
