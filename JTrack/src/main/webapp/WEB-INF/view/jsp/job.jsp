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
                <div class="navbar-entry"><a href="logout.htm">Logout</a></div>
            </div>
        </div>

        <div class="main-tabs-region">
            <ul id="tabs">
                <li class="first-current">
                    <a class="tab_link" href="job.htm"><span></span>Jobs</a>
                </li>
                <li class="non-current">
                    <a class="tab_link" href="timesheet.htm"><span></span>Timesheet</a>
                </li>
                <!--  
                <li class="non-current">
                    <a class="tab_link" href="weeklyReport.htm"><span></span>Reports</a>
                </li>
                -->
                <li class="non-current">
                    <a class="tab_link" href="jobType.htm"><span></span>Admin</a>
                </li>
                <li class="last"><span></span></li>
            </ul>
        </div>
        
        <form:form method="POST" action="job.htm">
                <table cellpadding="0" border="0" cellspacing="0" summary="" class="report-standard">
                    <tr>
                        <td>
                            <table border="0" summary="">
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Name</label>
                                    </td>
                                    <td  colspan="3" rowspan="1" align="left" valign="middle">
                                        <form:input path="jobName" size="52" />
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
                                    <td>
                                        <input type="submit" value="Go" class="button" />
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
                                    <td nowrap="nowrap" align="right">
                                        <label>Include Child Jobs</label>
                                    </td>
                                    <td>
                                        <form:checkbox path="includeChildJobs" />
                                    </td>
                                </tr>
                            </table> 
                        </td>
                    </tr>
                </table>
        </form:form> 
        
        <div class="button-region">
            <a href="<c:url value="jobCreate.htm" />" class="button">Create</a>
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
                        <td><a href="<c:url value="jobEdit.htm?id=${job.jobNo}" />" >Edit</a></td>
                        <td><a href="<c:url value="jobDelete.htm?id=${job.jobNo}" />" >Delete</a></td>
                        <td><a href="<c:url value="timesheetForJob.htm?id=${job.jobNo}" />" >${job.jobNo}</a></td>
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
                        <td><c:out value="${job.active}"/></td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${pageNumber gt 1}">
                <a href="jobPage.htm?pageNumber=${pageNumber - 1}">Previous</a>
            </c:if>
            <c:if test="${pageNumber lt numberOfPages}">
                <a href="jobPage.htm?pageNumber=${pageNumber + 1}">Next</a>
            </c:if>
        </div>
    </body>
</html>