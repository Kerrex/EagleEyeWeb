<%-- 
    Document   : eagleeye
    Created on : 2016-01-06, 19:22:58
    Author     : tomek

--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link href="css/simple-sidebar.css" rel="stylesheet">
<script src="js/jquery-1.12.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-datepicker.js"></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EagleEye Web</title>
</head>
<body>
<div id="wrapper">
    <div id="sidebar-wrapper" style="max-width: 15%">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">
                <a href="#">
                    EagleEye
                </a>
            </li>
            <li>
                <a href="#">Panel główny</a>
            </li>
            <li>
                <a href="eagleeye">Sprzedaż</a>
            </li>
            <li>
                <a href="#">Klienci</a>
            </li>
        </ul>
    </div>
    <div id="page-content-wrapper" class="container">
        <form action="eagleeye" method="get">
            <div class="form-group jumbotron" style="float: right; padding-right: 10px; width: 15%">
                <select class="form-control" size="40" multiple name="customers" style="height: 70%;">
                    <c:choose>
                        <c:when test="${not empty customerList}">
                            <c:forEach items="${customerList}" var="customer">
                                <option value="${customer.id}">${customer.name}</option>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </select>
            </div>
            <div class="jumbotron" style="float: left; width: 80%; ">
                <c:choose>
                    <c:when test="${not empty productList}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <td><h3>Nazwa produktu</h3></td>
                                <td><h3>EAN</h3></td>
                                <td><h3>Ilosc</h3></td>
                            </tr>
                            </thead>
                            <c:forEach items="${productList}" var="product">
                                <tr>
                                    <td>${product.name}</td>
                                    <td>${product.EAN}</td>
                                    <td>${product.quantity}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            Nie znaleziono produktów<br>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <br>
            <input type="hidden" name="action" value="search"/>
            <div class="input-daterange input-group" id="datepicker" style="margin:auto; width: 70%">
                <span class="input-group-addon">od</span>
                <input type="text" class="input-sm form-control" name="dpstart" value="${startDate}"/>
                <span class="input-group-addon">do</span>
                <input type="text" class="input-sm form-control" name="dpend" value="${endDate}"/>
            </div>
            <div style="display:table; margin:auto; padding-top: 3px;">
                <button id="find-button" type="submit" class="btn btn-primary btn-md">Znajdź</button>
            </div>
        </form>
    </div>
</div>

</body>
<script>
    $("#find-button").click(function () {
        $("#sales").load(eagleeye + " #sales");
    });
    $(document.getElementsByName("customers")).val(${selectedCustomers});
    $(document.getElementsByClassName("input-daterange")).datepicker({
        format: "dd-mm-yyyy",
        language: "PL"
    });
</script>
</html>
