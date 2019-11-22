package com.jtrack.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.JobDao;
import com.jtrack.model.Job;

@Service
@Transactional
public class JobService {
	
	Logger logger = LogManager.getLogger(JobService.class);
	
	@Autowired
	private JobDao jobDao;

	public List<Job> getJobList(){
		logger.info("getJobList()");
		return jobDao.findAll();
	}
	
	public List<Job> getJobList(String jobType) {
		logger.info("getJobList({})", jobType);
		return jobDao.findByJobType(jobType);
	}
	
	public Job getJob(long jobNo){
		logger.info("getJob({})", jobNo);
		return jobDao.findByJobNo(jobNo);
	}
	
	public boolean jobExists(long jobNo) {
		Job jobExisting = getJob(jobNo);
		return (jobExisting != null);
	}
	
	public Job addJob(Job job) {
		logger.info("addJob({})", job);
		job.setDateCrt(new Date());
		 
	    return jobDao.save(job);
	}
	
	public void deleteJob(long jobNo) {
		logger.info("deleteJob({})", jobNo);
		jobDao.deleteByJobNo(jobNo);
	}
	
	public Job updateJob(Job job) {
		logger.info("updateJob({})", job);
		job.setDateMod(new Date());
		
		return jobDao.save(job);
	}
}
