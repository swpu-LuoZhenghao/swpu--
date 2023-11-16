<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除用户</title>
</head>
<body>
<h2>删除用户</h2>
<form action="DeleteServlet" method="post">
    <label>姓名：</label>
    <input type="text" name="userName" required>
    <input type="submit" value="删除">
</form>
</body>
</html>
