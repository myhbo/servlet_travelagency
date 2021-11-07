<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="role" uri="myTags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All tours</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="navbar.jspf" %>

<div class="container">

    <div class="row" style="justify-content: center">

        <div>
            <c:if test="${!requestScope.tours.isEmpty()}">
                <h1>
                    <fmt:message key="tours.header"/>
                </h1>
            </c:if>
        </div>
        <div>
            <c:if test="${requestScope.tours.isEmpty()}">
                <h1>
                    <fmt:message key="tours.empty"/>
                </h1>
            </c:if>
        </div>
    </div>
    <role:hasRole role="ADMIN">
        <div class="row" style="justify-content: center; margin-bottom: 20px">
            <a href="${pageContext.request.contextPath}/app/tours/add"
               class="btn btn-primary"
               type="submit">
                <fmt:message key="tours.button.add"/>
            </a>
        </div>
    </role:hasRole>
    <c:if test="${!requestScope.tours.isEmpty()}">
        <table class="table">
            <thead>
            <tr>
                <role:hasRole role="MANAGER">
                    <th>
                        <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'id'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                            <fmt:message key="tours.id"/>
                        </a>
                    </th>
                </role:hasRole>
                <th>
                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'name'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="tours.name"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'tour_type'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="tours.tourtype"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'hotel_type'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="tours.hoteltype"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'group_size'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="tours.groupsize"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'price'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="tours.price"/>
                    </a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'is_hot'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                        <fmt:message key="tours.ishot"/>
                    </a>
                </th>
                <role:hasRole role="MANAGER">
                    <th>
                        <fmt:message key="tours.manage"/>
                    </th>
                </role:hasRole>
                <c:if test="${sessionScope.user != null}">
                    <th>
                        <fmt:message key="tours.order"/>
                    </th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.tours}" var="tour">
                <tr>
                    <role:hasRole role="MANAGER">
                        <td>
                    <span>
                            ${tour.id}
                    </span>
                        </td>
                    </role:hasRole>
                    <td>
                    <span>
                            ${tour.name}
                    </span>
                    </td>
                    <td>
                    <span>
                            ${tour.tourType.toString()}
                    </span>
                    </td>
                    <td>
                    <span>
                            ${tour.hotelType.toString()}
                    </span>
                    </td>
                    <td>
                    <span>
                            ${tour.groupSize}
                    </span>
                    </td>
                    <td>
                    <span>
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${tour.price}"/>
                    </span>
                    </td>
                    <td>
                        <c:if test="${tour.hot == true}">
                            <a>
                                <fmt:message key="tours.hot"/>
                            </a>
                        </c:if>
                        <c:if test="${tour.hot == false}">
                            <a>
                                <fmt:message key="tours.default"/>
                            </a>
                        </c:if>
                    </td>
                    <role:hasRole role="MANAGER">
                        <td>

                            <a href="${pageContext.request.contextPath}/app/tours/toggle-hot?id=${tour.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}" class="btn btn-primary">
                        <span>
                            <fmt:message key="tours.togglehot.button"/>
                        </span>
                            </a>
                            <role:hasRole role="ADMIN">
                                <a class="btn btn-warning"
                                   href="${pageContext.request.contextPath}/app/tours/update?id=${tour.id}">
                                    <fmt:message key="tours.button.update"/>
                                </a>
                                <a class="btn btn-danger"
                                   href="${pageContext.request.contextPath}/app/tours/delete?id=${tour.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                    <fmt:message key="tours.button.delete"/>
                                </a>
                            </role:hasRole>
                        </td>
                    </role:hasRole>
                    <c:if test="${sessionScope.user != null}">
                        <td>
                            <a class="btn btn-primary"
                               href="${pageContext.request.contextPath}/app/orders/add?id=${tour.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="tours.button.order"/>
                            </a>
                        </td>
                    </c:if>
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
                               href="${pageContext.request.contextPath}/app/tours?page=${0}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="pager.first"/>
                            </a>
                        </li>
                        <li class="page-item ${requestScope.currentPage == 0 ? 'disabled' : ''}">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage < 1 ? 0 : requestScope.currentPage - 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
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
                                       href="${pageContext.request.contextPath}/app/tours?page=${i}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
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
                               href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage + 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                <fmt:message key="pager.next"/>
                            </a>
                        </li>
                        <li class="page-item ${requestScope.currentPage == requestScope.totalPages - 1 ? 'disabled' : ''}" >
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app/tours?page=${requestScope.totalPages - 1}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
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
                                       href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${c}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
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