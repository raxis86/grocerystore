<%--
  Created by IntelliJ IDEA.
  User: raxis
  Date: 27.12.2016
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление продукта</title>
</head>
<body>
    <form action="/GroceryAdd" method="post">
        Наименование:<input type="text" name="name"><br>
        Цена:<input type="text" name="price"><br>
        Количество:<input type="text" name="quantity"><br>
        <input type="submit" value="Войти">
    </form>
</body>
</html>
