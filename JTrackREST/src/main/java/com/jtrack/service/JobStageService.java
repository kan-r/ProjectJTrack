package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.JobStageDao;
import com.jtrack.model.JobStage;

@Service
@Transactional
public class JobStageService {
	
	Logger logger = LogManager.getLogger(JobStageService.class);
	
	@Autowired
	private JobStageDao jobStageDao;

	public List<JobStage> getJobStageList(){
		logger.info("getJobStageList()");
		return jobStageDao.findAll();
	}
	
	public JobStage getJobStage(String jobStageId){
		logger.info("getJobStage({})", jobStageId);
		Optional<JobStage> jobStage = jobStageDao.findById(jobStageId);
		if(jobStage.isPresent()) {
			return jobStage.get();
		}
		
		return null;
	}
	
	public boolean jobStageExists(String jobStageId) {
		JobStage jobStageExisting = getJobStage(jobStageId);
		return (jobStageExisting != null);
	}
	
	public JobStage addJobStage(JobStage jobStage) {
		logger.info("addJobStage({})", jobStage);
		jobStage.setDateCrt(new Date());
		 
	    return jobStageDao.save(jobStage);
	}
	
	public void deleteJobStage(String jobStageId) {
		logger.info("deleteJobStage({})", jobStageId);
		jobStageDao.deleteById(jobStageId);
	}
	
	public JobStage updateJobStage(JobStage jobStage) {
		logger.info("updateJobStage({})", jobStage);
		jobStage.setDateMod(new Date());
		
		return jobStageDao.save(jobStage);
	}

}
