/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.Timesheet;
import com.jtrack.service.UsersService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kan
 */
@Repository
public class TimesheetDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<Timesheet> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM Timesheet").list();
    }
    
    public List<Timesheet> getAll(String userId, Date workedDateFrom, Date workedDateTo) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        String where = "";
        
        if(userId != null && !userId.isEmpty()){
            where += "t.userId = :userId";
        }
        
        if(workedDateFrom != null){
            if(!where.isEmpty()){
                where += " and ";
            }
            
            where += "t.workedDate >= :workedDateFrom";
        }
        
        if(workedDateTo != null){
            if(!where.isEmpty()){
                where += " and ";
            }
            
            where += "t.workedDate <= :workedDateTo";
        }
        
        if(!where.isEmpty()){
            where = " where " + where;
        }
               
        Query query = session.createQuery("FROM Timesheet t" + where);
        
        if(userId != null && !userId.isEmpty()){
            query.setString("userId", userId);
        }
        
        if(workedDateFrom != null){
            query.setDate("workedDateFrom", workedDateFrom);
        }
        
        if(workedDateTo != null){
            query.setDate("workedDateTo", workedDateTo);
        }
        
        return query.list();
    }
    
    public Timesheet get(String timesheetId) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (Timesheet) session.get(Timesheet.class, timesheetId);
    }
    
    public Timesheet get(String userId, long jobNo, Date workedDate) {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        Query query = session.createQuery("FROM Timesheet t where t.userId = :userId and t.jobNo = :jobNo and t.workedDate = :workedDate");
        query.setString("userId", userId);
        query.setLong("jobNo", jobNo);
        query.setDate("workedDate", workedDate);
        
        List<Timesheet> lst = query.list();
        
        if(lst == null || lst.size() == 0){
            return null;
        }else{
            return lst.get(0);
        }
    }
 
    public void add(Timesheet timesheet) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        SimpleDateFormat dtFmt = new SimpleDateFormat("yyyyMMdd");
        
        String timesheetId = timesheet.getUserId() + "-" + timesheet.getJobNo() + "-" + dtFmt.format(timesheet.getWorkedDate());
        
        timesheet.setTimesheetId(timesheetId);
        timesheet.setDateCrt(new Date());
        timesheet.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(timesheet);
    }
    
    public void delete(String timesheetId) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        Timesheet oTimesheet = (Timesheet) session.get(Timesheet.class, timesheetId);

        // Delete 
        session.delete(oTimesheet);
    }
    
    public void update(Timesheet timesheet) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        Timesheet oTimesheet = (Timesheet) session.get(Timesheet.class, timesheet.getTimesheetId());

        oTimesheet.setWorkedHrs(timesheet.getWorkedHrs());
        oTimesheet.setTimesheetCode(timesheet.getTimesheetCode());
        oTimesheet.setActive(timesheet.getActive());
        oTimesheet.setDateMod(new Date());
        oTimesheet.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oTimesheet);
    }
}