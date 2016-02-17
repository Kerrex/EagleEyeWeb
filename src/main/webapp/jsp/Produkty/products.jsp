<%--
  Created by IntelliJ IDEA.
  User: tomek
  Date: 12.02.16
  Time: 13:55
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
            <c:when test="${not empty productList}">
                <form id="productForm" method="get" action="products">
                    <input type="hidden" name="ean" id="ean">
                    <input type="hidden" name="action" id="action">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <td><h3>EAN</h3></td>
                            <td><h3>Nazwa produktu</h3></td>
                        </tr>
                        </thead>
                        <c:forEach var="product" items="${productList}">
                            <tr>
                                <td><a href="#"><span onclick="edit('${product.EAN}')">${product.EAN}</span></a></td>
                                <td>${product.name}</td>
                                <td><a href="#"><span onclick="erase('${product.EAN}')"
                                                      class="glyphicon glyphicon-trash"></span></a></td>
                            </tr>
                        </c:forEach>
                    </table>


                </form>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    Nie znaleziono produktów. Może to oznaczać problem z połączeniem z bazą danych<br>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
<script>
    function erase(ean) {
        document.getElementById("ean").value = ean;
        document.getElementById("action").value = "erase";
        document.getElementById("productForm").submit();
    }
    function edit(ean) {
        document.getElementById("ean").value = ean;
        document.getElementById("action").value = "edit";
        document.getElementById("productForm").submit();
    }
</script>
</html>
