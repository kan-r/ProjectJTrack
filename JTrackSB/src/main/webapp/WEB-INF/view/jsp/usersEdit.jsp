<%-- 
    Document   : usersEdit
    Created on : 04/07/2013, 10:52:45 PM
    Author     : Kan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/include.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Edit</title>
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
        
        <form:form method="POST" action="usersEdit?id=${userId}">
            <div class="button-region">
                <a href="<c:url value="users" />" class="button">Cancel</a>
                <input type="submit" value="Save" class="button" />
            </div>

            <div class="form-region">
                <table cellpadding="0" border="0" cellspacing="0" summary="" class="form-standard">
                    <tr>
                        <th><div>Edit User</div></th>
                    </tr>
                    <tr>
                        <td>
                            <table border="0" summary="" >
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>User ID</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="userId" readonly="true" class="read-only" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Password</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="pword" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>First Name</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="firstName" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">
                                        <label>Last Name</label>
                                    </td>
                                    <td  colspan="1" rowspan="1" align="left" valign="middle">
                                        <form:input path="lastName" />
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