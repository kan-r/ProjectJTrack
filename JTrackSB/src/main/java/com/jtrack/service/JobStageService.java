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

import com.jtrack.dao.JobStageDao;
import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.JobStage;

@Service
@Transactional
public class JobStageService {
	
	Logger logger = LogManager.getLogger(JobStageService.class);
	
	@Autowired
	private JobStageDao jobStageDao;
	
	@Autowired
	private UserService userService;

	public List<JobStage> getJobStageList(){
		logger.info("getJobStageList()");
		return jobStageDao.findAll(Sort.by("jobStage"));
	}
	
	public JobStage getJobStage(String jobStageId){
		logger.info("getJobStage({})", jobStageId);
		Optional<JobStage> jobStage = jobStageDao.findById(jobStageId);
		if(jobStage.isPresent()) {
			return jobStage.get();
		}
		
		return null;
	}
	
	public JobStage addJobStage(JobStage jobStage) throws InvalidDataException {
		logger.info("addJobStage({})", jobStage);
		
		if(jobStage.getJobStage() == null || jobStage.getJobStage().isEmpty()) {
			throw new InvalidDataException("Job Stage is required");
		}
		
		if(getJobStage(jobStage.getJobStage()) != null) {
			throw new InvalidDataException("Job Stage already exists");
		}
		
		jobStage.setDateCrt(new Date());
		jobStage.setUserCrt(userService.getCurrentUserId());
		 
	    return jobStageDao.save(jobStage);
	}
	
	public void deleteJobStage(String jobStageId) {
		logger.info("deleteJobStage({})", jobStageId);
		jobStageDao.deleteById(jobStageId);
	}
	
	public void deleteJobStage(JobStage jobStage) {
		logger.info("deleteJobStage({})", jobStage);
		jobStageDao.delete(jobStage);
	}
	
	public JobStage updateJobStage(JobStage jobStage) {
		logger.info("updateJobStage({})", jobStage);
		
		JobStage jobStageOld = getJobStage(jobStage.getJobStage());
		
		jobStage.setDateCrt(jobStageOld.getDateCrt());
		jobStage.setUserCrt(jobStageOld.getUserCrt());
		
		jobStage.setDateMod(new Date());
		jobStage.setUserMod(userService.getCurrentUserId());
		
		return jobStageDao.save(jobStage);
	}

}
