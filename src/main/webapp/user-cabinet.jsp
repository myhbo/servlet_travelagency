<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="navbar.jspf" %>

<hr>
<div>
    <h4 class="row" style="justify-content: center">
        <fmt:message key="user.details"/>
    </h4>
    <div class="row" style="justify-content: center">
        <span class="text-info mr-2">
            <fmt:message key="users.cabinet.email"/>
        </span>
        <span>
            ${requestScope.user.email}
        </span>
    </div>
    <div class="row" style="justify-content: center">
        <span class="text-info mr-2">
            <fmt:message key="users.cabinet.full.name"/>
        </span>
        <span>
            ${requestScope.user.fullName}
        </span>
    </div>
</div>
<hr>

<%--<div class="row" style="justify-content: center; margin-top: 50px">

    <div th:if="${user.orders.isEmpty()}">
        <h4 class="display-5" th:text="#{users.cabinet.orders.empty}"></h4>
    </div>
    <div th:unless="${user.orders.isEmpty()}">
        <h4 class="display-5" th:text="#{users.cabinet.orders.header}"></h4>
    </div>
</div>
<div class="container">
    <table th:unless="${user.orders.isEmpty()}" class="table mt-20">
        <thead>
        <tr>
            <th sec:authorize="hasAnyAuthority('ADMIN', 'MANAGER')" th:text="#{users.cabinet.orders.id}"></th>
            <th th:text="#{users.cabinet.orders.tour}"></th>
            <th th:text="#{users.cabinet.orders.price}"></th>
            <th th:text="#{users.cabinet.orders.discount}"></th>
            <th th:text="#{users.cabinet.orders.status}" class="text-center"></th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="order : ${user.orders}">
            <td sec:authorize="hasAnyAuthority('ADMIN', 'MANAGER')">
                <span th:text="${order.id}"></span></td>
            <td><span th:text="${order.tour.name}"></span></td>
            <td><span th:text="${#numbers.formatDecimal(order.price,1,1)}"></span></td>
            <td><span th:text="${#numbers.formatDecimal(order.discount,1,1)}"></span></td>
            <td class="text-center" style="width: 15%">
                <button class="btn btn-info active btn-block" th:if="${order.isProcessing()}"
                        th:text="#{users.cabinet.orders.status.processing}"></button>
                <button class="btn btn-success active btn-block" th:if="${order.isConfirmed()}"
                        th:text="#{users.cabinet.orders.status.confirmed}"></button>
                <button class="btn btn-danger active btn-block" th:if="${order.isRejected()}"
                        th:text="#{users.cabinet.orders.status.rejected}"></button>
            </td>
        </tr>
        </tbody>
    </table>
</div>--%>



<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>