<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="role" uri="myTags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add new tour</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="navbar.jspf" %>

<div class="d-flex justify-content-center align-items-center">
    <div>
        <div class="row" style="justify-content: center">
            <h2>
                <fmt:message key="tours.add.title"/>
            </h2>

        </div>
        <hr>
        <form method="post"
              action="${pageContext.request.contextPath}/app/tours/add">

            <div>
                <label for="name">
                    <fmt:message key="tours.add.name.label"/>
                </label>
                <input class="form-control"
                       id="name"
                       name="name"
                       value="${requestScope.tour.name}"
                       placeholder="<fmt:message key="tours.add.name.placeholder"/> "
                       type="text"
                       required/>
                <span class="text-danger">

                </span>
            </div>
            <div>
                <label for="tourType">
                    <fmt:message key="tours.add.type.label"/>
                </label>
                <select class="form-control"
                        id="tourType"
                        name="tourType">
                    <c:forEach items="${requestScope.tourTypes}" var="tourType">
                        <option value="${tourType.name()}">
                                ${tourType.toString()}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label for="hotelType">
                    <fmt:message key="tours.add.hoteltype.label"/>
                </label>
                <select class="form-control"
                        id="hotelType"
                        name="hotelType">
                    <c:forEach items="${requestScope.hotelTypes}" var="hotelType">
                        <option value="${hotelType.name()}">
                                ${hotelType.toString()}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label for="groupSize">
                    <fmt:message key="tours.add.groupsize.label"/>
                </label>
                <input class="form-control"
                       id="groupSize"
                       name="groupSize"
                       value="${requestScope.tour.groupSize}"
                       placeholder="<fmt:message key="tours.add.groupsize.label"/>"
                       type="number"
                       required/>
                <span class="text-danger">

                </span>
            </div>
            <div>
                <label for="price">
                    <fmt:message key="tours.add.price.label"/>
                </label>
                <input class="form-control"
                       id="price"
                       name="price"
                       value="${requestScope.tour.price}"
                       placeholder="<fmt:message key="tours.add.price.placeholder"/>"
                       type="number"
                       required/>
                <span class="text-danger">
                    <c:forEach items="${requestScope.errors}" var="error">
                        ${error.message}
                        <br>
                    </c:forEach>
                </span>
            </div>

            <div style="padding-top: 10px; margin-right: 150px; margin-left: 150px">
                <input class="btn btn-primary"
                       value="<fmt:message key="tours.add.button.submit"/>"
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