<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ContentResult">
    <div class="Result">
        <div class="Score">
            Your score
            <span> ${sessionScope.result} (${sessionScope.result2}%) </span>
            <p class="${sessionScope.PassorNot}"> - ${sessionScope.result >= 5?"Passed":"Not Passed"} </p>
            <c:if test="${not empty sessionScope.CHEATING}">
                <p class="notPass"> - Reject</p>
            </c:if>    
        </div>
        <div class="AnotherTest">
            Take another Test 
            <form action="TakeQuizControl" method="post">
                <input name="StartResult" type="submit" value="Start">
            </form>
        </div>
    </div>
</div>
