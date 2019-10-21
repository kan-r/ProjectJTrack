/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.JobTypeDao;
import com.jtrack.model.JobType;
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
public class JobTypeService {
    
    @Autowired
    private JobTypeDao jobTypeDao;
    
    public List<JobType> getAll() {
        return jobTypeDao.getAll();
    }
    
    public JobType get(String jobType) {
        return jobTypeDao.get(jobType);
    }
    
    public void add(JobType jobType) {
        jobTypeDao.add(jobType);
    }
    
    public void delete(String jobType) {
        jobTypeDao.delete(jobType);
    }
    
    public void update(JobType jobType) {
        jobTypeDao.update(jobType);
    }
}