/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.dao.UsersDao;
import com.jtrack.model.Users;
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
public class UsersService {
    
    public static Users user = new Users("D261288", "Kan", "Ranganathan");
    
    @Autowired
    private UsersDao jobTypeDao;
    
    public List<Users> getAll() {
        return jobTypeDao.getAll();
    }
    
    public Users get(String userId) {
        return jobTypeDao.get(userId);
    }
    
    public void add(Users jobType) {
        jobTypeDao.add(jobType);
    }
    
    public void delete(String userId) {
        jobTypeDao.delete(userId);
    }
    
    public void update(Users jobType) {
        jobTypeDao.update(jobType);
    }
}
