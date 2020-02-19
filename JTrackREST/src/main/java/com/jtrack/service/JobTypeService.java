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

import com.jtrack.dao.JobTypeDao;
import com.jtrack.model.JobType;

@Service
@Transactional
public class JobTypeService {
	
	Logger logger = LogManager.getLogger(JobTypeService.class);
	
	@Autowired
	private JobTypeDao jobTypeDao;
	
	@Autowired
	private UserService userService;

	public List<JobType> getJobTypeList(){
		logger.info("getJobTypeList()");
		return jobTypeDao.findAll(Sort.by("jobType"));
	}
	
	public JobType getJobType(String jobTypeId){
		logger.info("getJobType({})", jobTypeId);
		Optional<JobType> jobType = jobTypeDao.findById(jobTypeId);
		if(jobType.isPresent()) {
			return jobType.get();
		}
		
		return null;
	}
	
	public boolean jobTypeExists(String jobTypeId) {
		JobType jobTypeExisting = getJobType(jobTypeId);
		return (jobTypeExisting != null);
	}
	
	public JobType addJobType(JobType jobType) {
		logger.info("addJobType({})", jobType);
		
		jobType.setUserCrt(userService.getCurrentUserId());
		jobType.setDateCrt(new Date());
		 
	    return jobTypeDao.save(jobType);
	}
	
	public void deleteJobType(String jobTypeId) {
		logger.info("deleteJobType({})", jobTypeId);
		jobTypeDao.deleteById(jobTypeId);
	}
	
	public JobType updateJobType(JobType jobType) {
		logger.info("updateJobType({})", jobType);
		
		jobType.setUserMod(userService.getCurrentUserId());
		jobType.setDateMod(new Date());
		
		return jobTypeDao.save(jobType);
	}
}
