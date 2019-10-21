/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.JobType;
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
public class JobTypeDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<JobType> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM JobType").list();
    }
    
    public JobType get(String jobType) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (JobType) session.get(JobType.class, jobType);
    }
    
    public void add(JobType jobType) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        jobType.setDateCrt(new Date());
        jobType.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(jobType);
    }
    
    public void delete(String jobType) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        JobType oJobType = (JobType) session.get(JobType.class, jobType);

        // Delete 
        session.delete(oJobType);
    }
    
    public void update(JobType jobType) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        JobType oJobType = (JobType) session.get(JobType.class, jobType.getJobType());

        oJobType.setJobTypeDesc(jobType.getJobTypeDesc());
        oJobType.setActive(jobType.getActive());
        oJobType.setDateMod(new Date());
        oJobType.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oJobType);
    }
}