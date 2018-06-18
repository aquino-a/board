<%-- 
    Document   : attributeForm
    Created on : 2018. 5. 28, 오전 9:32:43
    Author     : b005
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" 
uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>JSP Page</title>
    </head>
    <body>
        <form:form method="post" modelAttribute="objectForm" action="/form">
            <label for="name">Name: </label><form:input id="name" path="name" type="text"  value="${user.name}"/>
            <input type="submit" value="Submit!"/>
        </form:form>
    </body>
</html>
