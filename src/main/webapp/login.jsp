<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="role" uri="myTags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<%@include file="navbar.jspf" %>

<form style="width: 550px" class="form-signin container"   action="${pageContext.request.contextPath}/app/login" method="post">
    <h1 class="mb-3 font-weight-normal" >
        <fmt:message key="login.header"/>
    </h1>
    <c:if test="${requestScope.success}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <span>
            <fmt:message key="login.logout.success"/>
        </span>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    </c:if>
    <label for="email" class="sr-only">
        <fmt:message key="login.label.email"/>
    </label>
    <input type="text"
           id="email"
           name="email"
           class="form-control"
           placeholder="<fmt:message key="login.label.email.placeholder"/>"
           required
           autofocus>
    <label for="password" class="sr-only">
        <fmt:message key="login.label.password"/>
    </label>
    <input type="password"
           name="password"
           id="password"
           placeholder="<fmt:message key="login.input.password.placeholder"/>"
           required
           class="form-control">
    <c:if test="${requestScope.error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <span>
            <fmt:message key="login.invalid"/>
        </span>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    </c:if>
    <button class="btn btn-lg btn-primary btn-block" type="submit">
        <fmt:message key="button.sign_in"/>
    </button>
</form>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>