package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.JobPriorityDao;
import com.jtrack.model.JobPriority;

@Service
@Transactional
public class JobPriorityService {
	
	Logger logger = LogManager.getLogger(JobPriorityService.class);
	
	@Autowired
	private JobPriorityDao jobPriorityDao;

	public List<JobPriority> getJobPriorityAll(){
		logger.info("getJobPriorityAll()");
		return jobPriorityDao.findAll();
	}
	
	public JobPriority getJobPriority(String jobPriorityId){
		logger.info("getJobPriority({})", jobPriorityId);
		Optional<JobPriority> jobPriority = jobPriorityDao.findById(jobPriorityId);
		if(jobPriority.isPresent()) {
			return jobPriority.get();
		}
		
		return new JobPriority();
	}
	
	public JobPriority addJobPriority(JobPriority jobPriority) {
		logger.info("addJobPriority({})", jobPriority);
		jobPriority.setDateCrt(new Date());
		jobPriority.setUserCrt(UserService.currentUser.getUserId());
		 
	    return jobPriorityDao.save(jobPriority);
	}
	
	public void deleteJobPriority(String jobPriorityId) {
		logger.info("deleteJobPriority({})", jobPriorityId);
		jobPriorityDao.deleteById(jobPriorityId);
	}
	
	public void deleteJobPriority(JobPriority jobPriority) {
		logger.info("deleteJobPriority({})", jobPriority);
		jobPriorityDao.delete(jobPriority);
	}
	
	public JobPriority updateJobPriority(JobPriority jobPriority) {
		logger.info("updateJobPriority({})", jobPriority);
		jobPriority.setDateMod(new Date());
		jobPriority.setUserMod(UserService.currentUser.getUserId());
		
		return jobPriorityDao.save(jobPriority);
	}
}
