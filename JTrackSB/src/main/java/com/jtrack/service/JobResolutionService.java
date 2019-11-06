package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.JobResolutionDao;
import com.jtrack.model.JobResolution;

@Service
@Transactional
public class JobResolutionService {

	Logger logger = LogManager.getLogger(JobResolutionService.class);
	
	@Autowired
	private JobResolutionDao jobResolutionDao;

	public List<JobResolution> getJobResolutionAll(){
		logger.info("getJobResolutionAll()");
		return jobResolutionDao.findAll();
	}
	
	public JobResolution getJobResolution(String jobResolutionId){
		logger.info("getJobResolution({})", jobResolutionId);
		Optional<JobResolution> jobResolution = jobResolutionDao.findById(jobResolutionId);
		if(jobResolution.isPresent()) {
			return jobResolution.get();
		}
		
		return new JobResolution();
	}
	
	public JobResolution addJobResolution(JobResolution jobResolution) {
		logger.info("addJobResolution({})", jobResolution);
		jobResolution.setDateCrt(new Date());
		jobResolution.setUserCrt(UserService.currentUser.getUserId());
		 
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
		jobResolution.setDateMod(new Date());
		jobResolution.setUserMod(UserService.currentUser.getUserId());
		
		return jobResolutionDao.save(jobResolution);
	}
}
