<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 10.02.16
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<script src="js/jquery-1.12.0.min.js"></script>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EagleEye Web</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="../sidebar.jsp"/>
    <div id="page-content-wrapper" class="container">
        <c:choose>
            <c:when test="${not empty customerList}">
                <form id="customerForm" method="get" action="customers">
                    <input type="hidden" name="idCustomer" id="idCustomer">
                    <input type="hidden" name="action" id="action">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <td><h3>Id klienta</h3></td>
                            <td><h3>Nazwa klienta</h3></td>
                            <td><h3>REGON</h3></td>
                        </tr>
                        </thead>
                        <c:forEach var="customer" items="${customerList}">
                            <tr>
                                <td><a href="#"><span onclick="edit('${customer.id}')">${customer.id}</span></a></td>
                                <td>${customer.name}</td>
                                <td>${customer.REGON}</td>
                                <td><a href="#"><span onclick="erase('${customer.id}')"
                                                      class="glyphicon glyphicon-trash"></span></a></td>
                            </tr>
                        </c:forEach>
                    </table>


                </form>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    Nie znaleziono klientów. Może to oznaczać problem z połączeniem z bazą danych<br>
                </div>
            </c:otherwise>
        </c:choose>
        <div style="margin: auto;">
            <form action="customers" method="get">
                <input type="hidden" value="search" name="action">
                Nazwa klienta: <input type="text" placeholder="Podaj nazwę klienta" name="name">
                REGON: <input type="text" placeholder="Podaj REGON" name="regon">
                <button type="submit" class="btn btn-primary btn-md">Znajdź</button>
            </form>
        </div>
    </div>
</div>
</body>
<script>
    function erase(id) {
        document.getElementById("idCustomer").value = id;
        document.getElementById("action").value = "erase";
        document.getElementById("customerForm").submit();
    }
    function edit(id) {
        document.getElementById("idCustomer").value = id;
        document.getElementById("action").value = "edit";
        document.getElementById("customerForm").submit();
    }
</script>
</html>
