<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>所有用户</title>
</head>
<body>
<h2>所有用户</h2>
<table border="1">
    <tr>
        <th>姓名</th>
        <th>地址</th>
        <th>电话</th>
    </tr>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.name}</td>
            <td>${user.address}</td>
            <td>${user.phone}</td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="Register.jsp">增加用户</a>
<br>
<a href="Delete.jsp">删除用户</a>
<br>
<a href="Update.jsp">更新用户</a>
<br>
<a href="Search.jsp">查询用户</a>
</body>
</html>
