package com.jtrack.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jtrack.model.Job;

public interface JobDao extends JpaRepository <Job, String>, JpaSpecificationExecutor<Job> {
	
	public Job findByJobNo(long jobNo);
	public List<Job> findByJobType(String jobType, Sort sort);
	public List<Job> findByParentJobNo(Long parentJobNo, Sort sort);
	public Job deleteByJobNo(long jobNo);

}
