/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.TimesheetDao;
import com.jtrack.model.Timesheet;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kan
 */
@Service
@Transactional
public class TimesheetService {
    
    @Autowired
    private TimesheetDao timesheetDao;
    
    public List<Timesheet> getAll() {
        return timesheetDao.getAll();
    }
    
    public List<Timesheet> getAll(String userId, Date workedDateFrom, Date workedDateTo) {
        return timesheetDao.getAll(userId, workedDateFrom, workedDateTo);
    }
    
    public Timesheet get(String timesheetId) {
        return timesheetDao.get(timesheetId);
    }
    
    public Timesheet get(String userId, long jobNo, Date workedDate) {
        return timesheetDao.get(userId, jobNo, workedDate);
    }
    
    public void add(Timesheet timesheet) {
        timesheetDao.add(timesheet);
    }
    
    public void delete(String timesheetId) {
        timesheetDao.delete(timesheetId);
    }
    
    public void update(Timesheet timesheet) {
        timesheetDao.update(timesheet);
    }
}