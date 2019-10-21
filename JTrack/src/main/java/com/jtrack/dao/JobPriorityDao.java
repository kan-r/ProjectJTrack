/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.JobPriority;
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
public class JobPriorityDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<JobPriority> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM JobPriority").list();
    }
    
    public JobPriority get(String jobPriority) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (JobPriority) session.get(JobPriority.class, jobPriority);
    }
    
    public void add(JobPriority jobPriority) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        jobPriority.setDateCrt(new Date());
        jobPriority.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(jobPriority);
    }
    
    public void delete(String jobPriority) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        JobPriority oJobPriority = (JobPriority) session.get(JobPriority.class, jobPriority);

        // Delete 
        session.delete(oJobPriority);
    }
    
    public void update(JobPriority jobPriority) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        JobPriority oJobPriority = (JobPriority) session.get(JobPriority.class, jobPriority.getJobPriority());

        oJobPriority.setJobPriorityDesc(jobPriority.getJobPriorityDesc());
        oJobPriority.setActive(jobPriority.getActive());
        oJobPriority.setDateMod(new Date());
        oJobPriority.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oJobPriority);
    }
}