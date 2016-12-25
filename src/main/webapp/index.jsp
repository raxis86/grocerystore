<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: raxis
  Date: 20.12.2016
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Grocery Store</title>
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
          <li><a href="/GroceryList">Каталог товаров</a></li>
        </ul>

      </nav>
    </div>
  </body>
</html>
