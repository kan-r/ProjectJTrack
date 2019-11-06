package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

	public List<JobStatus> getJobStatusAll(){
		logger.info("getJobStatusAll()");
		return jobStatusDao.findAll();
	}
	
	public JobStatus getJobStatus(String jobStatusId){
		logger.info("getJobStatus({})", jobStatusId);
		Optional<JobStatus> jobStatus = jobStatusDao.findById(jobStatusId);
		if(jobStatus.isPresent()) {
			return jobStatus.get();
		}
		
		return new JobStatus();
	}
	
	public JobStatus addJobStatus(JobStatus jobStatus) {
		logger.info("addJobStatus({})", jobStatus);
		jobStatus.setDateCrt(new Date());
		jobStatus.setUserCrt(UserService.currentUser.getUserId());
		 
	    return jobStatusDao.save(jobStatus);
	}
	
	public void deleteJobStatus(String jobStatusId) {
		logger.info("deleteJobStatus({})", jobStatusId);
		jobStatusDao.deleteById(jobStatusId);
	}
	
	public void deleteJobStatus(JobStatus jobStatus) {
		logger.info("deleteJobStatus({})", jobStatus);
		jobStatusDao.delete(jobStatus);
	}
	
	public JobStatus updateJobStatus(JobStatus jobStatus) {
		logger.info("updateJobStatus({})", jobStatus);
		jobStatus.setDateMod(new Date());
		jobStatus.setUserMod(UserService.currentUser.getUserId());
		
		return jobStatusDao.save(jobStatus);
	}

}
