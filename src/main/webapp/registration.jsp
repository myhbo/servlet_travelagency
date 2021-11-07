<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration form</title>
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
        <div class="row" style="justify-content: center">
            <h1>
                <fmt:message key="registration.header"/>
            </h1>
        </div>
        <hr>
        <div>
            <form action="${pageContext.request.contextPath}/app/registration" method="post">
                <div>
                    <label>
                        <span>
                            <fmt:message key="registration.email.label"/>
                        </span>
                        <input type="text"
                               name="email"
                               value="${requestScope.user.email}"
                               placeholder="<fmt:message key="registration.email.placeholder"/>"
                               class="form-control"
                               required/>

                        <span class="text-danger">
                            <c:forEach items="${requestScope.errors.emailErrors}" var="error">
                                ${error}
                            </c:forEach>
                        </span>
                    </label>
                </div>
                <div>
                    <label>
                        <span>
                            <fmt:message key="registration.password.label"/>
                        </span>
                        <input type="password"
                               name="password"
                               value="${requestScope.user.password}"
                               placeholder="<fmt:message key="registration.password.placeholder"/>"
                               class="form-control"
                               required/>
                        <span class="text-danger">

                        </span>
                    </label>
                </div>
                <div>
                    <label>
                        <span>
                            <fmt:message key="registration.fullname.label"/>
                        </span>
                        <input type="text"
                               name="fullName"
                               value="${requestScope.user.fullName}"
                               placeholder="<fmt:message key="registration.fullname.placeholder"/>"
                               class="form-control"
                               required/>
                        <span class="text-danger">

                        </span>
                    </label>
                </div>

                <div class="row" style="justify-content: center">
                    <button type="submit" class="btn btn-primary" style="margin-top: 20px">
                        <fmt:message key="button.sign_up"/>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>