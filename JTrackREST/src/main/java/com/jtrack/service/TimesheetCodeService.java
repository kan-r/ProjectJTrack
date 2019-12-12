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
import com.jtrack.model.TimesheetCode;

@Service
@Transactional
public class TimesheetCodeService {
	
	Logger logger = LogManager.getLogger(TimesheetCodeService.class);
	
	@Autowired
	private TimesheetCodeDao timesheetCodeDao;

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
	
	public boolean timesheetCodeExists(String timesheetCodeId) {
		TimesheetCode timesheetCodeExisting = getTimesheetCode(timesheetCodeId);
		return (timesheetCodeExisting != null);
	}
	
	public TimesheetCode addTimesheetCode(TimesheetCode timesheetCode) {
		logger.info("addTimesheetCode({})", timesheetCode);
		timesheetCode.setDateCrt(new Date());
		 
	    return timesheetCodeDao.save(timesheetCode);
	}
	
	public void deleteTimesheetCode(String timesheetCodeId) {
		logger.info("deleteTimesheetCode({})", timesheetCodeId);
		timesheetCodeDao.deleteById(timesheetCodeId);
	}
	
	public TimesheetCode updateTimesheetCode(TimesheetCode timesheetCode) {
		logger.info("updateTimesheetCode({})", timesheetCode);
		timesheetCode.setDateMod(new Date());
		
		return timesheetCodeDao.save(timesheetCode);
	}
}
