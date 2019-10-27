<%-- 
    Document   : jobResolution
    Created on : 04/07/2013, 8:34:55 PM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Job Resolution</title>
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
                <li class="first-non-current">
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
                <li class="current">
                    <a class="tab_link" href="jobType.htm"><span></span>Admin</a>
                </li>
                <li class="last"><span></span></li>
            </ul>
        </div>

        <div class="sHorizontalTabs">
            <div class="sHorizontalTabsInner">
                <ul>
                    <li>
                        <a href="<c:url value="jobType.htm" />"><span>Job Type</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobStatus.htm" />"><span>Job Status</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobPriority.htm" />"><span>Job Priority</span></a>
                    </li>
                    <li class="active">
                        <a href="<c:url value="jobResolution.htm" />"><span>Job Resolution</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobStage.htm" />"><span>Job Stage</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="timesheetCode.htm" />"><span>Timesheet Code</span></a>
                    </li>
                    <li>
	                	<c:if test="${currentUser.isAdmin}">
	                    	<a href="<c:url value="users.htm" />"><span>Users</span></a>
	                    </c:if>
	                    <c:if test="${!currentUser.isAdmin}">
	                    	<a><span>Users - No access</span></a>
	                    </c:if>
	                </li>
                </ul>
            </div>
        </div>
        
        <div class="button-region">
            <a href="<c:url value="jobResolutionCreate.htm" />" class="button">Create</a>
        </div>
        
        <div class="report-region">
            <table cellpadding="0" border="0" cellspacing="0" summary="" class="report-standard">
                <tr >
                    <th></th>
                    <th></th>
                    <th>Job Resolution</th>
                    <th>Job Resolution Description</th>
                    <th>Active</th>
                    <th>Date Created</th>
                    <th>User Created</th>
                    <th>Date Modified</th>
                    <th>User Modified</th>
                </tr>
                <c:forEach items="${jobResolutionList}" var="jobResolution">
                    <tr>
                        <td><a href="<c:url value="jobResolutionEdit.htm?id=${jobResolution.jobResolution}" />" >Edit</a></td>
                        <td><a href="<c:url value="jobResolutionDelete.htm?id=${jobResolution.jobResolution}" />" >Delete</a></td>
                        <td><c:out value="${jobResolution.jobResolution}"/></td>
                        <td><c:out value="${jobResolution.jobResolutionDesc}"/></td>
                        <td><c:out value="${jobResolution.active}"/></td>
                        <td><fmt:formatDate type="both" value="${jobResolution.dateCrt}"/></td>
                        <td><c:out value="${jobResolution.userCrtObj.firstName} ${jobResolution.userCrtObj.lastName}"/></td>
                        <td><fmt:formatDate type="both" value="${jobResolution.dateMod}"/></td>
                        <td><c:out value="${jobResolution.userModObj.firstName} ${jobResolution.userModObj.lastName}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>