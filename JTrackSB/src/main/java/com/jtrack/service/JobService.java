package com.jtrack.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.JobDao;
import com.jtrack.model.Job;
import com.jtrack.model.JobSearchObj;
import com.jtrack.model.Timesheet;

@Service
@Transactional
public class JobService {
	
	Logger logger = LogManager.getLogger(JobService.class);
	
	private final int C_PAGE_SIZE = 2;
	private int totalPages = 1;
	
	@Autowired
	private JobDao jobDao;

	public List<Job> getJobAll(){
		logger.info("getJobAll()");
		return jobDao.findAll();
	}
	
	public List<Job> getJobAll(String jobType) {
		logger.info("getJobAll({})", jobType);
		return jobDao.findByJobType(jobType);
	}
	
	public int getNumberOfPages() {
		return totalPages;
	}
	
	public List<Job> getJobPage(int pageNumber){
		logger.info("getJobPage({})", pageNumber);
		Page<Job> jobPage = jobDao.findAll(PageRequest.of(pageNumber, C_PAGE_SIZE));
		totalPages = jobPage.getTotalPages();
		return jobPage.getContent(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Job> getJobPage(JobSearchObj jobSO, int pageNumber) {
		
		logger.info("getJobPage({}, {})", jobSO, pageNumber);
		
		Page<Job> jobPage = jobDao.findAll(new Specification<Job>() {

			@Override
			public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				
				if(!jobSO.getJobName().isEmpty()){
		            predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("jobName"), jobSO.getJobName())));
		        }
		        
		        if(!jobSO.getJobType().isEmpty()){
		            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("jobType"), jobSO.getJobType())));
		        }
		        
		        if(!jobSO.getJobStatus().isEmpty()){
		            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("jobStatus"), jobSO.getJobStatus())));
		        }
		        
		        if(!jobSO.getAssignedTo().isEmpty()){
		            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("assignedTo"), jobSO.getAssignedTo())));
		        }
		        
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		}, PageRequest.of(pageNumber, C_PAGE_SIZE));
        
        totalPages = jobPage.getTotalPages();
		return jobPage.getContent(); 
    }
	
	public Job getJob(long jobNo){
		logger.info("getJob({})", jobNo);
		Job job = jobDao.findByJobNo(jobNo);
		
		if(job == null) {
			job = new Job();
		}
		
		return job;
	}
	
	public List<Job> getJob(String jobType){
		logger.info("getJob({})", jobType);
		return jobDao.findByJobType(jobType);
	}
	
	public Job addJob(Job job) {
		logger.info("addJob({})", job);
		job.setDateCrt(new Date());
		job.setUserCrt(UserService.currentUser.getUserId());
		 
	    return jobDao.save(job);
	}
	
	public void deleteJob(long jobNo) {
		logger.info("deleteJob({})", jobNo);
		jobDao.deleteByJobNo(jobNo);
	}
	
	public void deleteJob(Job job) {
		logger.info("deleteJob({})", job);
		jobDao.delete(job);
	}
	
	public Job updateJob(Job job) {
		logger.info("updateJob({})", job);
		job.setDateMod(new Date());
		job.setUserMod(UserService.currentUser.getUserId());
		
		return jobDao.save(job);
	}

}
