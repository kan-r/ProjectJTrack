<%-- 
    Document   : timesheetEdit
    Created on : 17/07/2013, 9:31:53 PM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Timesheet Edit</title>
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
                <li class="non-first-current">
                    <a class="tab_link" href="job"><span></span>Jobs</a>
                </li>
                <li class="current">
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
        
        <form:form method="POST" action="timesheetEdit?id=${timesheetId}">
            <div class="button-region-2">
                <a href="<c:url value="timesheet" />" class="button">Cancel</a>
                <input type="submit" value="Save" class="button" />
            </div>

            <div class="form-region">
                <table cellpadding="0" border="0" cellspacing="0" summary="" class="form-standard">
                    <tr>
                        <th><div>Edit Timesheet</div></th>
                    </tr>
                    <tr>
                        <td>
                            <table border="0" summary="" >
                                <tr>
                                    <td></td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="timesheetId" type="hidden" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>User ID</label>
                                    </td>
                                    <td>
                                    	<form:input path="userId" readonly="true" class="read-only" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job No</label>
                                    </td>
                                    <td>
                                    	<form:input path="jobNo" readonly="true" class="read-only" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Worked Date</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="workedDate" class="read-only" readonly="true" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Worked Hrs</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="workedHrs" class="text-field" />
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