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
import com.jtrack.exception.InvalidDataException;
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
	
	public JobType addJobType(JobType jobType) throws InvalidDataException {
		logger.info("addJobType({})", jobType);
		
		if(jobType.getJobType() == null || jobType.getJobType().isEmpty()) {
			throw new InvalidDataException("Job Type is required");
		}
		
		if(getJobType(jobType.getJobType()) != null) {
			throw new InvalidDataException("Job Type already exists");
		}
		
		jobType.setDateCrt(new Date());
		jobType.setUserCrt(userService.getCurrentUserId());
		 
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
		
		JobType jobTypeOld = getJobType(jobType.getJobType());
		
		jobType.setDateCrt(jobTypeOld.getDateCrt());
		jobType.setUserCrt(jobTypeOld.getUserCrt());
		
		jobType.setDateMod(new Date());
		jobType.setUserMod(userService.getCurrentUserId());
		
		return jobTypeDao.save(jobType);
	}

}
