package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.JobResolution;;

public interface JobResolutionDao extends JpaRepository <JobResolution, String> {

}
