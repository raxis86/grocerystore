<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: raxis
  Date: 26.12.2016
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Заказ сделан!</title>
</head>
<body>

<div class="menu">
    <c:if test="${empty sessionScope.user}" >
        <div> <a href="/Login">Вход</a> </div>
        <div> <a href="/Signin">Регистрация</a> </div>
    </c:if>
    <c:if test="${!empty sessionScope.user}" >
        <div> ${sessionScope.user.getName()}!</div>
        <div> <a href="/Logout">Выход</a> </div>
    </c:if>
</div>

<div>
    <nav>
        <ul id="menu">
            <li><a href="index.jsp">Главная</a></li>
            <c:if test="${empty sessionScope.user}" >
                <li><a href="/GroceryListController">Каталог товаров</a></li>
            </c:if>
            <c:if test="${!empty sessionScope.user}" >
                <c:if test="${!sessionScope.role.getName().equals('admin')}" >
                    <li><a href="/GroceryListController">Каталог товаров</a></li>
                    <li><a href="/CartList">Корзина покупок</a></li>
                    <li><a href="/OrderList">Список заказов</a></li>
                </c:if>
                <c:if test="${sessionScope.role.getName().equals('admin')}" >
                    <li><a href="/OrderListAdmin">Список заказов (админ-режим)</a></li>
                    <li><a href="/GroceryListAdmin">Каталог товаров (админ-режим)</a></li>
                </c:if>
            </c:if>
        </ul>
    </nav>
</div>

    <H2>Заказ оформлен!</H2>
</body>
</html>
