package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.UserDao;
import com.jtrack.model.User;

@Service
@Transactional
public class UserService {
	
	Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;

	public List<User> getUserList(){
		logger.info("getUserList()");
		return userDao.findAll();
	}
	
	public User getUser(String userId){
		logger.info("getUser({})", userId);
		Optional<User> user = userDao.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		
		return null;
	}
	
	public boolean userExists(String userId) {
		User userExisting = getUser(userId);
		return (userExisting != null);
	}
	
	public User addUser(User user) {
		logger.info("addUser({})", user);
		user.setDateCrt(new Date());
		 
	    return userDao.save(user);
	}
	
	public void deleteUser(String userId) {
		logger.info("deleteUser({})", userId);
		userDao.deleteById(userId);
	}
	
	public User updateUser(User user) {
		logger.info("updateUser({})", user);
		user.setDateMod(new Date());
		
		return userDao.save(user);
	}
}
