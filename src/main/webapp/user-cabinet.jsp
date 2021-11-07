<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="role" uri="myTags" %>

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

<div class="row" style="justify-content: center; margin-top: 50px">

    <div>
        <c:if test="${requestScope.orders.isEmpty()}">
            <h4 class="display-5">
                <fmt:message key="users.cabinet.orders.empty"/>
            </h4>
        </c:if>
    </div>
    <div>
        <c:if test="${!requestScope.orders.isEmpty()}">
            <h4 class="display-5">
                <fmt:message key="users.cabinet.orders.header"/>
            </h4>
        </c:if>
    </div>
</div>
<div class="container">
    <c:if test="${!requestScope.orders.isEmpty()}">
        <table class="table mt-20">
            <thead>
            <tr>
                <role:hasRole role="MANAGER">
                    <th><fmt:message key="users.cabinet.orders.id"/></th>
                </role:hasRole>
                <th><fmt:message key="users.cabinet.orders.tour"/></th>
                <th><fmt:message key="users.cabinet.orders.price"/></th>
                <th><fmt:message key="users.cabinet.orders.discount"/></th>
                <th class="text-center"><fmt:message key="users.cabinet.orders.status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.user.orders}" var="order">
                <tr>
                    <role:hasRole role="MANAGER">
                        <td>
                            <span>
                                    ${order.id}
                            </span>
                        </td>
                    </role:hasRole>
                    <td>
                        <span>
                                ${order.tour.name}
                        </span>
                    </td>
                    <td>
                        <span>
                            <fmt:formatNumber type="number"
                                              maxFractionDigits="2"
                                              value="${order.price}"/>
                        </span>
                    </td>
                    <td>
                        <span>
                            <fmt:formatNumber type="number"
                                              maxFractionDigits="2"
                                              value="${order.discount}"/>
                        </span>
                    </td>
                    <td class="text-center" style="width: 15%">
                        <c:if test="${order.processing}">
                            <button class="btn btn-info active btn-block">
                                <fmt:message key="users.cabinet.orders.status.processing"/>
                            </button>
                        </c:if>
                        <c:if test="${order.confirmed}">
                            <button class="btn btn-success active btn-block">
                                <fmt:message key="users.cabinet.orders.status.confirmed"/>
                            </button>
                        </c:if>
                        <c:if test="${order.rejected}">
                            <button class="btn btn-danger active btn-block">
                                <fmt:message key="users.cabinet.orders.status.rejected"/>
                            </button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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