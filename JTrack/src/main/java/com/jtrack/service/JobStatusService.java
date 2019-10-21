/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.JobStatusDao;
import com.jtrack.model.JobStatus;
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
public class JobStatusService {
    
    @Autowired
    private JobStatusDao jobStatusDao;
    
    public List<JobStatus> getAll() {
        return jobStatusDao.getAll();
    }
    
    public JobStatus get(String jobStatus) {
        return jobStatusDao.get(jobStatus);
    }
    
    public void add(JobStatus jobStatus) {
        jobStatusDao.add(jobStatus);
    }
    
    public void delete(String jobStatus) {
        jobStatusDao.delete(jobStatus);
    }
    
    public void update(JobStatus jobStatus) {
        jobStatusDao.update(jobStatus);
    }
}