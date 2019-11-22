package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.JobPriority;;

public interface JobPriorityDao extends JpaRepository <JobPriority, String> {

}
