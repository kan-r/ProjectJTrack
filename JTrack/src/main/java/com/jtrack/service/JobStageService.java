/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.JobStageDao;
import com.jtrack.model.JobStage;
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
public class JobStageService {
    
    @Autowired
    private JobStageDao jobStageDao;
    
    public List<JobStage> getAll() {
        return jobStageDao.getAll();
    }
    
    public JobStage get(String jobStage) {
        return jobStageDao.get(jobStage);
    }
    
    public void add(JobStage jobStage) {
        jobStageDao.add(jobStage);
    }
    
    public void delete(String jobStage) {
        jobStageDao.delete(jobStage);
    }
    
    public void update(JobStage jobStage) {
        jobStageDao.update(jobStage);
    }
}