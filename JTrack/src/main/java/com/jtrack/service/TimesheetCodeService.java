/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.TimesheetCodeDao;
import com.jtrack.model.TimesheetCode;
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
public class TimesheetCodeService {
    
    @Autowired
    private TimesheetCodeDao timesheetCodeDao;
    
    public List<TimesheetCode> getAll() {
        return timesheetCodeDao.getAll();
    }
    
    public TimesheetCode get(String timesheetCode) {
        return timesheetCodeDao.get(timesheetCode);
    }
    
    public void add(TimesheetCode timesheetCode) {
        timesheetCodeDao.add(timesheetCode);
    }
    
    public void delete(String timesheetCode) {
        timesheetCodeDao.delete(timesheetCode);
    }
    
    public void update(TimesheetCode timesheetCode) {
        timesheetCodeDao.update(timesheetCode);
    }
}