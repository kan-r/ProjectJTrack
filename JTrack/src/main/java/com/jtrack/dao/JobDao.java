/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.Job;
import com.jtrack.model.JobSO;
import com.jtrack.service.UsersService;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kan
 */
@Repository
public class JobDao {
    
    private final int C_PAGE_SIZE = 2;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<Job> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM Job J ORDER BY J.parentJobNo, J.jobNo").list();
    }
    
    public List<Job> getPage(int pageNumber) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.createQuery("FROM Job J ORDER BY J.parentJobNo, J.jobNo");
        query = query.setFirstResult(C_PAGE_SIZE * (pageNumber - 1));
        query.setMaxResults(C_PAGE_SIZE);
    
        return query.list();
    }
    
     public List<Job> getPage(JobSO jobSO, int pageNumber) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        String where = "";
        
        if(!jobSO.getJobName().isEmpty()){
            where = " where j.jobName like :jobName";
        }
        
        if(!jobSO.getJobType().isEmpty()){
            if(where.isEmpty()){
                where = " where ";
            }else{
                where += " and ";
            }
            
            where += "j.jobType = :jobType";
        }
        
        if(!jobSO.getJobStatus().isEmpty()){
            if(where.isEmpty()){
                where = " where ";
            }else{
                where += " and ";
            }
            
            where += "j.jobStatus = :jobStatus";
        }
        
        if(!jobSO.getAssignedTo().isEmpty()){
            if(where.isEmpty()){
                where = " where ";
            }else{
                where += " and ";
            }
            
            where += "j.assignedTo = :assignedTo";
        }
        
        Query query = session.createQuery("from Job j" + where);
        
        if(!jobSO.getJobName().isEmpty()){
            query.setParameter("jobName", jobSO.getJobName());
        }
        
        if(!jobSO.getJobType().isEmpty()){
            query.setParameter("jobType", jobSO.getJobType());
        }
        
        if(!jobSO.getJobStatus().isEmpty()){
            query.setParameter("jobStatus", jobSO.getJobStatus());
        }
        
        if(!jobSO.getAssignedTo().isEmpty()){
            query.setParameter("assignedTo", jobSO.getAssignedTo());
        }
            
        query = query.setFirstResult(C_PAGE_SIZE * (pageNumber - 1));
        query.setMaxResults(C_PAGE_SIZE);
    
        return query.list();
    }
    
    public int getNumberOfPages(){
         // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Job.class).setProjection(Projections.rowCount()).list().size();
//        return (Integer)session.createCriteria(Job.class).setProjection(Projections.rowCount()).uniqueResult();
    }
    
    public List<Job> get(String jobType) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM Job J where J.jobType = :jobType").setParameter("jobType", jobType).list();
    }
    
    public Job get(long jobNo) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (Job) session.get(Job.class, jobNo);
    }
    
    public void add(Job jobs) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        jobs.setDateCrt(new Date());
        jobs.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(jobs);
    }
    
    public void delete(long jobNo) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        Job oJobs = (Job) session.get(Job.class, jobNo);

        // Delete 
        session.delete(oJobs);
    }
    
    public void update(Job jobs) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        Job oJobs = (Job) session.get(Job.class, jobs.getJobNo());

        oJobs.setJobDesc(jobs.getJobDesc());
        oJobs.setActive(jobs.getActive());
        oJobs.setDateMod(new Date());
        oJobs.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oJobs);
    }
}