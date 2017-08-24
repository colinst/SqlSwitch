<%--
  Created by IntelliJ IDEA.
  User: colin
  Date: 2017/8/20
  Time: 0:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>get</title>
</head>
<body>
查看table
    <form action="getdba.do" method="post">
        <input placeholder="dba_name" name="dba_name"><br>
        <input type="submit">
    </form>
<br>
打印table
    <form action="print.do" method="post">

        <input type="submit" value="打印表格">
    </form>
对比table1,table2
    <form action="compareTable.do" method="post" >
        <input placeholder="table_name1" name="table_name1"><br>
        <input placeholder="table_name2" name="table_name2"><br>
        <input type="submit" value="对比结果">

    </form>

</body>
</html>
