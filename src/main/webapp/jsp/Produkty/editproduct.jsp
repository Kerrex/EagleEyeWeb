<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 13.02.16
  Time: 12:39
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
    <jsp:include page="../sidebar.html"/>
    <div id="page-content-wrapper" class="container">
        <c:if test="${state == 'failed'}">
            <div class="alert alert-danger">
                Nie udało się zmienić produktu, prawdopodobnie już istnieje
            </div>
        </c:if>
        <form method="post" action="products">
            <input type="hidden" name="action" value="editproduct">
            <input type="hidden" name="ean" value="${product.EAN}">
            <div class="form-group">
                <label for="name">Nazwa produktu:</label>
                <input type="text" class="form-control" name="name" id="name" value="${product.name}"
                       placeholder="Podaj nazwę produktu">
            </div>
            <div class="form-group">
                <label for="ean">EAN:</label>
                <input type="text" class="form-control" name="newean" id="ean" value="${product.EAN}"
                       placeholder="Podaj EAN">
            </div>
            <button type="submit" class="btn btn-primary">Edytuj</button>
        </form>
    </div>
</div>

</body>
</html>
