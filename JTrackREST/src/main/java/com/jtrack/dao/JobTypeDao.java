package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.JobType;;

public interface JobTypeDao extends JpaRepository <JobType, String> {

}
