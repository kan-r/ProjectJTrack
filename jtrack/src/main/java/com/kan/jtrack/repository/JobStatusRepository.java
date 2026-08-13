package com.kan.jtrack.repository;

import com.kan.jtrack.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobStatusRepository extends JpaRepository<JobStatus, String> {
}
