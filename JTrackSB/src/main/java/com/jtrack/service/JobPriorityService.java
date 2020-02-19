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
	
	public JobPriority addJobPriority(JobPriority jobPriority) throws InvalidDataException {
		logger.info("addJobPriority({})", jobPriority);
		
		if(jobPriority.getJobPriority() == null || jobPriority.getJobPriority().isEmpty()) {
			throw new InvalidDataException("Job Priority is required");
		}
		
		if(getJobPriority(jobPriority.getJobPriority()) != null) {
			throw new InvalidDataException("Job Priority already exists");
		}
		
		jobPriority.setDateCrt(new Date());
		jobPriority.setUserCrt(userService.getCurrentUserId());
		 
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
		
		JobPriority jobPriorityOld = getJobPriority(jobPriority.getJobPriority());
		
		jobPriority.setDateCrt(jobPriorityOld.getDateCrt());
		jobPriority.setUserCrt(jobPriorityOld.getUserCrt());
		
		jobPriority.setDateMod(new Date());
		jobPriority.setUserMod(userService.getCurrentUserId());
		
		return jobPriorityDao.save(jobPriority);
	}
}
