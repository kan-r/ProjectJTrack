package com.kan.jtrack.repository;

import com.kan.jtrack.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTypeRepository extends JpaRepository<JobType, String> {
}
