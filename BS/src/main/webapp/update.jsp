<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新用户信息</title>
</head>
<body>
<h2>更新用户信息</h2>
<form action="UpdateServlet" method="post">
    <label>姓名：</label>
    <input type="text" name="userName" required><br>
    <label>新地址：</label>
    <input type="text" name="userAddress" required><br>
    <label>新电话：</label>
    <input type="text" name="userPhone" required><br>
    <input type="submit" value="更新">
</form>
</body>
</html>
