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

@Entity
@Table(name="JOB_RESOLUTION")
public class JobResolution {

	 @Id 
	 @Column(name="JOB_RESOLUTION", unique=true, nullable=false)
	 private String jobResolution;
	 
	 @Column(name="JOB_RESOLUTION_DESC")
     private String jobResolutionDesc;
	 
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
	 @JoinColumn(name = "USER_CRT", referencedColumnName = "USER_ID")
     private User userCrtObj;
	 
	 @Transient
	 @ManyToOne(fetch=FetchType.EAGER, optional=true)
	 @JoinColumn(name = "USER_MOD", referencedColumnName = "USER_ID")
     private User userModObj;
     
	public String getJobResolution() {
		return jobResolution;
	}
	public void setJobResolution(String jobResolution) {
		this.jobResolution = jobResolution;
	}
	public String getJobResolutionDesc() {
		return jobResolutionDesc;
	}
	public void setJobResolutionDesc(String jobResolutionDesc) {
		this.jobResolutionDesc = jobResolutionDesc;
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
		return "JobResolution [jobResolution=" + jobResolution + ", jobResolutionDesc=" + jobResolutionDesc
				+ ", active=" + active + ", userCrt=" + userCrt + ", userMod=" + userMod + "]";
	}
}
