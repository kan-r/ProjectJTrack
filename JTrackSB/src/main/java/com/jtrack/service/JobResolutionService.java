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
	
	public JobResolution addJobResolution(JobResolution jobResolution) throws InvalidDataException {
		logger.info("addJobResolution({})", jobResolution);
		
		if(jobResolution.getJobResolution() == null || jobResolution.getJobResolution().isEmpty()) {
			throw new InvalidDataException("Job Resolution is required");
		}
		
		if(getJobResolution(jobResolution.getJobResolution()) != null) {
			throw new InvalidDataException("Job Resolution already exists");
		}
		
		jobResolution.setDateCrt(new Date());
		jobResolution.setUserCrt(userService.getCurrentUserId());
		 
	    return jobResolutionDao.save(jobResolution);
	}
	
	public void deleteJobResolution(String jobResolutionId) {
		logger.info("deleteJobResolution({})", jobResolutionId);
		jobResolutionDao.deleteById(jobResolutionId);
	}
	
	public void deleteJobResolution(JobResolution jobResolution) {
		logger.info("deleteJobResolution({})", jobResolution);
		jobResolutionDao.delete(jobResolution);
	}
	
	public JobResolution updateJobResolution(JobResolution jobResolution) {
		logger.info("updateJobResolution({})", jobResolution);
		
		JobResolution jobResolutionOld = getJobResolution(jobResolution.getJobResolution());
		
		jobResolution.setDateCrt(jobResolutionOld.getDateCrt());
		jobResolution.setUserCrt(jobResolutionOld.getUserCrt());
		
		jobResolution.setDateMod(new Date());
		jobResolution.setUserMod(userService.getCurrentUserId());
		
		return jobResolutionDao.save(jobResolution);
	}
}
