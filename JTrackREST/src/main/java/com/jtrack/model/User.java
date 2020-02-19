package com.jtrack.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="USERS")
public class User {

	 @Id 
	 @Column(name="USER_ID", unique=true, nullable=false)
	 private String userId;
	 
	 @Column(name="FIRST_NAME")
     private String firstName;
	 
	 @Column(name="LAST_NAME")
     private String lastName;
	 
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
	 
	 @Column(name="PWORD")
     private String pword;
	 
	 @OneToOne(fetch=FetchType.EAGER, optional=true)
	 @JoinColumn(name = "USER_CRT", insertable = false, updatable = false)
	 private UserName userCrtObj;
		
	 @OneToOne(fetch=FetchType.EAGER, optional=true)
	 @JoinColumn(name = "USER_MOD", insertable = false, updatable = false)
	 private UserName userModObj;
	
	 @Transient
	 private boolean isAdmin;
	 
	public User() {
	}

	public User(String userId, String firstName, String lastName, Boolean active, Date dateCrt, String userCrt, Date dateMod, String userMod) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.dateCrt = dateCrt;
		this.userCrt = userCrt;
		this.dateMod = dateMod;
		this.userMod = userMod;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	

	public UserName getUserCrtObj() {
		return userCrtObj;
	}

	public void setUserCrtObj(UserName userCrtObj) {
		this.userCrtObj = userCrtObj;
	}

	public UserName getUserModObj() {
		return userModObj;
	}

	public void setUserModObj(UserName userModObj) {
		this.userModObj = userModObj;
	}
	
	public boolean getIsAdmin() {
		return this.userId.equalsIgnoreCase("ADMIN");
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPword() {
		return pword;
	}

	public void setPword(String pword) {
		this.pword = pword;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", active=" + active
				+ ", dateCrt=" + dateCrt + ", userCrt=" + userCrt + ", dateMod=" + dateMod + ", userMod=" + userMod
				+ ", pword=" + pword + "]";
	}
}
