package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.JobStage;;

public interface JobStageDao extends JpaRepository <JobStage, String> {

}
