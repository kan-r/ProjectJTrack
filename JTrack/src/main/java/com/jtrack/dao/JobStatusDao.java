/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.JobStatus;
import com.jtrack.service.UsersService;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kan
 */
@Repository
public class JobStatusDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<JobStatus> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM JobStatus").list();
    }
    
    public JobStatus get(String jobStatus) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (JobStatus) session.get(JobStatus.class, jobStatus);
    }
    
    public void add(JobStatus jobStatus) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        jobStatus.setDateCrt(new Date());
        jobStatus.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(jobStatus);
    }
    
    public void delete(String jobStatus) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        JobStatus oJobStatus = (JobStatus) session.get(JobStatus.class, jobStatus);

        // Delete 
        session.delete(oJobStatus);
    }
    
    public void update(JobStatus jobStatus) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        JobStatus oJobStatus = (JobStatus) session.get(JobStatus.class, jobStatus.getJobStatus());

        oJobStatus.setJobStatusDesc(jobStatus.getJobStatusDesc());
        oJobStatus.setActive(jobStatus.getActive());
        oJobStatus.setDateMod(new Date());
        oJobStatus.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oJobStatus);
    }
}