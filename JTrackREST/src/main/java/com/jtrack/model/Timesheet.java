package com.jtrack.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="TIMESHEET", uniqueConstraints = @UniqueConstraint(columnNames={"USER_ID", "JOB_NO", "WORKED_DATE"}))
public class Timesheet {
	
	@Id 
    @Column(name="TIMESHEET_ID", unique=true, nullable=false)
	private String timesheetId;
	
	@Column(name="USER_ID")
    private String userId;
	
	@Column(name="JOB_NO")
    private Long jobNo;
	
	@Temporal(TemporalType.DATE)
    @Column(name="WORKED_DATE", length=7)
    private Date workedDate;
	
	@Column(name="WORKED_HRS")
    private double workedHrs;
	
	@Column(name="TIMESHEET_CODE")
    private String timesheetCode;
	
	@Column(name="ACTIVE", precision=1, scale=0)
    private Boolean active;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_CRT")
    private Date dateCrt;
	
	@Column(name="USER_CRT")
    private String userCrt;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_MOD")
    private Date dateMod;
	
	@Column(name="USER_MOD")
    private String userMod;
    
	@Transient
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "JOB_NO", referencedColumnName = "JOB_NO")
    private Job jobObj;
	
	@Transient
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User userObj;
	
	@Transient
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "USER_CRT", referencedColumnName = "USER_ID")
    private User userCrtObj;
	
	@Transient
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "USER_MOD", referencedColumnName = "USER_ID")
    private User userModObj;

	public String getTimesheetId() {
		return timesheetId;
	}

	public void setTimesheetId(String timesheetId) {
		this.timesheetId = timesheetId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getJobNo() {
		return jobNo;
	}

	public void setJobNo(Long jobNo) {
		this.jobNo = jobNo;
	}

	public Date getWorkedDate() {
		return workedDate;
	}

	public void setWorkedDate(Date workedDate) {
		this.workedDate = workedDate;
	}

	public double getWorkedHrs() {
		return workedHrs;
	}

	public void setWorkedHrs(double workedHrs) {
		this.workedHrs = workedHrs;
	}

	public String getTimesheetCode() {
		return timesheetCode;
	}

	public void setTimesheetCode(String timesheetCode) {
		this.timesheetCode = timesheetCode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getDateCrt() {
		return dateCrt;
	}

	public void setDateCrt(Date dateCrt) {
		this.dateCrt = dateCrt;
	}

	public String getUserCrt() {
		return userCrt;
	}

	public void setUserCrt(String userCrt) {
		this.userCrt = userCrt;
	}

	public Date getDateMod() {
		return dateMod;
	}

	public void setDateMod(Date dateMod) {
		this.dateMod = dateMod;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

	public Job getJobObj() {
		return jobObj;
	}

	public void setJobObj(Job jobObj) {
		this.jobObj = jobObj;
	}

	public User getUserObj() {
		return userObj;
	}

	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}

	public User getUserCrtObj() {
		return userCrtObj;
	}

	public void setUserCrtObj(User userCrtObj) {
		this.userCrtObj = userCrtObj;
	}

	public User getUserModObj() {
		return userModObj;
	}

	public void setUserModObj(User userModObj) {
		this.userModObj = userModObj;
	}

	@Override
	public String toString() {
		return "Timesheet [timesheetId=" + timesheetId + ", userId=" + userId + ", jobNo=" + jobNo + ", workedDate="
				+ workedDate + ", workedHrs=" + workedHrs + ", timesheetCode=" + timesheetCode + ", active=" + active
				+ ", dateCrt=" + dateCrt + ", userCrt=" + userCrt + ", dateMod=" + dateMod + ", userMod=" + userMod
				+ "]";
	}
}
