<%-- 
    Document   : jobPriority
    Created on : 15/06/2013, 8:17:53 AM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Job Priority</title>
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
                <li class="first-non-current">
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
                <li class="current">
                    <a class="tab_link" href="jobType"><span></span>Admin</a>
                </li>
                <li class="last"><span></span></li>
            </ul>
        </div>

        <div class="sHorizontalTabs">
            <div class="sHorizontalTabsInner">
                <ul>
                    <li>
                        <a href="<c:url value="jobType" />"><span>Job Type</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobStatus" />"><span>Job Status</span></a>
                    </li>
                    <li class="active">
                        <a href="<c:url value="jobPriority" />"><span>Job Priority</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobResolution" />"><span>Job Resolution</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobStage" />"><span>Job Stage</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="timesheetCode" />"><span>Timesheet Code</span></a>
                    </li>
                    <li>
                    	<a href="<c:url value="users" />"><span>Users</span></a>
                    </li>
                </ul>
            </div>
        </div>
        
        <c:if test="${not empty error}">
	        <div class="container">
				<div class="alert alert-danger">${error}</div>
	        </div>
        </c:if>
        
        <div class="button-region">
            <a href="<c:url value="jobPriorityCreate" />" class="button">Create</a>
        </div>
        
        <div class="report-region">
            <table cellpadding="0" border="0" cellspacing="0" summary="" class="report-standard">
                <tr >
                    <th></th>
                    <th></th>
                    <th>Job Priority</th>
                    <th>Job Priority Description</th>
                    <th>Active</th>
                    <th>Date Created</th>
                    <th>User Created</th>
                    <th>Date Modified</th>
                    <th>User Modified</th>
                </tr>
                <c:forEach items="${jobPriorityList}" var="jobPriority">
                    <tr>
                        <td><a href="<c:url value="jobPriorityEdit?id=${jobPriority.jobPriority}" />" >Edit</a></td>
                        <td><a href="<c:url value="jobPriorityDelete?id=${jobPriority.jobPriority}" />" >Delete</a></td>
                        <td><c:out value="${jobPriority.jobPriority}"/></td>
                        <td><c:out value="${jobPriority.jobPriorityDesc}"/></td>
                        <c:if test="${jobPriority.active}">
                        	<td align="center"><input type="checkbox" checked disabled /></td>
                        </c:if>
                        <c:if test="${!jobPriority.active}">
                        	<td align="center"><input type="checkbox" disabled /></td>
                        </c:if>
                        <td><fmt:formatDate type="both" value="${jobPriority.dateCrt}"/></td>
                        <td><c:out value="${jobPriority.userCrtObj.firstName} ${jobPriority.userCrtObj.lastName}"/></td>
                        <td><fmt:formatDate type="both" value="${jobPriority.dateMod}"/></td>
                        <td><c:out value="${jobPriority.userModObj.firstName} ${jobPriority.userModObj.lastName}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
