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
    <c:if test="${empty sessionScope.user}" >
        <div> <a href="/Login">Вход</a> </div>
        <div> <a href="">Регистрация</a> </div>
    </c:if>
    <c:if test="${!empty sessionScope.user}" >
        <div> <a href="/Logout">Выход</a> </div>
    </c:if>
    <div>
        <nav>
            <ul id="menu">
                <li><a href="">Главная</a></li>
                <li><a href="/GroceryListController">Каталог товаров</a></li>
            </ul>

        </nav>
    </div>

    <H2>Заказ оформлен!</H2>
</body>
</html>
