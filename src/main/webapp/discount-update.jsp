<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="role" uri="myTags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Set discount</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@ include file="navbar.jspf" %>

<style>
    .container-fluid {
        align-items: center;
        justify-content: center;
        display: flex;
        align-self: stretch;
    }
</style>
<div class="container-fluid">
    <div>
        <form method="post"
              action="${pageContext.request.contextPath}/app/orders/set-discount?id=${requestScope.order.id}">

            <div>
                <label for="discount">
                    <fmt:message key="orders.set.discount"/>
                </label>
                <input class="form-control"
                       id="discount"
                       name="discount"
                       value="${requestScope.order.discount}"
                       placeholder="<fmt:message key="orders.set.discount"/>"
                       type="text"
                       required/>
            </div>
            <span class="text-danger">
                <c:forEach items="${requestScope.errors}" var="error">
                    ${error.message}
                    <br>
                </c:forEach>
            </span>
            <div class="row" style="justify-content: center; margin-top: 20px">
                <input class="btn btn-primary"
                       value="<fmt:message key="orders.button.set"/>"
                       type="submit">
            </div>
        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>