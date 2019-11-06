package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.JobTypeDao;
import com.jtrack.model.JobType;

@Service
@Transactional
public class JobTypeService {
	
	Logger logger = LogManager.getLogger(JobTypeService.class);
	
	@Autowired
	private JobTypeDao jobTypeDao;

	public List<JobType> getJobTypeAll(){
		logger.info("getJobTypeAll()");
		return jobTypeDao.findAll();
	}
	
	public JobType getJobType(String jobTypeId){
		logger.info("getJobType({})", jobTypeId);
		Optional<JobType> jobType = jobTypeDao.findById(jobTypeId);
		if(jobType.isPresent()) {
			return jobType.get();
		}
		
		return new JobType();
	}
	
	public JobType addJobType(JobType jobType) {
		logger.info("addJobType({})", jobType);
		jobType.setDateCrt(new Date());
		jobType.setUserCrt(UserService.currentUser.getUserId());
		 
	    return jobTypeDao.save(jobType);
	}
	
	public void deleteJobType(String jobTypeId) {
		logger.info("deleteJobType({})", jobTypeId);
		jobTypeDao.deleteById(jobTypeId);
	}
	
	public void deleteJobType(JobType jobType) {
		logger.info("deleteJobType({})", jobType);
		jobTypeDao.delete(jobType);
	}
	
	public JobType updateJobType(JobType jobType) {
		logger.info("updateJobType({})", jobType);
		jobType.setDateMod(new Date());
		jobType.setUserMod(UserService.currentUser.getUserId());
		
		return jobTypeDao.save(jobType);
	}

}
