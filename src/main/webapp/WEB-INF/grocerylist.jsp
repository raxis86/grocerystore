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
    <title>Каталог товаров</title>
  </head>
  <body>
    <%--<div> <a href="">Вход</a> </div>
    <div> <a href="">Регистрация</a> </div>--%>
    Привет, анон!
    <div>
      <nav>
        <ul id="menu">
          <li><a href="">Главная</a></li>
          <li><a href="">Каталог товаров</a></li>
        </ul>

      </nav>
    </div>
    <Table>
      <tr>
        <th>Наименование</th>
        <th>Цена</th>
      </tr>
      <c:forEach items="${groceryList}" var="item">
      <tr>
        <td>${item.getName()}</td>
        <td>${item.getPrice()}</td>
        <c:if test="${!empty sessionScope.user}" >
          <td>
            <form action="/CartAdd" method="post">
              <input type="hidden" name="groceryid" value="${item.getId()}">
              <%--<input type="hidden" name="returnurl" value="${req.requestURI}">--%>
              <input type="submit" value="Добавить в корзину">
            </form>
          </td>
        </c:if>
       <%-- <td><a href="studentdel.jsp?id=<%=s.getId().toString()%>">Удалить</a></td>
        <td><a href="studentupd.jsp?id=<%=s.getId().toString()%>">Редактировать</a></td>--%>
      </tr>
      </br>
      </c:forEach>
    </Table>

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
