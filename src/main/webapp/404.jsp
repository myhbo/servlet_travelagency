<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="role" uri="myTags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
  <meta charset="UTF-8">

  <title>404</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<%@ include file="navbar.jspf" %>
<h6 style="font-size: xxx-large;
           font-family: 'Adobe Devanagari';
           text-align: center;
           margin-top: fill">404</h6>
<hr>
<h6 style="font-size: xxx-large;
           font-family: 'Adobe Devanagari';
           text-align: center;
           margin-top: fill">
  <fmt:message key="404"/>
</h6>
</body>
</html>