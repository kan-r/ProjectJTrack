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

import com.jtrack.dao.JobResolutionDao;
import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.JobResolution;

@Service
@Transactional
public class JobResolutionService {

	Logger logger = LogManager.getLogger(JobResolutionService.class);
	
	@Autowired
	private JobResolutionDao jobResolutionDao;
	
	@Autowired
	private UserService userService;

	public List<JobResolution> getJobResolutionList(){
		logger.info("getJobResolutionList()");
		return jobResolutionDao.findAll(Sort.by("jobResolution"));
	}
	
	public JobResolution getJobResolution(String jobResolutionId){
		logger.info("getJobResolution({})", jobResolutionId);
		Optional<JobResolution> jobResolution = jobResolutionDao.findById(jobResolutionId);
		if(jobResolution.isPresent()) {
			return jobResolution.get();
		}
		
		return null;
	}
	
	public boolean jobResolutionExists(String jobResolutionId) {
		JobResolution jobResolutionExisting = getJobResolution(jobResolutionId);
		return (jobResolutionExisting != null);
	}
	
	public JobResolution addJobResolution(JobResolution jobResolution) throws InvalidDataException {
		logger.info("addJobResolution({})", jobResolution);
		
		if(jobResolutionExists(jobResolution.getJobResolution())) {
			throw new InvalidDataException("JobResolution already exists");
		}
		
		jobResolution.setUserCrt(userService.getCurrentUserId());
		jobResolution.setDateCrt(new Date());
		 
	    return jobResolutionDao.save(jobResolution);
	}
	
	public void deleteJobResolution(String jobResolutionId) throws InvalidDataException {
		logger.info("deleteJobResolution({})", jobResolutionId);
		
		if(!jobResolutionExists(jobResolutionId)) {
			throw new InvalidDataException("JobResolution does not exist");
		}
		
		jobResolutionDao.deleteById(jobResolutionId);
	}
	
	public JobResolution updateJobResolution(JobResolution jobResolution) throws InvalidDataException {
		logger.info("updateJobResolution({})", jobResolution);
		
		if(!jobResolutionExists(jobResolution.getJobResolution())) {
			throw new InvalidDataException("JobResolution does not exist");
		}
		
		jobResolution.setUserMod(userService.getCurrentUserId());
		jobResolution.setDateMod(new Date());
		
		return jobResolutionDao.save(jobResolution);
	}
}
