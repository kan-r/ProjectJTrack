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

import com.jtrack.dao.JobPriorityDao;
import com.jtrack.model.JobPriority;

@Service
@Transactional
public class JobPriorityService {
	
	Logger logger = LogManager.getLogger(JobPriorityService.class);
	
	@Autowired
	private JobPriorityDao jobPriorityDao;

	public List<JobPriority> getJobPriorityList(){
		logger.info("getJobPriorityList()");
		return jobPriorityDao.findAll(Sort.by("jobPriority"));
	}
	
	public JobPriority getJobPriority(String jobPriorityId){
		logger.info("getJobPriority({})", jobPriorityId);
		Optional<JobPriority> jobPriority = jobPriorityDao.findById(jobPriorityId);
		if(jobPriority.isPresent()) {
			return jobPriority.get();
		}
		
		return null;
	}
	
	public boolean jobPriorityExists(String jobPriorityId) {
		JobPriority jobPriorityExisting = getJobPriority(jobPriorityId);
		return (jobPriorityExisting != null);
	}
	
	public JobPriority addJobPriority(JobPriority jobPriority) {
		logger.info("addJobPriority({})", jobPriority);
		jobPriority.setDateCrt(new Date());
		 
	    return jobPriorityDao.save(jobPriority);
	}
	
	public void deleteJobPriority(String jobPriorityId) {
		logger.info("deleteJobPriority({})", jobPriorityId);
		jobPriorityDao.deleteById(jobPriorityId);
	}
	
	public JobPriority updateJobPriority(JobPriority jobPriority) {
		logger.info("updateJobPriority({})", jobPriority);
		jobPriority.setDateMod(new Date());
		
		return jobPriorityDao.save(jobPriority);
	}
}
