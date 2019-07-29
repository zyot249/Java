<%--
  Created by IntelliJ IDEA.
  User: shyn
  Date: 28/07/2019
  Time: 23:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Word Detail</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        a:-webkit-any-link {
            text-decoration: #00000000;
        }
    </style>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="id" value=""/>
<c:set var="key" value="-1"/>
<c:set var="mean" value=""/>
<c:if test="${word != null}">
    <c:set var="key" value="${word.word}"/>
    <c:set var="mean" value="${word.meaning}"/>
    <c:set var="id" value="${word.id}"/>
</c:if>
<c:if test="${not empty transTypes}">
    <c:set var="Types" value="${transTypes}"/>
</c:if>

<c:if test="${empty transTypes}">
    <c:set var="Types" value="${sessionScope.transTypes}"/>
</c:if>
<div class="container mt-3">
    <%--    Header--%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2">
                <a href="${contextPath}/search">
                    <button class="btn btn-primary btn-block">Back</button>
                </a>
            </div>
            <div class="col-sm-8">
                <h1 style="text-align: center">${op}</h1>
            </div>
            <div class="col-sm-2"></div>
        </div>
    </div>
    <%--        Form--%>
    <form action="${contextPath}/update?id=${word.id}" method="post">
        <%--    Body--%>
        <div class="form-group">
            <c:if test="${op == 'Edit'}">
                <label for="key">Word:</label>
                <input type="text" class="form-control" readonly="readonly" id="key" name="word" value="${key}">
            </c:if>
            <c:if test="${op == 'Add'}">
                <label for="key">Word:</label>
                <input type="text" class="form-control" id="key" name="word">
            </c:if>
        </div>
        <div class="form-group">
            <label for="mean">Meaning:</label>
            <input type="text" class="form-control" id="mean" name="meaning" value="${mean}">
        </div>
        <div class="form-group">
            <label for="selectType">Type:</label>
            <select class="form-control" id="selectType" name="type">
                <c:forEach items="${Types}" var="transType">
                    <c:choose>
                        <c:when test="${transType == sessionScope.curTransType}">
                            <option value="${transType}"
                                    selected="selected">${transType}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${transType}">${transType}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <%--    Submit--%>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-5"></div>
                <div class="col-sm-2">
                    <button type="submit" class="btn btn-outline-success btn-block">Update</button>
                </div>
                <div class="col-sm-5"></div>
            </div>
        </div>
    </form>
    <div style="text-align: center; color: red; font-size: 20px">
        <small>${warning}</small>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
