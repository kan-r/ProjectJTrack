<%-- 
    Document   : job
    Created on : 06/07/2013, 10:02:39 PM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobs</title>
    </head>
    <body>
        <div class="header-region">
            <div class="navbar">
                <div class="app-title">JTrack</div>
                <div class="app-user">Welcome: ${currentUser.firstName}</div>
                <div class="navbar-entry"><a href="<c:url value="/login?logout=true" />" >Logout</a></div>
            </div>
        </div>

        <div class="main-tabs-region">
            <ul id="tabs">
                <li class="first-current">
                    <a class="tab_link" href="job"><span></span>Jobs</a>
                </li>
                <li class="non-current">
                    <a class="tab_link" href="timesheet"><span></span>Timesheet</a>
                </li>
                <!--  
                <li class="non-current">
                    <a class="tab_link" href="weeklyReport"><span></span>Reports</a>
                </li>
                -->
                <li class="non-current">
                    <a class="tab_link" href="jobType"><span></span>Admin</a>
                </li>
                <li class="last"><span></span></li>
            </ul>
        </div>
        
        <c:if test="${not empty error}">
	        <div class="container">
				<div class="alert alert-danger">${error}</div>
	        </div>
        </c:if>
        
        <div class="form-region">
	        <form:form method="POST" action="job">
	                <table cellpadding="0" border="1" cellspacing="0" summary="" class="form-standard">
	                    <tr>
	                        <td>
	                            <table border="0" summary="">
	                                <tr>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Job Name</label>
	                                    </td>
	                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
	                                        <form:input path="jobName" />
	                                    </td>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Job Type</label>
	                                    </td>
	                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
	                                        <form:select path="jobType" id="jobType">
	                                            <form:option value=""></form:option>
	                                            <c:forEach items="${jobTypeList}" var="jobType">
	                                                <form:option value="${jobType.jobType}">${jobType.jobType}</form:option>
	                                            </c:forEach>
	                                        </form:select>
	                                    </td>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Include Child Jobs</label>
	                                    </td>
	                                    <td>
	                                        <form:checkbox path="includeChildJobs" />
	                                    </td>
			                            <td nowrap="nowrap" align="right">
			                                <label>==></label>
			                            </td>
	                                </tr>
	                                <tr>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Job Status</label>
	                                    </td>
	                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
	                                        <form:select path="jobStatus" id="jobStatus">
	                                            <form:option value=""></form:option>
	                                            <c:forEach items="${jobStatusList}" var="jobStatus">
	                                                <form:option value="${jobStatus.jobStatus}">${jobStatus.jobStatus}</form:option>
	                                            </c:forEach>
	                                        </form:select>
	                                    </td>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Assigned To</label>
	                                    </td>
	                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
	                                        <form:select path="assignedTo" id="assignedTo">
	                                            <form:option value=""></form:option>
	                                            <c:forEach items="${assignedToList}" var="assignedTo">
	                                                <form:option value="${assignedTo.userId}">${assignedTo.firstName} ${assignedTo.lastName}</form:option>
	                                            </c:forEach>
	                                        </form:select>
	                                    </td>
	                                    <td>
	                                        <input type="submit" value="Go" class="button" />
	                                    </td>
	                                </tr>
	                            </table> 
	                        </td>
	                        <td>
	                            <table border="0" summary="">
	                                <tr>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Job Name</label>
	                                    </td>
	                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
	                                        <form:input path="jobNameChild" />
	                                    </td>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Job Type</label>
	                                    </td>
	                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
	                                        <form:select path="jobTypeChild" id="jobTypeChild">
	                                            <form:option value=""></form:option>
	                                            <c:forEach items="${jobTypeList}" var="jobType">
	                                                <form:option value="${jobType.jobType}">${jobType.jobType}</form:option>
	                                            </c:forEach>
	                                        </form:select>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Job Status</label>
	                                    </td>
	                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
	                                        <form:select path="jobStatusChild" id="jobStatusChild">
	                                            <form:option value=""></form:option>
	                                            <c:forEach items="${jobStatusList}" var="jobStatus">
	                                                <form:option value="${jobStatus.jobStatus}">${jobStatus.jobStatus}</form:option>
	                                            </c:forEach>
	                                        </form:select>
	                                    </td>
	                                    <td nowrap="nowrap" align="right">
	                                        <label>Assigned To</label>
	                                    </td>
	                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
	                                        <form:select path="assignedToChild" id="assignedToChild">
	                                            <form:option value=""></form:option>
	                                            <c:forEach items="${assignedToList}" var="assignedTo">
	                                                <form:option value="${assignedTo.userId}">${assignedTo.firstName} ${assignedTo.lastName}</form:option>
	                                            </c:forEach>
	                                        </form:select>
	                                    </td>
	                                </tr>
	                            </table> 
	                        </td>
	                    </tr>
	                </table>
	        </form:form> 
        </div>
        
        <div class="button-region">
            <a href="<c:url value="jobCreate" />" class="button">Create</a>
        </div>
        
        <div class="report-region">
            <table cellpadding="0" border="0" cellspacing="0" summary="" class="report-standard">
                <tr >
                    <th></th>
                    <th></th>
                    <th>Job No</th>
                    <th>Parent Job Name</th>
                    <th>Job Name</th>
                    <th>Job Type</th>
                    <th>Job Stage</th>
                    <th>Job Priority</th>
                    <th>Job Status</th>
                    <th>Job Resolution</th>
                    <th>Job Order</th>
                    <th>Assigned To</th>
                    <th>Timesheet Code</th>
                    <th>Estimated Start Date</th>
                    <th>Actual Start Date</th>
                    <th>Estimated End Date</th>
                    <th>Actual End Date</th>
                    <th>Estimated Hrs</th>
                    <th>Completed Hrs</th>
                    <th>Active</th>
                </tr>
                <c:forEach items="${jobList}" var="job">
                    <tr>
                        <td><a href="<c:url value="jobEdit?id=${job.jobNo}" />" >Edit</a></td>
                        <td><a href="<c:url value="jobDelete?id=${job.jobNo}" />" >Delete</a></td>
                        <td><a href="<c:url value="timesheetForJob?id=${job.jobNo}" />" >${job.jobNo}</a></td>
                        <td><c:out value="${job.parentJobObj.jobName}"/></td>
                        <td><c:out value="${job.jobName}"/></td>
                        <td><c:out value="${job.jobType}"/></td>
                        <td><c:out value="${job.jobStage}"/></td>
                        <td><c:out value="${job.jobPriority}"/></td>
                        <td><c:out value="${job.jobStatus}"/></td>
                        <td><c:out value="${job.jobResolution}"/></td>
                        <td><c:out value="${job.jobOrder}"/></td>
                        <td><c:out value="${job.assignedToObj.firstName} ${job.assignedToObj.lastName}"/></td>
                        <td><c:out value="${job.timesheetCode}"/></td>
                        <td><fmt:formatDate type="date" value="${job.estimatedStartDate}"/></td>
                        <td><fmt:formatDate type="date" value="${job.actualStartDate}"/></td>
                        <td><fmt:formatDate type="date" value="${job.estimatedEndDate}"/></td>
                        <td><fmt:formatDate type="date" value="${job.actualEndDate}"/></td>
                        <td><c:out value="${job.estimatedHrs}"/></td>
                        <td><c:out value="${job.completedHrs}"/></td>
                        <c:if test="${job.active}">
                        	<td align="center"><input type="checkbox" checked disabled /></td>
                        </c:if>
                        <c:if test="${!job.active}">
                        	<td align="center"><input type="checkbox" disabled /></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <!--  
            <c:if test="${pageNumber gt 1}">
                <a href="jobPage?pageNumber=${pageNumber - 1}">Previous</a>
            </c:if>
            <c:if test="${pageNumber lt numberOfPages}">
                <a href="jobPage?pageNumber=${pageNumber + 1}">Next</a>
            </c:if>
            -->
        </div>
    </body>
</html>