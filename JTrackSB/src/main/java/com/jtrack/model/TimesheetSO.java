package com.jtrack.model;

import java.util.Date;

public class TimesheetSO {

	private String userId;
    private Date workedDateFrom;
    private Date workedDateTo;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getWorkedDateFrom() {
		return workedDateFrom;
	}
	public void setWorkedDateFrom(Date workedDateFrom) {
		this.workedDateFrom = workedDateFrom;
	}
	public Date getWorkedDateTo() {
		return workedDateTo;
	}
	public void setWorkedDateTo(Date workedDateTo) {
		this.workedDateTo = workedDateTo;
	}
	@Override
	public String toString() {
		return "TimesheetSearchObj [userId=" + userId + ", workedDateFrom=" + workedDateFrom + ", workedDateTo="
				+ workedDateTo + "]";
	}
}
