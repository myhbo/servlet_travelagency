<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:if test="${!requestScope.users.isEmpty()}">
        <table class="table">
            <thead>
            <tr>
                <th>
                    <a href="${pageContext.request.contextPath}/app/users?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'id'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="users.id"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/users?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'email'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="users.email"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/users?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'full_name'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="users.fullname"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/users?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'users.role'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="users.role"/>
                    </a>
                </th>
                <th  class="text-center">
                    <fmt:message key="users.update"/>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/users?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'enabled'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="users.status"/>
                    </a>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.users}" var="user">
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
                        <c:if test="${user.isEnabled()}">
                            <a href="${pageContext.request.contextPath}/app/users/ban?id=${user.id}"
                               class="btn btn-danger btn-block">
                                <fmt:message key="users.status.ban"/>
                            </a>
                        </c:if>
                        <c:if test="${!user.isEnabled()}">
                            <a href="${pageContext.request.contextPath}/app/users/unban?id=${user.id}"
                               class="btn btn-success btn-block">
                                <fmt:message key="users.status.unban"/>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
<div>
    <c:if test="${requestScope.totalPages > 0}">
        <div class="m-3">
            <div class="row" style="justify-content: center">
                <nav>
                    <ul class="pagination">
                        <li class="page-item ${requestScope.currentPage == 0 ? 'disabled' : ''}" >
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app/users?page=${0}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="pager.first"/>
                            </a>
                        </li>
                        <li class="page-item ${requestScope.currentPage == 0 ? 'disabled' : ''}">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app/users?page=${requestScope.currentPage < 1 ? 0 : requestScope.currentPage - 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="pager.previous"/>
                            </a>
                        </li>
                        <c:if test="${requestScope.currentPage >= 3}">
                            <li class="page-item disabled">
                                <a class="page-link" href="#">
                                    ...
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="${(requestScope.currentPage < 1 ? requestScope.currentPage : (requestScope.currentPage == 1 ? requestScope.currentPage - 1 : requestScope.currentPage - 2))}"
                                   end="${(requestScope.currentPage < requestScope.totalPages - 3 ? requestScope.currentPage + 2 : (requestScope.currentPage == requestScope.totalPages - 2 ? requestScope.currentPage + 1 : requestScope.totalPages - 1))}"
                                   step="1"
                                   var="i">
                            <li class="page-item ${i == requestScope.currentPage ? 'active' : ''}">
                                <c:if test="${i == requestScope.currentPage}">
                                    <a class="page-link" href="#">
                                            ${i + 1}
                                    </a>
                                </c:if>
                                <c:if test="${i != requestScope.currentPage}">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/app/users?page=${i}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                            ${i + 1}
                                    </a>
                                </c:if>
                            </li>
                        </c:forEach>
                        <c:if test="${requestScope.currentPage + 3 < requestScope.totalPages}">
                            <li class="page-item disabled">
                                <a class="page-link" href="#">
                                    ...
                                </a>
                            </li>
                        </c:if>
                        <li class="page-item ${requestScope.currentPage == requestScope.totalPages - 1 ? 'disabled' : ''}">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app/users?page=${requestScope.currentPage + 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="pager.next"/>
                            </a>
                        </li>
                        <li class="page-item ${requestScope.currentPage == requestScope.totalPages - 1 ? 'disabled' : ''}" >
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app/users?page=${requestScope.totalPages - 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="pager.last"/>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row" style="justify-content: center">
                <nav>
                    <ul class="pagination">
                        <li class="page-item disabled">
                            <p class="page-link">
                                <fmt:message key="pager.items"/>
                            </p>
                        </li>

                        <c:forEach items="${requestScope.pagerSizes}" var="c">
                            <li class="page-item ${c == requestScope.pageSize ? 'active' : ''}">
                                <c:if test="${c == requestScope.pageSize}">
                                    <a class="page-link" href="#">
                                            ${c}
                                    </a>
                                </c:if>
                                <c:if test="${c != requestScope.pageSize}">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/app/users?page=${0}&size=${c}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                            ${c}
                                    </a>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>
    </c:if>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>