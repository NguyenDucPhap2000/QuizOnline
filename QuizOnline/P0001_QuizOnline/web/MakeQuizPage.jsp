<%-- 
    Document   : MakeQuizPage
    Created on : Jun 20, 2021, 2:31:45 PM
    Author     : nguye
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/MakeQuiz.css" rel="stylesheet" type="text/css"/>
        <title>Manage Quiz Page</title>
    </head>
    <body>
        <div class="Container">
            <div class="Main">
                <jsp:include page="HeaderHome.jsp"/>
                <div class="Content">
                    <form action="MakeQuizControl" method="post">
                        <div class="MakeQuiz">
                            <div class="QuestionName">
                                <div>
                                    <span>${q}</span>
                                </div>
                                <div>
                                    <textarea name="question">${question}</textarea>
                                    <span class="alert">${alert}</span>
                                </div>
                            </div>
                            <div class="Answer">
                                <div class="OptionText">
                                    <div class="optionQues">
                                        <span>Option 1: </span>
                                    </div>
                                    <div class="optionQues">
                                        <span>Option 2: </span>
                                    </div>
                                    <div class="optionQues">
                                        <span>Option 3: </span>
                                    </div>
                                    <div class="optionQues">
                                        <span>Option 4: </span>
                                    </div>
                                </div>
                                <div class="AnswerText">
                                    <div class="EnterText">
                                        <textarea name="op1">${op1}</textarea>
                                    </div>
                                    <div class="EnterText">
                                        <textarea name="op2">${op2}</textarea>
                                    </div>
                                    <div class="EnterText">
                                        <textarea name="op3">${op3}</textarea>
                                    </div>
                                    <div class="EnterText">
                                        <textarea name="op4">${op4}</textarea>
                                    </div>
                                </div>
                            </div>
                            <p class="alert">${alert2}</p>
                            <div class="ChooseResult">
                                <div>
                                    <span>Answer(s):</span>
                                </div>
                                <div class="Option">
                                    <div>
                                        <input name="cbo" type="checkbox" value="1">
                                        <span>Option 1</span>
                                    </div>
                                    <div>
                                        <input name="cbo" type="checkbox" value="2">
                                        <span>Option 2</span>
                                    </div>
                                    <div>
                                        <input name="cbo" type="checkbox" value="3">
                                        <span>Option 3</span>
                                    </div>
                                    <div>
                                        <input name="cbo" type="checkbox" value="4">
                                        <span>Option 4</span>
                                    </div>
                                </div>
                            </div>
                            <p class="alert">${alert3}</p>
                            <p class="alertSuccess">${alert4}</p>
                            <div class="ButtonSave">
                                <input type="submit" value="Save">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
