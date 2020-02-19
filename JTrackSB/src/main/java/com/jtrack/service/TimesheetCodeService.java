package com.jtrack.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.TimesheetCodeDao;
import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.TimesheetCode;

@Service
@Transactional
public class TimesheetCodeService {
	
	Logger logger = LogManager.getLogger(TimesheetCodeService.class);
	
	@Autowired
	private TimesheetCodeDao timesheetCodeDao;
	
	@Autowired
	private UserService userService;

	public List<TimesheetCode> getTimesheetCodeList(){
		logger.info("getTimesheetCodeList()");
		return timesheetCodeDao.findAll(Sort.by("timesheetCode"));
	}
	
	public TimesheetCode getTimesheetCode(String timesheetCodeId){
		logger.info("getTimesheetCode({})", timesheetCodeId);
		Optional<TimesheetCode> timesheetCode = timesheetCodeDao.findById(timesheetCodeId);
		if(timesheetCode.isPresent()) {
			return timesheetCode.get();
		}
		
		return null;
	}
	
	public TimesheetCode addTimesheetCode(TimesheetCode timesheetCode) throws InvalidDataException {
		logger.info("addTimesheetCode({})", timesheetCode);
		
		if(timesheetCode.getTimesheetCode() == null || timesheetCode.getTimesheetCode().isEmpty()) {
			throw new InvalidDataException("Timesheet Code is required");
		}
		
		if(getTimesheetCode(timesheetCode.getTimesheetCode()) != null) {
			throw new InvalidDataException("Timesheet Code already exists");
		}
		
		timesheetCode.setDateCrt(new Date());
		timesheetCode.setUserCrt(userService.getCurrentUserId());
		 
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
		
		TimesheetCode timesheetCodeOld = getTimesheetCode(timesheetCode.getTimesheetCode());
		
		timesheetCode.setDateCrt(timesheetCodeOld.getDateCrt());
		timesheetCode.setUserCrt(timesheetCodeOld.getUserCrt());
		
		timesheetCode.setDateMod(new Date());
		timesheetCode.setUserMod(userService.getCurrentUserId());
		
		return timesheetCodeDao.save(timesheetCode);
	}

}
