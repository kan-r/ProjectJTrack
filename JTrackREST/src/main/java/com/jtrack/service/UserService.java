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
		
		if(!isCurrentUserAdmin()) {
			throw new InvalidDataException("No acess to Create User");
    	}
		
		if(userInvalid(user.getUserId())) {
			throw new InvalidDataException("User is required");
		}
		
		if(pwordInvalid(user.getPword())) {
			throw new InvalidDataException("Password is required");
		}
		
		if(userExists(user.getUserId())) {
			throw new InvalidDataException("User already exists");
		}
		
		user.setUserCrt(getCurrentUserId());
		user.setDateCrt(new Date());
		 
	    return userDao.save(user);
	}
	
	public void deleteUser(String userId) throws InvalidDataException {
		logger.info("deleteUser({})", userId);
		
		if(!isCurrentUserAdmin()) {
			throw new InvalidDataException("No acess to Delete User");
    	}
		
		if(!userExists(userId)) {
			throw new InvalidDataException("User does not exist");
		}
		
		userDao.deleteById(userId);
	}
	
	public User updateUser(User user) throws InvalidDataException {
		logger.info("updateUser({})", user);
		
		if(!isCurrentUserAdmin()) {
			throw new InvalidDataException("No acess to Update User");
    	}
		
		if(pwordInvalid(user.getPword())) {
			throw new InvalidDataException("Password is required");
		}
		
		if(!userExists(user.getUserId())) {
			throw new InvalidDataException("User does not exist");
		}
		
		user.setUserMod(getCurrentUserId());
		user.setDateMod(new Date());
		
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
	
	private boolean isCurrentUserAdmin() {
		return "ADMIN".equals(getCurrentUserId());
	}
	
	private boolean userExists(String userId) {
		User userExisting = getUser(userId);
		return (userExisting != null);
	}
	
	private boolean userInvalid(String userId) {
		return (userId == null || userId.trim().isEmpty());
	}
	
	private boolean pwordInvalid(String pword) {
		return (pword == null || pword.trim().isEmpty());
	}
}
