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
import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.JobPriority;

@Service
@Transactional
public class JobPriorityService {
	
	Logger logger = LogManager.getLogger(JobPriorityService.class);
	
	@Autowired
	private JobPriorityDao jobPriorityDao;
	
	@Autowired
	private UserService userService;

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
	
	public JobPriority addJobPriority(JobPriority jobPriority) throws InvalidDataException {
		logger.info("addJobPriority({})", jobPriority);
		
		if(jobPriorityExists(jobPriority.getJobPriority())) {
			throw new InvalidDataException("JobPriority already exists");
		}
		
		jobPriority.setUserCrt(userService.getCurrentUserId());
		jobPriority.setDateCrt(new Date());
		 
	    return jobPriorityDao.save(jobPriority);
	}
	
	public void deleteJobPriority(String jobPriorityId) throws InvalidDataException {
		logger.info("deleteJobPriority({})", jobPriorityId);
		
		if(!jobPriorityExists(jobPriorityId)) {
			throw new InvalidDataException("JobPriority does not exist");
		}
		
		jobPriorityDao.deleteById(jobPriorityId);
	}
	
	public JobPriority updateJobPriority(JobPriority jobPriority) throws InvalidDataException {
		logger.info("updateJobPriority({})", jobPriority);
		
		if(!jobPriorityExists(jobPriority.getJobPriority())) {
			throw new InvalidDataException("JobPriority does not exist");
		}

		jobPriority.setUserMod(userService.getCurrentUserId());
		jobPriority.setDateMod(new Date());
		
		return jobPriorityDao.save(jobPriority);
	}
}
