/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.TimesheetCode;
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
public class TimesheetCodeDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<TimesheetCode> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM TimesheetCode").list();
    }
    
    public TimesheetCode get(String timesheetCode) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (TimesheetCode) session.get(TimesheetCode.class, timesheetCode);
    }
    
    public void add(TimesheetCode timesheetCode) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        timesheetCode.setDateCrt(new Date());
        timesheetCode.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(timesheetCode);
    }
    
    public void delete(String timesheetCode) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        TimesheetCode oTimesheetCode = (TimesheetCode) session.get(TimesheetCode.class, timesheetCode);

        // Delete 
        session.delete(oTimesheetCode);
    }
    
    public void update(TimesheetCode timesheetCode) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        TimesheetCode oTimesheetCode = (TimesheetCode) session.get(TimesheetCode.class, timesheetCode.getTimesheetCode());

        oTimesheetCode.setTimesheetCodeDesc(timesheetCode.getTimesheetCodeDesc());
        oTimesheetCode.setActive(timesheetCode.getActive());
        oTimesheetCode.setDateMod(new Date());
        oTimesheetCode.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oTimesheetCode);
    }
}