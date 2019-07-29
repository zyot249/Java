<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: shyn
  Date: 26/07/2019
  Time: 00:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dictionary</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script>
        function confirmDelete(e) {
            var id = e.target.getAttribute('data-word-id');
            bootbox.confirm("Are you sure to delete this word?", function (result) {
                if (result) {
                    window.location.href = $("#context-path").val() + "/delete?wordId=" + id;
                }
            });
        }
    </script>
    <style>
        a:-webkit-any-link {
            text-decoration: #00000000;
        }
    </style>
</head>
<body>
<c:set var="name" value="${sessionScope.name}"/>
<c:set var="message" value="${sessionScope.message}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="isAdmin" value="${sessionScope.role == 'admin'}"/>
<input type="hidden" id="context-path" value="${ contextPath }"/>
<div class="container mt-3">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-10">
                <h3>${name}</h3>
            </div>
            <div class="col-sm-2">
                <a href="${contextPath}/logout">
                    <button class="btn btn-primary btn-block">Logout</button>
                </a>
            </div>
        </div>
    </div>
    <p>${message}</p>
    <%--    Form search--%>
    <form class="form-inline" action="${contextPath}/search" method="post">
        <div class="input-group mt-3 mb-3">
            <div class="input-group-prepend">
                <label for="selectType" class="btn btn-secondary btn-block">Type:</label>
                <select class="form-control" id="selectType" name="type">
                    <c:forEach items="${sessionScope.transTypes}" var="transType">
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
            <input type="text" class="form-control" placeholder="Search" name="keyword">
            <div class="input-group-append">
                <button class="btn btn-success" type="submit">Go</button>
            </div>
        </div>
    </form>
    <c:if test="${isAdmin}">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-10"></div>
                <div class="col-sm-2">
                    <a href="${contextPath}/add">
                        <button class="btn btn-outline-primary btn-block">Add a word</button>
                    </a>
                </div>
            </div>
        </div>
    </c:if>
    <%--    End of form search--%>
    <c:if test="${not empty words}">
        <div style="min-height: 350px">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Word</th>
                    <th>Meaning</th>
                    <c:if test="${isAdmin}">
                        <th>Operations</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${words}" var="word">
                    <tr>
                        <td>${word.word}</td>
                        <td>${word.meaning}</td>
                        <c:if test="${isAdmin}">
                            <c:url value="/edit" var="editUrl">
                                <c:param name="wordId" value="${word.id}"/>
                            </c:url>
                            <c:url value="/delete" var="deleteUrl">
                                <c:param name="wordId" value="${word.id}"/>
                            </c:url>
                            <td>
                                <div class="btn-group btn-group-sm">
                                    <a href="${editUrl}" style="margin-right: 10px">
                                        <button
                                                type="button" class="btn btn-outline-warning btn-block">Edit
                                        </button>
                                    </a>
                                    <button type="button" class="btn btn-outline-danger btn-block"
                                            onclick="confirmDelete(event)"
                                            data-word-id="${word.id}">Delete
                                    </button>
                                </div>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <%--    Paging--%>
        <ul class="pagination justify-content-center">
            <c:forEach var="index" begin="1" end="${numOfPages}">
                <c:if test="${index == curPageIndex}">
                    <li class="page-item active"><a class="page-link"
                                                    href="${contextPath}/search?keyword=${curKeyword}&type=${sessionScope.curTransType}&pageIndex=${index}">${index}</a>
                    </li>
                </c:if>
                <c:if test="${index != curPageIndex}">
                    <li class="page-item"><a class="page-link"
                                             href="${contextPath}/search?keyword=${curKeyword}&type=${sessionScope.curTransType}&pageIndex=${index}">${index}</a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${warning != null}">
        <div style="text-align: center; color: red; font-size: 16px">
            <small>${message}</small>
        </div>
    </c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js"></script>
</body>
</html>
