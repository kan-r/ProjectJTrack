<%-- 
    Document   : timesheetCode
    Created on : 04/07/2013, 9:59:31 PM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Timesheet Code</title>
    </head>
    <body>
        <div class="header-region">
            <div class="navbar">
                <div class="app-title">JTrack</div>
                <div class="app-user">Welcome: ${currentUser.firstName}</div>
                <div class="navbar-entry"><a href="logout">Logout</a></div>
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
                    <li>
                        <a href="<c:url value="jobPriority" />"><span>Job Priority</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobResolution" />"><span>Job Resolution</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobStage" />"><span>Job Stage</span></a>
                    </li>
                    <li class="active">
                        <a href="<c:url value="timesheetCode" />"><span>Timesheet Code</span></a>
                    </li>
                    <li>
	                	<c:if test="${currentUser.isAdmin}">
	                    	<a href="<c:url value="users" />"><span>Users</span></a>
	                    </c:if>
	                    <c:if test="${!currentUser.isAdmin}">
	                    	<a><span>Users - No access</span></a>
	                    </c:if>
	                </li>
                </ul>
            </div>
        </div>
        
        <div class="button-region">
            <a href="<c:url value="timesheetCodeCreate" />" class="button">Create</a>
        </div>
        
        <div class="report-region">
            <table cellpadding="0" border="0" cellspacing="0" summary="" class="report-standard">
                <tr >
                    <th></th>
                    <th></th>
                    <th>Timesheet Code</th>
                    <th>Timesheet Code Description</th>
                    <th>Active</th>
                    <th>Date Created</th>
                    <th>User Created</th>
                    <th>Date Modified</th>
                    <th>User Modified</th>
                </tr>
                <c:forEach items="${timesheetCodeList}" var="timesheetCode">
                    <tr>
                        <td><a href="<c:url value="timesheetCodeEdit?id=${timesheetCode.timesheetCode}" />" >Edit</a></td>
                        <td><a href="<c:url value="timesheetCodeDelete?id=${timesheetCode.timesheetCode}" />" >Delete</a></td>
                        <td><c:out value="${timesheetCode.timesheetCode}"/></td>
                        <td><c:out value="${timesheetCode.timesheetCodeDesc}"/></td>
                        <td><c:out value="${timesheetCode.active}"/></td>
                        <td><fmt:formatDate type="both" value="${timesheetCode.dateCrt}"/></td>
                        <td><c:out value="${timesheetCode.userCrtObj.firstName} ${timesheetCode.userCrtObj.lastName}"/></td>
                        <td><fmt:formatDate type="both" value="${timesheetCode.dateMod}"/></td>
                        <td><c:out value="${timesheetCode.userModObj.firstName} ${timesheetCode.userModObj.lastName}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>