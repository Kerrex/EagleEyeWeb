<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 12.02.16
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <c:if test="${state == 'failed'}">
            <div class="alert alert-danger">
                Nie udało się dodać klienta, prawdopodobnie już istnieje
            </div>
        </c:if>
        <form method="post" action="customers">
            <input type="hidden" name="action" value="editcustomer">
            <input type="hidden" name="idCustomer" value="${customer.id}">
            <div class="form-group">
                <label for="name">Nazwa klienta:</label>
                <input type="text" class="form-control" name="name" id="name" value="${customer.name}"
                       placeholder="Podaj nazwę klienta">
            </div>
            <div class="form-group">
                <label for="regon">REGON:</label>
                <input type="text" class="form-control" name="regon" id="regon" value="${customer.REGON}"
                       placeholder="Podaj REGON">
            </div>
            <button type="submit" class="btn btn-primary">Edytuj</button>
        </form>
    </div>
</div>

</body>
</html>
