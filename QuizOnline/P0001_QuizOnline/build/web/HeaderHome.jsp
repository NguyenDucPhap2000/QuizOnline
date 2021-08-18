
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/Header.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="Preheader"></div>
        <div class="Header">
            <div class="HeaderTitle">
                <c:if test="${sessionScope.user != null}">
                    <a href="HomeControl">Home</a>
                    <!--student login-->
                    <c:if test="${sessionScope.role==2}">
                        <a href="TakeQuizControl">Take Quiz</a>
                        <a href="TakeQuizControl">Make Quiz</a>
                        <a href="TakeQuizControl">Manage Quiz</a>
                        <a href="LogOutControl">Log Out</a>
                    </c:if>
                    <!--teacher login-->
                    <c:if test="${sessionScope.role==1}">
                        <a href="TakeQuizControl">Take Quiz</a>
                        <a href="MakeQuizControl">Make Quiz</a>
                        <a href="ManageQuiz">Manage Quiz</a>
                        <a href="LogOutControl">Log Out</a>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <a href="HomeControl">Home</a>
                    <a href="HomeControl">Take Quiz</a>
                    <a href="HomeControl">Make Quiz</a>
                    <a href="HomeControl">Manage Quiz</a>
                </c:if>
            </div>
        </div>
    </body>
</html>
