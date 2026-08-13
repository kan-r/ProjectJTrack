package com.kan.jtrack.repository;

import com.kan.jtrack.entity.JobPriority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPriorityRepository extends JpaRepository<JobPriority, String> {
}
