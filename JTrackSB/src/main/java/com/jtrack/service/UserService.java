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
	
	public static User currentUser = new User("KAN", "Kan", "Ranganathan");
	
	@Autowired
	private UserDao userDao;

	public List<User> getUserAll(){
		logger.info("getUsers()");
		return userDao.findAll();
	}
	
	public User getUser(String userId){
		logger.info("getUser({})", userId);
		Optional<User> user = userDao.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		
		return new User();
	}
	
	public User addUser(User user) {
		logger.info("addUser({})", user);
		user.setDateCrt(new Date());
	    user.setUserCrt(UserService.currentUser.getUserId());
		 
	    return userDao.save(user);
	}
	
	public void deleteUser(String userId) {
		logger.info("deleteUser({})", userId);
		userDao.deleteById(userId);
	}
	
	public void deleteUser(User user) {
		logger.info("deleteUser({})", user);
		userDao.delete(user);
	}
	
	public User updateUser(User user) {
		logger.info("updateUser({})", user);
		user.setDateMod(new Date());
		user.setUserMod(UserService.currentUser.getUserId());
		
		return userDao.save(user);
	}

}
