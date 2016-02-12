<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 12.02.16
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<script src="js/jquery-1.12.0.min.js"></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EagleEye Web</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="../sidebar.html"/>
    <div id="page-content-wrapper" class="container">
        <c:if test="${state == 'success'}">
            <div class="alert alert-info">
                Dodano produkt
            </div>
        </c:if>
        <c:if test="${state == 'failed'}">
            <div class="alert alert-danger">
                Nie udało się dodać produktu, prawdopodobnie już istnieje produkt o takim EANie
            </div>
        </c:if>
        <form method="post" action="products">
            <input type="hidden" name="action" value="addproduct">
            <div class="form-group">
                <label for="name">Nazwa produktu:</label>
                <input type="text" class="form-control" name="name" id="name" placeholder="Podaj nazwę produktu">
            </div>
            <div class="form-group">
                <label for="ean">EAN:</label>
                <input type="text" class="form-control" name="ean" id="ean" placeholder="Podaj EAN">
            </div>
            <button type="submit" class="btn btn-primary">Dodaj</button>
        </form>
    </div>
</div>

</body>
</html>
