package com.jtrack.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name="JOBS")
public class Job {
	
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "JOB_NO_SEQ", allocationSize = 1)
    @Column(name="JOB_NO", unique=true, nullable=false)
	private long jobNo;
	
	@Column(name="JOB_NAME")
    private String jobName;
	
	@Column(name="JOB_DESC")
    private String jobDesc;
	
	@Column(name="JOB_TYPE")
    private String jobType;
    
    @Column(name="JOB_PRIORITY")
    private String jobPriority;
    
    @Column(name="JOB_STATUS")
    private String jobStatus;
    
    @Column(name="JOB_RESOLUTION")
    private String jobResolution;
    
    @Column(name="JOB_STAGE")
    private String jobStage;
    
    @Column(name="JOB_ORDER")
    private Integer jobOrder;
    
    @Column(name="ASSIGNED_TO")
    private String assignedTo;
    
    @Column(name="TIMESHEET_CODE")
    private String timesheetCode;
    
    @Column(name="ESTIMATED_HRS")
    private Double estimatedHrs;
    
    @Column(name="COMPLETED_HRS")
    private Double completedHrs;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ESTIMATED_START_DATE")
    private Date estimatedStartDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACTUAL_START_DATE")
    private Date actualStartDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ESTIMATED_END_DATE")
    private Date estimatedEndDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACTUAL_END_DATE")
    private Date actualEndDate;
    
    @Column(name="PARENT_JOB_NO")
    private Long parentJobNo;
    
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
    
    @Column(name="JOB_REF")
    private String jobRef;
    
    @Transient
    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "PARENT_JOB_NO", referencedColumnName = "JOB_NO")
    private Job parentJobObj;
    
    @Transient
    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "ASSIGNED_TO", referencedColumnName = "USER_ID")
    private User assignedToObj;
    
    @Transient
    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "USER_CRT", referencedColumnName = "USER_ID")
    private User userCrtObj;
    
    @Transient
    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "USER_MOD", referencedColumnName = "USER_ID")
    private User userModObj;
    
	public Long getJobNo() {
		return jobNo;
	}
	public void setJobNo(Long jobNo) {
		this.jobNo = jobNo;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobPriority() {
		return jobPriority;
	}
	public void setJobPriority(String jobPriority) {
		this.jobPriority = jobPriority;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getJobResolution() {
		return jobResolution;
	}
	public void setJobResolution(String jobResolution) {
		this.jobResolution = jobResolution;
	}
	public String getJobStage() {
		return jobStage;
	}
	public void setJobStage(String jobStage) {
		this.jobStage = jobStage;
	}
	public Integer getJobOrder() {
		return jobOrder;
	}
	public void setJobOrder(Integer jobOrder) {
		this.jobOrder = jobOrder;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getTimesheetCode() {
		return timesheetCode;
	}
	public void setTimesheetCode(String timesheetCode) {
		this.timesheetCode = timesheetCode;
	}
	public Double getEstimatedHrs() {
		return estimatedHrs;
	}
	public void setEstimatedHrs(Double estimatedHrs) {
		this.estimatedHrs = estimatedHrs;
	}
	public Double getCompletedHrs() {
		return completedHrs;
	}
	public void setCompletedHrs(Double completedHrs) {
		this.completedHrs = completedHrs;
	}
	public Date getEstimatedStartDate() {
		return estimatedStartDate;
	}
	public void setEstimatedStartDate(Date estimatedStartDate) {
		this.estimatedStartDate = estimatedStartDate;
	}
	public Date getActualStartDate() {
		return actualStartDate;
	}
	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	public Date getEstimatedEndDate() {
		return estimatedEndDate;
	}
	public void setEstimatedEndDate(Date estimatedEndDate) {
		this.estimatedEndDate = estimatedEndDate;
	}
	public Date getActualEndDate() {
		return actualEndDate;
	}
	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	public Long getParentJobNo() {
		return parentJobNo;
	}
	public void setParentJobNo(Long parentJobNo) {
		this.parentJobNo = parentJobNo;
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
	public String getJobRef() {
		return jobRef;
	}
	public void setJobRef(String jobRef) {
		this.jobRef = jobRef;
	}
	public Job getParentJobObj() {
		return parentJobObj;
	}
	public void setParentJobObj(Job parentJobObj) {
		this.parentJobObj = parentJobObj;
	}
	public User getAssignedToObj() {
		return assignedToObj;
	}
	public void setAssignedToObj(User assignedToObj) {
		this.assignedToObj = assignedToObj;
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
		return "Job [jobNo=" + jobNo + ", jobName=" + jobName + ", jobDesc=" + jobDesc + ", jobType=" + jobType
				+ ", jobPriority=" + jobPriority + ", jobStatus=" + jobStatus + ", jobResolution=" + jobResolution
				+ ", jobStage=" + jobStage + ", jobOrder=" + jobOrder + ", assignedTo=" + assignedTo
				+ ", timesheetCode=" + timesheetCode + ", estimatedHrs=" + estimatedHrs + ", completedHrs="
				+ completedHrs + ", estimatedStartDate=" + estimatedStartDate + ", actualStartDate=" + actualStartDate
				+ ", estimatedEndDate=" + estimatedEndDate + ", actualEndDate=" + actualEndDate + ", parentJobNo="
				+ parentJobNo + ", active=" + active + ", dateCrt=" + dateCrt + ", userCrt=" + userCrt + ", dateMod="
				+ dateMod + ", userMod=" + userMod + ", jobRef=" + jobRef + "]";
	}
}
