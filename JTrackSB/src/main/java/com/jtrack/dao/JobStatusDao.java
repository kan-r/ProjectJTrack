package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.JobStatus;

public interface JobStatusDao extends JpaRepository <JobStatus, String> {

}
