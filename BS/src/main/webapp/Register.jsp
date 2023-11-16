<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
    <script>
        function showRegistrationSuccess() {
            alert("注册成功！");
        }
    </script>
</head>
<body>
<h2>用户注册</h2>
<form action="RegisterServlet" method="post" onsubmit="showRegistrationSuccess()">
    <label>姓名：</label>
    <input type="text" name="userName" required><br>
    <label>地址：</label>
    <input type="text" name="userAddress" required><br>
    <label>电话：</label>
    <input type="text" name="userPhone" required><br>
    <input type="submit" value="注册">
</form>
</body>
</html>
