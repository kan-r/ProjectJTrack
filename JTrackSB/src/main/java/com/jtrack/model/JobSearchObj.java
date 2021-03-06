package com.jtrack.model;

public class JobSearchObj {
	
	private String jobName = "";
    private String jobType = "";
    private String jobStatus = "";
    private String assignedTo = "";
    private boolean includeChildJobs = false;
    
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public boolean isIncludeChildJobs() {
		return includeChildJobs;
	}
	public void setIncludeChildJobs(boolean includeChildJobs) {
		this.includeChildJobs = includeChildJobs;
	}
	@Override
	public String toString() {
		return "JobSearchObj [jobName=" + jobName + ", jobType=" + jobType + ", jobStatus=" + jobStatus
				+ ", assignedTo=" + assignedTo + ", includeChildJobs=" + includeChildJobs + "]";
	}
}
