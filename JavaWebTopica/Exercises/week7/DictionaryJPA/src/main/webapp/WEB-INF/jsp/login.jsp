<%--
  Created by IntelliJ IDEA.
  User: shyn
  Date: 26/07/2019
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Dictionary">
    <meta name="author" content="Shyn">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login Page</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="<c:url value="/resources/theme/css/loginstyle.css" />" rel="stylesheet">

</head>
<body>
<c:set var="message" value="${sessionScope.message}"/>
<div class="my-container">
    <div class="form-main">
        <h1 class="font-main" style="text-align: center">Dictionary</h1>
    </div>
    <form id="form-login" action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <input type="text" class="form-control text-input" id="input-username" placeholder="username" name="user">
            <div class="form-error">
                <small>${message}</small>
            </div>
        </div>

        <div class="form-group">
            <input type="password" class="form-control text-input" placeholder="password" name="pass">
        </div>
        <input class="btn btn-primary btn-block" id="btn-login" type="submit" value="Login">
    </form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
