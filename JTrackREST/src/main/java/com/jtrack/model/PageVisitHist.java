package com.jtrack.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PAGE_VISIT_HIST")
public class PageVisitHist {
	
	@Id 
    @Column(name="HIST_ID", unique=true, nullable=false)
	private String histId;

	@Column(name="PAGE_ID")
    private String pageId;

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
	
	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
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
