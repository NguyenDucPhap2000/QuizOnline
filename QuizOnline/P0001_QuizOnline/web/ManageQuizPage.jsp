<%-- 
    Document   : ManageQuizPage
    Created on : Jun 20, 2021, 3:50:44 PM
    Author     : nguye
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/ManageQuiz.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="Container">
            <div class="Main">
                <jsp:include page="HeaderHome.jsp"/>
                <div class="Content">
                    <div class="Manage">
                        <div class="NumberQuestion">
                            <p>Number of question: </p>
                            <span> ${count}</span>
                        </div>
                        <div class="LeftAndRight">
                            <div class="left">
                                <div class="Question">
                                    <div class="Ques">
                                        <p>Question</p>
                                    </div>
                                    <c:forEach items="${listquestion}" var="o">
                                        <div class="QuestionNumber">
                                            <p>${o.question}</p>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="right">
                                <div class="DateCreated">
                                    <p>DateCreated</p>
                                </div>
                                <c:forEach items="${listquestion}" var="o">
                                    <div class="DateAndDelete">
                                        <div class="DateSQL">
                                            <p>${o.FormatDate()}</p>
                                        </div>
                                        <div class="delete">
                                            <form action="ManageQuiz" method="post">
                                                <input class="idQuestion" name="IdQuestion" type="text" value="${o.id}"/>
                                                <input id="delete" onclick="clickDelete()" name="Delete" type="submit" value="Delete"/>
                                            </form>
                                        </div>
                                    </div>
                                </c:forEach>
                                <h3>${SucessOrFail}</h3>
                            </div>
                        </div>
                        <!--paging-->
                        <div class="paging">
                            <c:forEach begin="1" end="${endpage}" var="i">
                                <a class="${i==index?"choosen":""}" href="ManageQuiz?index=${i}">${i}</a>
                            </c:forEach>
                            <h3>${notfound}</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="js/ManageQuiz.js" type="text/javascript"></script>
</html>
