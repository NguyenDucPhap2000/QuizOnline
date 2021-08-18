<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="ContentStart">
    <div class="TakeQuizStart">
        <div class="TitleQuizStart">
            Welcome <span>${sessionScope.user}</span>
        </div>
        <div class="TimeRemaining">
            Time remaining
            <Span id="timer"></span>
            <input id="timeEnd" type="text" value="${sessionScope.timeEnd}"/>
        </div>
        <form action="TakeQuizControl" method="post">
            <div class="Question">
                <p>${QUESTION.getQuestion()}</p>
                <input name="op" value="1" type="checkbox" >${QUESTION.getOption1()}<br>
                <input name="op" value ="2" type="checkbox" >${QUESTION.getOption2()}<br>
                <input name="op" value ="3" type="checkbox" >${QUESTION.getOption3()}<br>
                <input name="op" value ="4" type="checkbox" >${QUESTION.getOption4()}<br>
            </div>
            <div class="Button">
                <input type="submit" value="Next" name="btnSubmit" >
            </div>
        </form>
    </div>
</div>
<script src="js/jsStartQuiz.js" type="text/javascript"></script>
