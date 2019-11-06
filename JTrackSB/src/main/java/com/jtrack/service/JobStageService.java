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

	public List<JobStage> getJobStageAll(){
		logger.info("getJobStageAll()");
		return jobStageDao.findAll();
	}
	
	public JobStage getJobStage(String jobStageId){
		logger.info("getJobStage({})", jobStageId);
		Optional<JobStage> jobStage = jobStageDao.findById(jobStageId);
		if(jobStage.isPresent()) {
			return jobStage.get();
		}
		
		return new JobStage();
	}
	
	public JobStage addJobStage(JobStage jobStage) {
		logger.info("addJobStage({})", jobStage);
		jobStage.setDateCrt(new Date());
		jobStage.setUserCrt(UserService.currentUser.getUserId());
		 
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
		jobStage.setDateMod(new Date());
		jobStage.setUserMod(UserService.currentUser.getUserId());
		
		return jobStageDao.save(jobStage);
	}

}
