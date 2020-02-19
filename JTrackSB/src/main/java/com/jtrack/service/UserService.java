package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.UserDao;
import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.User;

@Service
@Transactional
public class UserService {
	
	Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;

	public List<User> getUserList(){
		logger.info("getUserList()");
		return userDao.findAll(Sort.by("userId"));
	}
	
	public User getUser(String userId){
		logger.info("getUser({})", userId);
		Optional<User> user = userDao.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		
		return null;
	}
	
	public User addUser(User user) throws InvalidDataException {
		logger.info("addUser({})", user);
		
		if(user.getUserId() == null || user.getUserId().isEmpty()) {
			throw new InvalidDataException("User ID is required");
		}
		
		if(user.getPword() == null || user.getPword().isEmpty()) {
			throw new InvalidDataException("Password is required");
		}
		
		if(getUser(user.getUserId()) != null) {
			throw new InvalidDataException("User ID already exists");
		}
		
		user.setDateCrt(new Date());
	    user.setUserCrt(getCurrentUserId());
		 
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
		
		User userOld = getUser(user.getUserId());
		
		if(userOld != null) {
			user.setDateCrt(userOld.getDateCrt());
			user.setUserCrt(userOld.getUserCrt());
		}
		
		user.setDateMod(new Date());
		user.setUserMod(getCurrentUserId());
		
		return userDao.save(user);
	}
	
	public String getCurrentUserId() {
		String user = "";
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
			user = authentication.getName();
		}
		
		return user.toUpperCase();
	}
	
	public User getCurrentUser() {
		return getUser(getCurrentUserId());
	}
	
	public boolean isCurrentUserAdmin() {
		return "ADMIN".equals(getCurrentUserId());
	}

}
