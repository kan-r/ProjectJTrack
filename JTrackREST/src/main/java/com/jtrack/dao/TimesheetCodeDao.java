package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.TimesheetCode;;

public interface TimesheetCodeDao extends JpaRepository <TimesheetCode, String> {

}
