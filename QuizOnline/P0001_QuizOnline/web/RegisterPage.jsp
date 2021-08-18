<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/Register.css" rel="stylesheet" type="text/css"/>
        <title>Register Page</title>
    </head>
    <body>
        <div class="Container">
            <div class="Main">
                <jsp:include page="HeaderHome.jsp"/>
                <div class="LoginForm">
                    <div class="TitleandForm">
                        <div class="Title">Registration Form</div>
                        <div class="Form">
                            <div class="TextLeft">
                                <p>User Name: </p>
                                <p>Password: </p>
                                <p>User type: </p>
                                <p>Email: </p>
                            </div>
                            <div class="InputRight">
                                <form action="RegisterControl" method="post">
                                    <div class="textInput">
                                        <input type="text" name="user" value="${sessionScope.accountName}"/>
                                    </div>
                                    <div class="textInput">
                                        <input type="password" name="pass" value="${sessionScope.Password}"/>
                                    </div>
                                    <div class="SelectOption">
                                        <select name="role">
                                            <c:forEach items="${list}" var="c">
                                                <option value="${c.name}" ${roleName==c.name?"selected":""}>${c.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="textEmail">
                                        <input type="text" name="email" value="${sessionScope.email}"/>
                                    </div>
                                    <div class="submit">
                                        <input type="submit" name="Sign in" value="Register"/>
                                    </div>
                                </form>
                                <h4 class="fail">${sucessOrDuplicate}</h4>
                                <h4 class="success">${sucess}</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
