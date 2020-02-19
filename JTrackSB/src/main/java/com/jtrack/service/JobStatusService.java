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
import com.jtrack.exception.InvalidDataException;
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
	
	public JobStatus addJobStatus(JobStatus jobStatus) throws InvalidDataException {
		logger.info("addJobStatus({})", jobStatus);
		
		if(jobStatus.getJobStatus() == null || jobStatus.getJobStatus().isEmpty()) {
			throw new InvalidDataException("Job Status is required");
		}
		
		if(getJobStatus(jobStatus.getJobStatus()) != null) {
			throw new InvalidDataException("Job Status already exists");
		}
		
		jobStatus.setDateCrt(new Date());
		jobStatus.setUserCrt(userService.getCurrentUserId());
		 
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
		
		JobStatus jobStatusOld = getJobStatus(jobStatus.getJobStatus());
		
		jobStatus.setDateCrt(jobStatusOld.getDateCrt());
		jobStatus.setUserCrt(jobStatusOld.getUserCrt());
		
		jobStatus.setDateMod(new Date());
		jobStatus.setUserMod(userService.getCurrentUserId());
		
		return jobStatusDao.save(jobStatus);
	}

}
