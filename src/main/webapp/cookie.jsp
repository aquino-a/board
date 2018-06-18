<%-- 
    Document   : cookie
    Created on : 2018. 5. 31, 오전 11:34:48
    Author     : b005
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Cookies</title>
    </head>
    <body>
        <c:if test="${cookie != null}">
            <c:forEach var="cookies" items="${cookie}">

            <bold>Name: ${cookies.key}</bold><br/>
            Max Age: ${cookies.value.maxAge} <br/>
            Value: ${cookies.value.value}<br/>
            Secure: ${cookies.value.secure}<br/>
            <br/>
        </c:forEach>
        <form action="cookie" method="post">
            <select name="delete">
                <c:forEach var="cookies" items="${cookie}">
                    <option value="${cookies.key}">${cookies.key}</option>
                </c:forEach>
            </select>
            <input type="submit" value="삭제"/>
        </form>
    </c:if>
    <c:if test="${cookie == null}">
        NO COOKIES
    </c:if>
</body>
</html>
