<%-- 
    Document   : jobStatusEdit
    Created on : 03/07/2013, 10:01:44 PM
    Author     : Kan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/include.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Job Status Edit</title>S
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
        
        <c:if test="${not empty error}">
	        <div class="container">
				<div class="alert alert-danger">${error}</div>
	        </div>
        </c:if>
        
        <form:form method="POST" action="jobStatusEdit?id=${jobStatus}">
            <div class="button-region">
                <a href="<c:url value="jobStatus" />" class="button">Cancel</a>
                <input type="submit" value="Save" class="button" />
            </div>

            <div class="form-region">
                <table cellpadding="0" border="0" cellspacing="0" summary="" class="form-standard">
                    <tr>
                        <th><div>Edit Job Status</div></th>
                    </tr>
                    <tr>
                        <td>
                            <table border="0" summary="" >
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Status</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="jobStatus" readonly="true" class="read-only" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Job Status Description</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="jobStatusDesc" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right"><label>Active</label></td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <fieldset class="radio_group">
                                            <table summary="">
                                                <tr>
                                                    <td nowrap="nowrap">
                                                        <form:radiobutton path="active" value="0"/>
                                                        <label>No</label>
                                                    </td>
                                                    <td nowrap="nowrap">
                                                        <form:radiobutton path="active" value="1"/>
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