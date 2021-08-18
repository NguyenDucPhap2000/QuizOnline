<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/Home.css" rel="stylesheet" type="text/css"/>
        <title>Home Page</title>
    </head>
    <body>
        <div class="Container">
            <div class="Main">
                <jsp:include page="HeaderHome.jsp"/>
                <div class="LoginForm">
                    <div class="TitleandForm">
                        <div class="Title">Login Form</div>
                        <div class="Form">
                            <div class="UserAndPass">
                                <p>User name: </p>
                                <p>Password: </p>
                            </div>
                            <div>
                                <form action="HomeControl" method="post">
                                    <div class="textInput">
                                        <input type="text" name="user" value="${us}"/>
                                    </div>
                                    <div class="textInput">
                                        <input type="password" name="pass" value="${ps}"/>
                                    </div>
                                    <div class="SignInHome">
                                        <input type="submit" name="login" value="Sign in"/>
                                        <a href="RegisterControl">Register</a>
                                    </div>
                                </form>
                                <h4>${wrong}</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
