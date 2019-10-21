/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.JobStage;
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
public class JobStageDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<JobStage> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM JobStage").list();
    }
    
    public JobStage get(String jobStage) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (JobStage) session.get(JobStage.class, jobStage);
    }
    
    public void add(JobStage jobStage) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        jobStage.setDateCrt(new Date());
        jobStage.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(jobStage);
    }
    
    public void delete(String jobStage) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        JobStage oJobStage = (JobStage) session.get(JobStage.class, jobStage);

        // Delete 
        session.delete(oJobStage);
    }
    
    public void update(JobStage jobStage) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        JobStage oJobStage = (JobStage) session.get(JobStage.class, jobStage.getJobStage());

        oJobStage.setJobStageDesc(jobStage.getJobStageDesc());
        oJobStage.setActive(jobStage.getActive());
        oJobStage.setDateMod(new Date());
        oJobStage.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oJobStage);
    }
}