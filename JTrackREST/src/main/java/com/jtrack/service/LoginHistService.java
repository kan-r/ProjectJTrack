package com.jtrack.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.LoginHistDao;
import com.jtrack.model.LoginHist;

@Service
@Transactional
public class LoginHistService {
	
	Logger logger = LogManager.getLogger(LoginHistService.class);
	
	@Autowired
	private LoginHistDao loginHistDao;
	
	@Autowired
	private UserService userService;

	public List<LoginHist> getLoginHistList(){
		logger.info("getLoginHistList()");
		return loginHistDao.findAll(Sort.by("dateCrt"));
	}
	
	
	public LoginHist addLoginHist(LoginHist loginHist) {
		logger.info("addLoginHist({})", loginHist);
		
		if(loginHist.getUserId() == null || loginHist.getUserId().trim().isEmpty()) {
			loginHist.setUserId(userService.getCurrentUserId());
		}
		
		loginHist.setDateCrt(new Date());
		
		SimpleDateFormat dtFmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String histId = loginHist.getUserId() + "-" + loginHist.getIpAddr() + "-" + dtFmt.format(loginHist.getDateCrt());
  
        loginHist.setHistId(histId);
        
		 
	    return loginHistDao.save(loginHist);
	}
}
