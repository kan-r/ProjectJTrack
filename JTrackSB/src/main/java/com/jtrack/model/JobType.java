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
@Table(name="JOB_TYPE")
public class JobType {
	
	@Id 
    @Column(name="JOB_TYPE", unique=true, nullable=false)
	private String jobType;
	
	@Column(name="JOB_TYPE_DESC")
    private String jobTypeDesc;
	
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
    
    public JobType() {
    }

    public JobType(String jobType) {
        this.jobType = jobType;
    }
    
    public JobType(String jobType, String jobTypeDesc, Boolean active, Date dateCrt, String userCrt, Date dateMod, String userMod) {
       this.jobType = jobType;
       this.jobTypeDesc = jobTypeDesc;
       this.active = active;
       this.dateCrt = dateCrt;
       this.userCrt = userCrt;
       this.dateMod = dateMod;
       this.userMod = userMod;
    }
    
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobTypeDesc() {
		return jobTypeDesc;
	}
	public void setJobTypeDesc(String jobTypeDesc) {
		this.jobTypeDesc = jobTypeDesc;
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
		return "JobType [jobType=" + jobType + ", jobTypeDesc=" + jobTypeDesc + ", active=" + active + ", dateCrt="
				+ dateCrt + ", userCrt=" + userCrt + ", dateMod=" + dateMod + ", userMod=" + userMod + "]";
	}
}
