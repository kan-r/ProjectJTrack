package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jtrack.model.Timesheet;

public interface TimesheetDao extends JpaRepository <Timesheet, String>, JpaSpecificationExecutor<Timesheet> {

}
