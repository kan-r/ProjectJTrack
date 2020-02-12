package com.jtrack.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.JobDao;
import com.jtrack.model.Job;
import com.jtrack.model.JobSO;

@Service
@Transactional
public class JobService {
	
	Logger logger = LogManager.getLogger(JobService.class);
	
	@Autowired
	private JobDao jobDao;
	
	@PersistenceContext
	EntityManager entityManager;

	public List<Job> getJobList(){
		logger.info("getJobList()");
		return jobDao.findAll(Sort.by("jobNo"));
	}
	
	public List<Job> getJobList(String jobType) {
		logger.info("getJobList({})", jobType);
		return jobDao.findByJobType(jobType, Sort.by("jobNo"));
	}
	
	private List<Job> getChildJobList(Long parentJobNo) {
		logger.info("getChildJobList({})", parentJobNo);
		return jobDao.findByParentJobNo(parentJobNo, Sort.by("jobNo"));
	}
	
	private List<Job> getChildJobList(Long parentJobNo, JobSO jobSO) {
		
		logger.info("getChildJobList({}, {})", parentJobNo, jobSO);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Job> criteriaQuery = criteriaBuilder.createQuery(Job.class);
		Root<Job> job = criteriaQuery.from(Job.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(criteriaBuilder.and(criteriaBuilder.equal(job.get("parentJobNo"), parentJobNo)));
		
		if(!jobSO.getJobNameChild().isEmpty()){
			predicates.add(criteriaBuilder.and(criteriaBuilder.like(job.get("jobName"), jobSO.getJobNameChild())));
        }
		
		if(!jobSO.getJobTypeChild().isEmpty()){
			predicates.add(criteriaBuilder.and(criteriaBuilder.equal(job.get("jobType"), jobSO.getJobTypeChild())));
        }
        
        if(!jobSO.getJobStatusChild().isEmpty()){
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(job.get("jobStatus"), jobSO.getJobStatusChild())));
        }
        
        if(!jobSO.getAssignedToChild().isEmpty()){
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(job.get("assignedTo"), jobSO.getAssignedToChild())));
        }
		
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        criteriaQuery.orderBy(criteriaBuilder.asc(job.get("jobNo")));
        TypedQuery<Job> query = entityManager.createQuery(criteriaQuery);
        List<Job> result = query.getResultList();
        
		return result;
	}
	
	public List<Job> getJobList(JobSO jobSO) {
		
		logger.info("getJobList({})", jobSO);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Job> criteriaQuery = criteriaBuilder.createQuery(Job.class);
		Root<Job> job = criteriaQuery.from(Job.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(job.get("parentJobNo"))));
		
		if(!jobSO.getJobName().isEmpty()){
			predicates.add(criteriaBuilder.and(criteriaBuilder.like(job.get("jobName"), jobSO.getJobName())));
        }
		
		if(!jobSO.getJobType().isEmpty()){
			predicates.add(criteriaBuilder.and(criteriaBuilder.equal(job.get("jobType"), jobSO.getJobType())));
        }
        
        if(!jobSO.getJobStatus().isEmpty()){
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(job.get("jobStatus"), jobSO.getJobStatus())));
        }
        
        if(!jobSO.getAssignedTo().isEmpty()){
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(job.get("assignedTo"), jobSO.getAssignedTo())));
        }
		
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        criteriaQuery.orderBy(criteriaBuilder.asc(job.get("jobNo")));
        TypedQuery<Job> query = entityManager.createQuery(criteriaQuery);
        List<Job> result = query.getResultList();
        
        if(jobSO.isIncludeChildJobs()) {
        	List<Job> res = new ArrayList<Job>();
        	
        	for(int i = 0; i < result.size(); i++){
        		Job pJ = result.get(i);
        		res.add(pJ);
        		
        		if(pJ.getParentJobNo() == null) {
        			List<Job> cJList = getChildJobList(pJ.getJobNo(), jobSO);
        			for(int j = 0; j < cJList.size(); j++){
        				if(!res.contains(cJList.get(j))) {
        					res.add(cJList.get(j));
        				}
        			}
        		}
        	}
        	
        	return res;
        }else {
        	return result;
        }
	}
	
	public Job getJob(long jobNo){
		logger.info("getJob({})", jobNo);
		return jobDao.findByJobNo(jobNo);
	}
	
	public boolean jobExists(long jobNo) {
		Job jobExisting = getJob(jobNo);
		return (jobExisting != null);
	}
	
	public boolean childJobExists(long jobNo) {
		List<Job> jobList = getChildJobList(jobNo);
		return (jobList.size() > 0);
	}
	
	public boolean timesheetExists(long jobNo) {
		return (jobDao.getTimesheetCount(jobNo) > 0);
	}
	
	public Job addJob(Job job) {
		logger.info("addJob({})", job);
		
		job.setDateCrt(new Date());
		 
		Job j = jobDao.save(job);
		refreshParentJob(job);
		
		return j;
	}
	
	public void deleteJob(long jobNo) {
		logger.info("deleteJob({})", jobNo);
		
		Job job = getJob(jobNo);
		jobDao.delete(job);
		refreshParentJob(job);
	}
	
	public Job updateJob(Job job) {
		logger.info("updateJob({})", job);
		
		job.setDateMod(new Date());
		
		Job j = jobDao.save(job);
		refreshParentJob(job);
		
		return j;
	}
	
	private void refreshParentJob(Job job) {
		logger.info("updateParentJob({})", job);
		
		if(job.getParentJobNo() == null) {
			return;
		}
			
		try {
			jobDao.refreshParentJob(job.getParentJobNo());
		}catch(Exception e) {
			
		}
	}
}
