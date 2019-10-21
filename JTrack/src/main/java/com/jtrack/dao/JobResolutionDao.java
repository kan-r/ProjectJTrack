/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.JobResolution;
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
public class JobResolutionDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<JobResolution> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM JobResolution").list();
    }
    
    public JobResolution get(String jobResolution) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (JobResolution) session.get(JobResolution.class, jobResolution);
    }
    
    public void add(JobResolution jobResolution) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        jobResolution.setDateCrt(new Date());
        jobResolution.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(jobResolution);
    }
    
    public void delete(String jobResolution) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        JobResolution oJobResolution = (JobResolution) session.get(JobResolution.class, jobResolution);

        // Delete 
        session.delete(oJobResolution);
    }
    
    public void update(JobResolution jobResolution) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        JobResolution oJobResolution = (JobResolution) session.get(JobResolution.class, jobResolution.getJobResolution());

        oJobResolution.setJobResolutionDesc(jobResolution.getJobResolutionDesc());
        oJobResolution.setActive(jobResolution.getActive());
        oJobResolution.setDateMod(new Date());
        oJobResolution.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oJobResolution);
    }
}
