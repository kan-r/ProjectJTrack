package com.jtrack.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.TimesheetDao;
import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.Timesheet;
import com.jtrack.model.TimesheetSO;

@Service
@Transactional
public class TimesheetService {
	
	Logger logger = LogManager.getLogger(TimesheetService.class);
	
	@Autowired
	private TimesheetDao timesheetDao;
	
	@Autowired
	private UserService userService;

	public List<Timesheet> getTimesheetList(){
		logger.info("getTimesheetList()");
		return timesheetDao.findAll(Sort.by("userId", "jobNo", "workedDate"));
	}
	
	public List<Timesheet> getTimesheetList(TimesheetSO timesheetSO){
		logger.info("getTimesheetList({})", timesheetSO);
		return getTimesheetList(
				timesheetSO.getUserId(), 
				timesheetSO.getWorkedDateFrom(), 
				timesheetSO.getWorkedDateTo()
				);
	}
	
	public List<Timesheet> getTimesheetList(String userId, Date workedDateFrom, Date workedDateTo){
		
		logger.info("getTimesheetList({}, {}, {})", userId, workedDateFrom, workedDateTo);
		
		return timesheetDao.findAll(new Specification<Timesheet>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Timesheet> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<>();
				if(userId != null && !userId.isEmpty()){
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("userId"), userId)));
				}
				if(workedDateFrom != null){
					predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("workedDate"), workedDateFrom)));
					
				}
				if(workedDateTo != null){
					predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("workedDate"), workedDateTo)));
				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, Sort.by("userId", "jobNo", "workedDate"));
	}
	
	public Timesheet getTimesheet(String timesheetId){
		logger.info("getTimesheet({})", timesheetId);
		Optional<Timesheet> timesheet = timesheetDao.findById(timesheetId);
		if(timesheet.isPresent()) {
			return timesheet.get();
		}
		
		return null;
	}
	
	public Timesheet getTimesheet(String userId, long jobNo, Date workedDate) {
		logger.info("getTimesheet({}, {}, {})", userId, jobNo, workedDate);
	
		Timesheet filterBy = new Timesheet();
		filterBy.setUserId(userId);
		filterBy.setJobNo(jobNo);
		filterBy.setWorkedDate(workedDate);
		
		Optional<Timesheet> timesheet = timesheetDao.findOne(Example.of(filterBy));
		if(timesheet.isPresent()) {
			return timesheet.get();
		}
		
		return null;
	}
	
	public boolean timesheetExists(String timesheetId) {
		Timesheet timesheetExisting = getTimesheet(timesheetId);
		return (timesheetExisting != null);
	}
	
	public boolean timesheetExists(String userId, long jobNo, Date workedDate) {
		Timesheet timesheetExisting = getTimesheet(userId, jobNo, workedDate);
		return (timesheetExisting != null);
	}
	
	public Timesheet addTimesheet(Timesheet timesheet) throws InvalidDataException {
		logger.info("addTimesheet({})", timesheet);
		
		List<String> fieldMissing = new ArrayList<>();
		
		if(timesheet.getUserId() == null || timesheet.getUserId().isEmpty()) {
			fieldMissing.add("User");
		}
		
		if(timesheet.getJobNo() == null) {
			fieldMissing.add("Job");
		}
		
		if(timesheet.getWorkedDate() == null) {
			fieldMissing.add("Workdate");
		}
		
		String error = "";
		int size = fieldMissing.size();
		for (int i = 0; i < size; i++) {
			
			if(!error.isEmpty()) {
				if(i == (size-1)) {
					error += " and ";
				}else {
					error += ", ";
				}
			}
			
			error += fieldMissing.get(i);
			
			if(i == (size-1)) {
				if(i == 0) {
					error += " is";
				}else {
					error += " are";
				}
				
				error += " required";
			}
		}
		
		if(!error.isEmpty()) {
			throw new InvalidDataException(error);
		}
		
		if(timesheetExists(timesheet.getUserId(), timesheet.getJobNo(), timesheet.getWorkedDate())) {
			throw new InvalidDataException("Timesheet already exists");
		}

		SimpleDateFormat dtFmt = new SimpleDateFormat("yyyyMMdd");
        String timesheetId = timesheet.getUserId() + "-" + timesheet.getJobNo() + "-" + dtFmt.format(timesheet.getWorkedDate());
        
        timesheet.setTimesheetId(timesheetId);
		timesheet.setDateCrt(new Date());
		timesheet.setUserCrt(userService.getCurrentUserId());
		
		Timesheet t = timesheetDao.save(timesheet);
		refreshJob(timesheet.getJobNo());
		return t;
	}
	
	public void deleteTimesheet(String timesheetId) {
		logger.info("deleteTimesheet({})", timesheetId);
		
		long jobNo = getTimesheet(timesheetId).getJobNo();
		
		timesheetDao.deleteById(timesheetId);
		refreshJob(jobNo);
	}
	
	public Timesheet updateTimesheet(Timesheet timesheet) {
		logger.info("updateTimesheet({})", timesheet);
		
		Timesheet timesheeteOld = getTimesheet(timesheet.getTimesheetId());
		
		timesheet.setDateCrt(timesheeteOld.getDateCrt());
		timesheet.setUserCrt(timesheeteOld.getUserCrt());
		
		timesheet.setDateMod(new Date());
		timesheet.setUserMod(userService.getCurrentUserId());
		
		Timesheet t = timesheetDao.save(timesheet);
		refreshJob(timesheet.getJobNo());
		return t;
	}
	
	private void refreshJob(long jobNo) {
		try {
			timesheetDao.refreshCompletedHrsInJob(jobNo);
			timesheetDao.refreshCompletedHrsInParentJob(jobNo);
			timesheetDao.refreshStatusInJob(jobNo);
			timesheetDao.refreshStatusInParentJob(jobNo);
		}catch(Exception e) {
			
		}
	}

}
