/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.model;

import java.util.Date;

/**
 *
 * @author Kan
 */
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
    
}
