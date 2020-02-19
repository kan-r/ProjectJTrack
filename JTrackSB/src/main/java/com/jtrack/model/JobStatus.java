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

@Entity
@Table(name="JOB_STATUS")
public class JobStatus {

	@Id 
    @Column(name="JOB_STATUS", unique=true, nullable=false)
	private String jobStatus;
	
	@Column(name="JOB_STATUS_DESC")
    private String jobStatusDesc;
	
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
    
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "USER_CRT", insertable = false, updatable = false)
    private User userCrtObj;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "USER_MOD", insertable = false, updatable = false)
    private User userModObj;
    
    public JobStatus() {
    }
	
    public JobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
    
    public JobStatus(String jobStatus, String jobStatusDesc, Boolean active, Date dateCrt, String userCrt, Date dateMod, String userMod) {
       this.jobStatus = jobStatus;
       this.jobStatusDesc = jobStatusDesc;
       this.active = active;
       this.dateCrt = dateCrt;
       this.userCrt = userCrt;
       this.dateMod = dateMod;
       this.userMod = userMod;
    }
    
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getJobStatusDesc() {
		return jobStatusDesc;
	}
	public void setJobStatusDesc(String jobStatusDesc) {
		this.jobStatusDesc = jobStatusDesc;
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
		return "JobStatus [jobStatus=" + jobStatus + ", jobStatusDesc=" + jobStatusDesc + ", active=" + active
				+ ", dateCrt=" + dateCrt + ", userCrt=" + userCrt + ", dateMod=" + dateMod + ", userMod=" + userMod
				+ "]";
	}
}
