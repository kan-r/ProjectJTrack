/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.JobResolutionDao;
import com.jtrack.model.JobResolution;
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
public class JobResolutionService {
    
    @Autowired
    private JobResolutionDao jobResolutionDao;
    
    public List<JobResolution> getAll() {
        return jobResolutionDao.getAll();
    }
    
    public JobResolution get(String jobResolution) {
        return jobResolutionDao.get(jobResolution);
    }
    
    public void add(JobResolution jobResolution) {
        jobResolutionDao.add(jobResolution);
    }
    
    public void delete(String jobResolution) {
        jobResolutionDao.delete(jobResolution);
    }
    
    public void update(JobResolution jobResolution) {
        jobResolutionDao.update(jobResolution);
    }
}