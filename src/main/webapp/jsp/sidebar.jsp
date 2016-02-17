<link rel="stylesheet" href="css/bootstrap.min.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<link href="css/simple-sidebar.css" rel="stylesheet">
<script src="js/bootstrap.min.js"></script>
<div id="sidebar-wrapper" style="max-width: 15%">
    <ul class="sidebar-nav">
        <li class="sidebar-brand">
            <a href="#">
                EagleEye
            </a>
        </li>
        <li>
            <a href="dashboard">Panel główny</a>
        </li>
        <li>
            <a href="eagleeye">Sprzedaż</a>
        </li>
        <li>
            <a href="#" data-toggle="collapse" data-target="#customermenu">Klienci</a>
            <ul id="customermenu" class="collapse">
                <li>
                    <a href="customers">Znajdź</a>
                </li>
                <li>
                    <a href="customers?action=addcustomer">Dodaj klienta</a>
                </li>
            </ul>
        </li>
        <li>
            <a href="#" data-toggle="collapse" data-target="#productmenu">Produkty</a>
            <ul id="productmenu" class="collapse">
                <li>
                    <a href="products">Pokaż produkty</a>
                </li>
                <li>
                    <a href="products?action=addproduct">Dodaj produkt</a>
                </li>
            </ul>
        </li>
        <li>
            <form method="post" action="logout" id="logout-form">
                <a href="#" onclick="submitLogout()">Wyloguj się</a>
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form>
        </li>
    </ul>
</div>
<script>
    function submitLogout() {
        document.getElementById("logout-form").submit();
    }
</script>