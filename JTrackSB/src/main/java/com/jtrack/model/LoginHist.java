package com.jtrack.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="LOGIN_HIST")
public class LoginHist {
	
	@Id 
    @Column(name="HIST_ID", unique=true, nullable=false)
	private String histId;

	@Column(name="USER_ID")
    private String userId;

	@Column(name="IP_ADDR")
    private String ipAddr;
	 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CRT")
	private Date dateCrt;

	public String getHistId() {
		return histId;
	}

	public void setHistId(String histId) {
		this.histId = histId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Date getDateCrt() {
		return dateCrt;
	}

	public void setDateCrt(Date dateCrt) {
		this.dateCrt = dateCrt;
	}
	 
	 
}
