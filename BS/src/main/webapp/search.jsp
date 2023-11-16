<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>搜索用户</title>
</head>
<body>
<h2>搜索用户</h2>
<form action="SearchServlet" method="get">
  <label>姓名：</label>
  <input type="text" name="searchName" required>
  <input type="submit" value="搜索">
</form>
</body>
</html>
