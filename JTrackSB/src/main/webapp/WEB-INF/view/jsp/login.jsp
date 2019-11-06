<%-- 
    Document   : login
    Created on : 25/07/2013, 9:17:30 PM
    Author     : Kan
--%>

<%@ include file="/WEB-INF/view/jsp/include.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>  
        <table width="100%" height="100%">
            <tr>
                <td align="center">
        <div class="report-region">
            <p>
                <c:if test="${error == true}">
                    <div class="error">
                        Invalid login credentials, try again.
                    </div>
                </c:if>
            </p>
            <table cellpadding="0" border="0" cellspacing="0" summary="" class="report-standard">
                <tr>
                    <th><div>JTrack - Login</div></th>
                </tr>
                <tr>
                    <td>
                        <form method="post" action="<c:url value='loginSuccess'/>" >
                            <table>
                                <tbody>
                                    <tr>
                                        <td>User:</td>
                                        <td><input type="text" name="username" id="username"size="30" maxlength="40"  /></td>
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
                        </form>
                    </td>
                </tr>
            </table>
        </div>
        </td>
        </tr>
        </table>
    </body>
</html>