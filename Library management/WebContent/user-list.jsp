<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Library Management Application</title>
    <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
        style="background-color: orange">
        <div>
            <a href="https://www.xadmin.net" class="navbar-brand"> Library
                Management Application </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="${pageContext.request.contextPath}/list"
                class="nav-link">Users</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">
    <!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

    <div class="container">
        <h3 class="text-center">List of Users</h3>
        <hr>
        <div class="container text-left">
            <a href="${pageContext.request.contextPath}/new" class="btn btn-success">Add
                New User</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Country</th>
                    <th>Book Name</th>
                    <th>Issued Date</th>
                    <th>Due Date</th>
                    <th>Fine</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>

                <c:forEach var="user" items="${listUser}">
                    <tr>
                        <td><c:out value="${user.id}" /></td>
                        <td><c:out value="${user.name}" /></td>
                        <td><c:out value="${user.email}" /></td>
                        <td><c:out value="${user.country}" /></td>
                        <td><c:out value="${user.bookname}" /></td>
                        <td><fmt:formatDate value="${user.issuedDate}" pattern="yyyy-MM-dd" /></td>
                        <td><fmt:formatDate value="${user.dueDate}" pattern="yyyy-MM-dd" /></td>
                        <td><c:out value="${user.fine}" /></td>
                        <td>
                            <a href="edit?id=${user.id}">Edit</a>
                            &nbsp;&nbsp;&nbsp;&nbsp; 
                            <a href="delete?id=${user.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
</div>
</body>
</html>
