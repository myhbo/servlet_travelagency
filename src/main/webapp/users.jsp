<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<%@include file="navbar.jspf" %>

<div class="container">
    <core:if test="${!requestScope.users.isEmpty()}">
    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="users.id"/></th>
            <th><fmt:message key="users.email"/></th>
            <th><fmt:message key="users.fullname"/></th>
            <th><fmt:message key="users.role"/></th>
            <th  class="text-center"><fmt:message key="users.update"/></th>
            <th><fmt:message key="users.status"/></th>
        </tr>
        </thead>
        <tbody>
        <core:forEach items="${requestScope.users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.email}</td>
            <td>${user.fullName}</td>
            <td>${user.role}</td>
            <td class="text-center">
                <a href="${pageContext.request.contextPath}/app/users/update?id=${user.id}" class="btn btn-primary">
                    <fmt:message key="users.update"/>
                </a>
            </td>
            <td class="text-center">
                <core:if test="${user.isEnabled()}">
                <a href="${pageContext.request.contextPath}/app/users/ban?id=${user.id}"
                   class="btn btn-danger btn-block">
                    <fmt:message key="users.status.ban"/>
                </a>
                </core:if>
                <core:if test="${!user.isEnabled()}">
                <a href="${pageContext.request.contextPath}/app/users/unban?id=${user.id}"
                   class="btn btn-success btn-block">
                    <fmt:message key="users.status.unban"/>
                </a>
                </core:if>
            </td>
        </tr>
        </core:forEach>
        </tbody>
    </table>
    </core:if>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>