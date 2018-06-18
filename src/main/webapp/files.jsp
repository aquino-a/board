<%-- 
    Document   : files
    Created on : 2018. 6. 4, 오후 1:35:17
    Author     : b005
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="c" uri="" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Files</title>
        <style>
            #picture img {
                max-width: 300px;
                height:auto;
                float:left;
            }
            #links {
                float:left;
            }

        </style>
        <script src="http://code.jquery.com/jquery-3.3.1.js"></script> 
        <script>
            function setImage(path) {
                $("#picture").text("").prepend($('<img>', {id: 'pic', src: path}));
            }
            ;
        </script>

    </head>
    <body>
        <c:if test="${files == null}" >
            <form action="files" method="post" enctype="multipart/form-data">
                <label for="file">Upload File: </label><input id="file" type="file" name="files" multiple /><br/>
                <input type="submit" value="Submit"/>
            </form>
        </c:if>
        <c:if test="${files != null}">
            <div id="links">
                <c:forEach items="${files}" var="file">
                    파일명: <a href="javascript:setImage('/uploads/${file}');"><c:out value="${file}" /></a><br/>
                </c:forEach>
            </div>
        </c:if>
        <div id="picture">

        </div>



    </body>
</html>
