<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 15.02.16
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<script src="js/jquery-1.12.0.min.js"></script>
<script src="js/bootstrap-datepicker.js"></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EagleEye Web</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="sidebar.html"/>
    <div id="page-content-wrapper" class="container">
        <div style="text-align: center" class="form-group jumbotron"><h3>W ciągu ostatnich 7 dni sprzedano:</h3><br>
            <h4>${lastWeek}</h4></div>
        <div style="text-align: center" class="form-group jumbotron"><h3>W ciągu ostatnich 30 dni sprzedano:</h3><br>
            <h4>${lastMonth}</h4></div>
        <div style="text-align: center" class="form-group jumbotron"><h3>W ciągu ostatnich 365 dni sprzedano:</h3><br>
            <h4>${lastYear}</h4></div>
    </div>
</div>
</body>
</html>