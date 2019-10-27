<%-- 
    Document   : jobCreate
    Created on : 06/07/2013, 10:03:23 PM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Job Create</title>
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
        
        <form:form method="POST" action="jobCreate.htm">
            <div class="button-region-2">
                <a href="<c:url value="job.htm" />" class="button">Cancel</a>
                <input type="submit" value="Save" class="button" />
            </div>

            <div class="report-region">
                <table cellpadding="0" border="0" cellspacing="0" summary="" class="report-standard">
                    <tr>
                        <th><div>Create Job</div></th>
                    </tr>
                    <tr>
                        <td>
                            <table border="0" summary="" >
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Name</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="jobName" size="80" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Description / Comments</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:textarea path="jobDesc" rows="3" cols="60" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Reference</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="jobRef" class="text-field" />
                                    </td>
                                </tr>
                                <tr>
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
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Stage</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:select path="jobStage" id="jobStage">
                                            <form:option value=""></form:option>
                                            <c:forEach items="${jobStageList}" var="jobStage">
                                                <form:option value="${jobStage.jobStage}">${jobStage.jobStage}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Priority</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:select path="jobPriority" id="jobPriority">
                                            <form:option value=""></form:option>
                                            <c:forEach items="${jobPriorityList}" var="jobPriority">
                                                <form:option value="${jobPriority.jobPriority}">${jobPriority.jobPriority}</form:option>
                                            </c:forEach>
                                        </form:select>
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
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Resolution</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:select path="jobResolution" id="jobResolution">
                                            <form:option value=""></form:option>
                                            <c:forEach items="${jobResolutionList}" var="jobResolution">
                                                <form:option value="${jobResolution.jobResolution}">${jobResolution.jobResolution}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Order</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="jobOrder" class="text-field" />
                                    </td>
                                </tr>
                                <tr>
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
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Estimated Hrs</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="estimatedHrs" class="text-field" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Timesheet Code</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:select path="timesheetCode" id="timesheetCode">
                                            <form:option value=""></form:option>
                                            <c:forEach items="${timesheetCodeList}" var="timesheetCode">
                                                <form:option value="${timesheetCode.timesheetCode}">${timesheetCode.timesheetCode} - ${timesheetCode.timesheetCodeDesc}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Estimated Start Date</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="estimatedStartDate" class="date-field" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Estimated End Date</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="estimatedEndDate" class="date-field" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Parent Job ( Project )</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:select path="parentJobNo" id="parentJobNo">
                                            <form:option value=""></form:option>
                                            <c:forEach items="${projectList}" var="project">
                                                <form:option value="${project.jobNo}">${project.jobName} - ${project.jobNo}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right"><label>Active</label></td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <fieldset  class="radio_group">
                                            <table summary="">
                                                <tr>
                                                    <td nowrap="nowrap">
                                                        <form:radiobutton path="active" value="0"/>
                                                        <label>No</label>
                                                    </td>
                                                    <td nowrap="nowrap">
                                                        <form:radiobutton path="active" value="1" checked="checked"/>
                                                        <label>Yes</label>
                                                    </td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                            </table> 
                        </td>
                    </tr>
                </table>
            </div>
        </form:form> 
        
    </body>
</html>
