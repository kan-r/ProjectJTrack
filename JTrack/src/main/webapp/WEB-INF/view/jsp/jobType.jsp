<%-- 
    Document   : jobType
    Created on : 02/07/2013, 8:56:47 PM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Job Type</title>
    </head>
    <body>
        <div class="header-region">
            <div class="navbar">
                <div class="app-title">JTrack</div>
                <div class="app-user">Welcome: ${currentUser.firstName}</div>
                <div class="navbar-entry"><a href="">Logout</a></div>
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
                <li class="non-current">
                    <a class="tab_link" href="weeklyReport.htm"><span></span>Reports</a>
                </li>
                <li class="current">
                    <a class="tab_link" href="jobType.htm"><span></span>Admin</a>
                </li>
                <li class="last"><span></span></li>
            </ul>
        </div>

        <div class="sHorizontalTabs">
            <div class="sHorizontalTabsInner">
                <ul>
                    <li class="active">
                        <a href="<c:url value="jobType.htm" />"><span>Job Type</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobStatus.htm" />"><span>Job Status</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobPriority.htm" />"><span>Job Priority</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobResolution.htm" />"><span>Job Resolution</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="jobStage.htm" />"><span>Job Stage</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="timesheetCode.htm" />"><span>Timesheet Code</span></a>
                    </li>
                    <li>
                        <a href="<c:url value="users.htm" />"><span>Users</span></a>
                    </li>
                </ul>
            </div>
        </div>
        
        <div class="button-region">
            <a href="<c:url value="jobTypeCreate.htm" />" class="button">Create</a>
        </div>
        
        <div class="report-region">
            <table cellpadding="0" border="0" cellspacing="0" summary="" class="report-standard">
                <tr >
                    <th></th>
                    <th></th>
                    <th>Job Type</th>
                    <th>Job Type Description</th>
                    <th>Active</th>
                    <th>Date Created</th>
                    <th>User Created</th>
                    <th>Date Modified</th>
                    <th>User Modified</th>
                </tr>
                <c:forEach items="${jobTypeList}" var="jobType">
                    <tr class="highlight-row">
                        <td><a href="<c:url value="jobTypeEdit.htm?id=${jobType.jobType}" />" >Edit</a></td>
                        <td><a href="<c:url value="jobTypeDelete.htm?id=${jobType.jobType}" />" >Delete</a></td>
                        <td><c:out value="${jobType.jobType}"/></td>
                        <td><c:out value="${jobType.jobTypeDesc}"/></td>
                        <td><c:out value="${jobType.active}"/></td>
                        <td><fmt:formatDate type="both" value="${jobType.dateCrt}"/></td>
                        <td><c:out value="${jobType.userCrtObj.firstName} ${jobType.userCrtObj.lastName}"/></td>
                        <td><fmt:formatDate type="both" value="${jobType.dateMod}"/></td>
                        <td><c:out value="${jobType.userModObj.firstName} ${jobType.userModObj.lastName}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <script type="text/javascript">
            $("table.report-standard tr").not(":first").hover(
                function () {
                  $(this).toggleClass("highlight-row-2");
                },
                function () {
                  $(this).toggleClass("highlight-row-2");
                }
            );
        </script>
    </body>
</html>
