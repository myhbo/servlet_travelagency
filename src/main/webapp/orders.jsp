<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="role" uri="myTags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All orders</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="navbar.jspf" %>

<div class="container">
    <div class="row" style="justify-content: center">
        <div>
            <c:if test="${!requestScope.orders.isEmpty()}">
                <h1 class="display-5">
                    <fmt:message key="orders.header"/>
                </h1>
            </c:if>
        </div>
        <div>
            <c:if test="${requestScope.orders.isEmpty()}">
                <h1 class="display-5">
                    <fmt:message key="orders.empty"/>
                </h1>
            </c:if>
        </div>
    </div>

    <c:if test="${!requestScope.orders.isEmpty()}">
        <table class="table">
            <thead>
            <tr>
                <th>
                    <a href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'orders.id'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="orders.id"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'tours.name'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="orders.tour"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'users.full_name'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="orders.user"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'orders.price'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="orders.price"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'orders.discount'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="orders.discount"/>
                    </a>
                </th>
                <th class="text-center">
                    <fmt:message key="orders.set.discount"/>
                </th>
                <th class="text-center">
                    <a href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'orders.status'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="orders.status"/>
                    </a>
                </th>
                <th class="text-center">
                    <fmt:message key="tours.manage"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.orders}" var="order">
                <tr>
                    <td>
                        <span>
                                ${order.id}
                        </span>
                    </td>
                    <td>
                        <span>
                                ${order.tour.name}
                        </span>
                    </td>
                    <td>
                        <span>
                                ${order.user.fullName}
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
                    <td>
                        <c:if test="${order.processing}">
                            <a class="btn btn-primary"
                               href="${pageContext.request.contextPath}/app/orders/set-discount?id=${order.id}">
                                <fmt:message key="orders.button.set"/>
                            </a>
                        </c:if>

                    </td>

                    <td class="text-center" style="width: 15%">
                        <c:if test="${order.processing}">
                            <button class="btn btn-info active btn-block">
                                <fmt:message key="orders.processing"/>
                            </button>
                        </c:if>
                        <c:if test="${order.confirmed}">
                            <button class="btn btn-success active btn-block">
                                <fmt:message key="orders.confirmed"/>
                            </button>
                        </c:if>
                        <c:if test="${order.rejected}">
                            <button class="btn btn-danger active btn-block">
                                <fmt:message key="orders.rejected"/>
                            </button>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${order.processing}">
                            <a class="btn btn-success"
                               href="${pageContext.request.contextPath}/app/orders/mark-confirmed?id=${order.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="orders.status.button.mark.confirmed"/>
                            </a>
                            <a class="btn btn-danger"
                               href="${pageContext.request.contextPath}/app/orders/mark-rejected?id=${order.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="orders.status.button.mark.rejected"/>
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
                               href="${pageContext.request.contextPath}/app/orders?page=${0}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="pager.first"/>
                            </a>
                        </li>
                        <li class="page-item ${requestScope.currentPage == 0 ? 'disabled' : ''}">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage < 1 ? 0 : requestScope.currentPage - 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
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
                                       href="${pageContext.request.contextPath}/app/orders?page=${i}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
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
                               href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage + 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="pager.next"/>
                            </a>
                        </li>
                        <li class="page-item ${requestScope.totalPages == 0 ? 'disabled' : ''}" >
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app/orders?page=${requestScope.totalPages - 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
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
                                       href="${pageContext.request.contextPath}/app/orders?page=${requestScope.currentPage}&size=${c}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
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