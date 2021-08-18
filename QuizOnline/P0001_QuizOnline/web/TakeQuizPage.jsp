<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/TakeQuiz.css" rel="stylesheet" type="text/css"/>
        <title>Take Quiz Page</title>
    </head>
    <body>
        <div class="Container">
            <div class="Main">
                <jsp:include page="HeaderHome.jsp"/>
                <div class="Content">
                    <form action="TakeQuizControl" method="post">
                        <c:if test="${empty QUESTION and empty SCORE}">
                            <div class="TakeQuiz">
                                <div class="TitleQuiz">
                                    Welcome <span>${sessionScope.user}</span>
                                </div>
                                <div class="NumberQues">
                                    <p>Enter number of questions: </p>
                                    <input type="text" name="numberQuestion" value="${sessionScope.number}"/>
                                    <span>${error}</span>
                                </div>
                                <div class="Button">
                                    <input type="submit" value="Start">
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty QUESTION}">
                            <%@include file="StartTakeQuiz.jsp" %>
                        </c:if>
                        <c:if test="${not empty SCORE}">
                            <%@include file="Result.jsp" %>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
