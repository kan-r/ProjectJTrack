/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.dao;

import com.jtrack.model.Users;
import com.jtrack.service.UsersService;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;


/**
 *
 * @author Kan
 */
@Repository
public class UsersDao {
	
	final static Logger logger = Logger.getLogger(UsersDao.class);
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<Users> getAll() {
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        return session.createQuery("FROM Users").list();
    }
    
    public Users get(String userId) {
    	
    	logger.info("User ID: " + userId);
        
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
                
        return (Users) session.get(Users.class, userId);
    }
    
    public void add(Users users) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        
        users.setDateCrt(new Date());
        users.setUserCrt(UsersService.user.getUserId());
        
        // Save
        session.save(users);
    }
    
    public void delete(String userId) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record first
        Users oUsers = (Users) session.get(Users.class, userId);

        // Delete 
        session.delete(oUsers);
    }
    
    public void update(Users users) {
		
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing record
        Users oUsers = (Users) session.get(Users.class, users.getUserId());

        oUsers.setFirstName(users.getFirstName());
        oUsers.setLastName(users.getLastName());
        oUsers.setActive(users.getActive());
        oUsers.setDateMod(new Date());
        oUsers.setUserMod(UsersService.user.getUserId());

        // Save updates
        session.save(oUsers);
    }
}