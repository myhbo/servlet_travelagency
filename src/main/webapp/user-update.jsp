<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Update user</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<%@include file="navbar.jspf" %>
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
        <div>
            <div class="row" style="justify-content: center">
                <h2>
                    <fmt:message key="tours.update.title"/>
                </h2>
            </div>
            <hr>
            <form action="${pageContext.request.contextPath}/app/users/update?id=${requestScope.user.id}"
                  method="post">

                <div>
                    <label>
                        <span>
                            <fmt:message key="users.update.email.label"/>
                        </span>
                        <input type="text"
                               name="email"
                               placeholder="<fmt:message key="users.update.email.placeholder"/>"
                               class="form-control"/>
                        <span class="text-danger">

                        </span>
                    </label>
                </div>
                <div>
                    <label>
                        <span>
                            <fmt:message key="users.update.password.label"/>
                        </span>
                        <input class="form-control"
                               id="password"
                               name="password"
                               type="password"
                               placeholder="<fmt:message key="users.update.password.label"/>">
                        <span class="text-danger">

                        </span>
                    </label>
                </div>

                <div>
                    <label>
                        <span>
                            <fmt:message key="users.update.fullname.label"/>
                        </span>
                        <input type="text"
                               name="fullName"
                               value="${requestScope.user.fullName}"
                               placeholder="<fmt:message key="users.update.fullname.placeholder"/>"
                               class="form-control"/>
                        <span class="text-danger">

                        </span>
                    </label>
                </div>
                <div>
                    <label for="roles">
                        <fmt:message key="user.roles"/>
                    </label>
                    <select class="form-control"
                            id="roles"
                            name="roles">
                        <c:forEach items="${requestScope.roles}" var="role">
                            <option value="${role.toString()}">
                                ${role.toString()}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <span class="text-danger">
                    <c:forEach items="${requestScope.errors}" var="error">
                        ${error.message}
                        <br>
                    </c:forEach>
                </span>
                <div class="row" style="justify-content: center; padding-top: 10px">

                    <input class="btn btn-primary"
                           value="<fmt:message key="users.update.button.submit"/>"
                           type="submit">
                </div>

            </form>
        </div>
    </div>
</div>


<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</body>
</html>
