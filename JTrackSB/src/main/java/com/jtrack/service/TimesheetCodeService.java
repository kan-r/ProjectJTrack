package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.TimesheetCodeDao;
import com.jtrack.model.TimesheetCode;

@Service
@Transactional
public class TimesheetCodeService {
	
	Logger logger = LogManager.getLogger(TimesheetCodeService.class);
	
	@Autowired
	private TimesheetCodeDao timesheetCodeDao;

	public List<TimesheetCode> getTimesheetCodeAll(){
		logger.info("getTimesheetCodeAll()");
		return timesheetCodeDao.findAll();
	}
	
	public TimesheetCode getTimesheetCode(String timesheetCodeId){
		logger.info("getTimesheetCode({})", timesheetCodeId);
		Optional<TimesheetCode> timesheetCode = timesheetCodeDao.findById(timesheetCodeId);
		if(timesheetCode.isPresent()) {
			return timesheetCode.get();
		}
		
		return new TimesheetCode();
	}
	
	public TimesheetCode addTimesheetCode(TimesheetCode timesheetCode) {
		logger.info("addTimesheetCode({})", timesheetCode);
		timesheetCode.setDateCrt(new Date());
		timesheetCode.setUserCrt(UserService.currentUser.getUserId());
		 
	    return timesheetCodeDao.save(timesheetCode);
	}
	
	public void deleteTimesheetCode(String timesheetCodeId) {
		logger.info("deleteTimesheetCode({})", timesheetCodeId);
		timesheetCodeDao.deleteById(timesheetCodeId);
	}
	
	public void deleteTimesheetCode(TimesheetCode timesheetCode) {
		logger.info("deleteTimesheetCode({})", timesheetCode);
		timesheetCodeDao.delete(timesheetCode);
	}
	
	public TimesheetCode updateTimesheetCode(TimesheetCode timesheetCode) {
		logger.info("updateTimesheetCode({})", timesheetCode);
		timesheetCode.setDateMod(new Date());
		timesheetCode.setUserMod(UserService.currentUser.getUserId());
		
		return timesheetCodeDao.save(timesheetCode);
	}

}
