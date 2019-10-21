/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.JobDao;
import com.jtrack.model.Job;
import com.jtrack.model.JobSO;
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
public class JobService {
    
    @Autowired
    private JobDao jobsDao;
    
    public List<Job> getAll() {
        return jobsDao.getAll();
    }
    
    public List<Job> getPage(int pageNumber) {
        return jobsDao.getPage(pageNumber);
    }
    
    public List<Job> getPage(JobSO jobSO, int pageNumber) {
        return jobsDao.getPage(jobSO, pageNumber);
    }
    
    public int getNumberOfPages(){
        return jobsDao.getNumberOfPages();
    }
    
    public List<Job> get(String jobType) {
        return jobsDao.get(jobType);
    }
    
    public Job get(long jobNo) {
        return jobsDao.get(jobNo);
    }
    
    public void add(Job jobs) {
        jobsDao.add(jobs);
    }
    
    public void delete(long jobNo) {
        jobsDao.delete(jobNo);
    }
    
    public void update(Job jobs) {
        jobsDao.update(jobs);
    }
}