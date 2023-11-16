<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册成功页面</title>
</head>
<body>
<h2>注册成功</h2>
<p>欢迎，<%= request.getAttribute("username") %>！</p>
<a href="HelloServlet">返回HelloServlet</a>
</body>
</html>
