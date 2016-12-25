<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Models.Grocery" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: raxis
  Date: 20.12.2016
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="req" value="${pageContext.request}" />
<html>
  <head>
    <title>Корзина покупок</title>
  </head>
  <body>
    <%--<div> <a href="">Вход</a> </div>
    <div> <a href="">Регистрация</a> </div>--%>
    <div>
      <nav>
        <ul id="menu">
          <li><a href="index.jsp">Главная</a></li>
          <li><a href="/GroceryList">Каталог товаров</a></li>
        </ul>

      </nav>
    </div>
    <Table>
      <tr>
        <th>Наименование</th>
        <th>Цена</th>
        <th>Количество</th>
        <th></th>
      </tr>
      <c:forEach items="${cart.getMap()}" var="item">
      <tr>
        <td>${item.key.getName()}</td>
        <td>${item.key.getPrice()}</td>
        <td>${item.value}</td>
        <td>
          <form action="/CartRemove" method="post">
            <input type="hidden" name="groceryid" value="${item.key.getId()}">
              <%--<input type="hidden" name="returnurl" value="${req.requestURI}">--%>
            <input type="submit" value="Удалить из корзины">
          </form>
        </td>
       <%-- <td><a href="studentdel.jsp?id=<%=s.getId().toString()%>">Удалить</a></td>
        <td><a href="studentupd.jsp?id=<%=s.getId().toString()%>">Редактировать</a></td>--%>
      </tr>
      </br>
      </c:forEach>
    </Table>
    <br>
    <div>Итого:${totalprice}</div>
    <br>
    <div> <a href="/GroceryList">Продолжить покупки</a> </div>
    <div> <a href="/OrderAdd">Оформить заказ</a> </div>

      <%--<c:forEach items="${groceryList}" var="item">
        ${item}<br>
      </c:forEach>--%>

   <%-- <c:forEach items="${groceryList}" var="item">
      ${item.getPrice()}<br>
    </c:forEach>--%>

  <%--<%for(Grocery g : (List<Grocery>)request.getAttribute("grocerylist")){%>
    <%=g.getName()%>
  <%}%>
  </body>--%>
</html>
