<%-- 
    Document   : login
    Created on : 25/07/2013, 9:17:30 PM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
    	<div class="header-region">
            <div class="navbar">
                <div class="app-title">JTrack</div>
                <div class="app-user">Welcome: ${currentUser.firstName}</div>
                <div class="navbar-entry"><a href="<c:url value="" />" >Logout</a></div>
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
          
        <div class="form-region-2">
            
             <c:if test="${param.error}">
              	<div class="container">
              		<div class="alert alert-danger">Bad credentials.</div>
	        	</div>
            </c:if>
            <c:if test="${param.logout}">
              	<div class="container">
              		<div class="alert alert-info">You have been logged out.</div>
	        	</div>
            </c:if>
            
            <table cellpadding="0" border="0" cellspacing="0" summary="" class="form-standard">
                <tr>
                    <th><div>JTrack - Login</div></th>
                </tr>
                <tr>
                    <td>
                    	<form:form method="POST" action="login">
                            <table>
                                <tbody>
                                    <tr>
                                        <td>User:</td>
                                        <td><input type="text" name="username" id="username" size="30" maxlength="40"  /></td>
                                    </tr>
                                    <tr>
                                        <td>Password:</td>
                                        <td><input type="password" name="password" id="password" size="30" maxlength="32" /></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td><input type="submit" value="Login" /></td>
                                    </tr>
                                </tbody>
                            </table>
                        </form:form>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>