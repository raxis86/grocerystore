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
    <title>Оформление заказа</title>
  </head>
  <body>
    <%--<div> <a href="">Вход</a> </div>
    <div> <a href="">Регистрация</a> </div>--%>
    <div>
      <nav>
        <ul id="menu">
          <li><a href="/">Главная</a></li>
          <li><a href="GroceryListController">Каталог товаров</a></li>
        </ul>

      </nav>
    </div>
<%--    <c:if test="${!empty sessionScope.user}" >
      <div> <a href="/CartList">Корзина покупок</a> </div>
    </c:if>--%>
    <div>Вы заказываете следующие продукты:</div>
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
        </tr>
        </br>
      </c:forEach>
    </Table>
    <br>
    <div>Итого:${totalprice}</div>
    <br>
    <div>Ваши данные:</div>
    <form action="/OrderAdd" method="post">
      <input type="hidden" name="userid" value="${user.getId()}">
      Имя: <input type="text" name="name" value="${user.getName()}"> <br>
      Фамилия: <input type="text" name="lastname" value="${user.getLastName()}"> <br>
      Отчество: <input type="text" name="surname" value="${user.getSurName()}"> <br>
      Телефон: <input type="text" name="phone" value="${user.getPhone()}"> <br>
      Адрес доставки: <input type="textarea" name="address" value="${user.getAddress()}"> <br>
      <%--<input type="hidden" name="returnurl" value="${req.requestURI}">--%>
      <input type="submit" value="Оформить">
    </form>


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
