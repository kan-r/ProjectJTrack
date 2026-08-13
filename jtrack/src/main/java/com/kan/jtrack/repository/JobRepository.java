package com.kan.jtrack.repository;

import com.kan.jtrack.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JobRepository extends JpaRepository<Job, Integer> {

   @Modifying
    @Transactional
    @Query("UPDATE Job j " +
            "SET j.estimatedHours = (select sum(sub.estimatedHours) from Job sub where sub.parentId = :parentId) " +
            "WHERE j.id = :parentId")
    void refreshParentJobEstimatedHours(@Param("parentId") Integer parentId);

    @Modifying
    @Transactional
    @Query("UPDATE Job j " +
            "SET j.actualHours = (select sum(sub.actualHours) from Job sub where sub.parentId = :parentId) " +
            "WHERE j.id = :parentId")
    void refreshParentJobActualHours(@Param("parentId") Integer parentId);

    @Modifying
    @Transactional
    @Query("UPDATE Job j " +
            "SET j.actualHours = (select sum(sub.workedHours) from Timesheet sub where sub.jobId = :jobId) " +
            "WHERE j.id = :jobId")
    void refreshJobActualHours(@Param("jobId") Integer jobId);
}
