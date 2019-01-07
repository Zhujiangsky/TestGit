<%--
  Created by IntelliJ IDEA.
  User: zhujiang
  Date: 2019/1/3
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.0.0/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var json = [{"name": "周老板1号", "age": "21", "phone": "123123123"}, {
                "name": "周老板2号",
                "age": "21",
                "phone": "123123123"
            }, {"name": "周老板3号", "age": "21", "phone": "123123123"}, {"name": "周老板4号", "age": "21", "phone": "123123123"}];
            var $table = $("<table border='1'></table>").append(
                "<tr><td>ID</td><td>用户名</td><td>密码</td></tr>");
            $("#div1").append($table);
        });
    </script>
</head>

<body>
<div id="div1"></div>
</body>
</html>
