/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.JobPriorityDao;
import com.jtrack.model.JobPriority;
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
public class JobPriorityService {
    
    @Autowired
    private JobPriorityDao jobPriorityDao;
    
    public List<JobPriority> getAll() {
        return jobPriorityDao.getAll();
    }
    
    public JobPriority get(String jobPriority) {
        return jobPriorityDao.get(jobPriority);
    }
    
    public void add(JobPriority jobPriority) {
        jobPriorityDao.add(jobPriority);
    }
    
    public void delete(String jobPriority) {
        jobPriorityDao.delete(jobPriority);
    }
    
    public void update(JobPriority jobPriority) {
        jobPriorityDao.update(jobPriority);
    }
}
