<%--
  Created by IntelliJ IDEA.
  User: colin
  Date: 2017/8/16
  Time: 1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user</title>
</head>
<body>
<form action="adduser.do" method="post">

    ID:<input placeholder="user_id" name="user_id" ><br>
    名称：<input placeholder="user_name" name="user_name" ><br>
    密码：<input placeholder="password" name="password" type="password" ><br>

    <input type="submit">

</form>

</body>
</html>
