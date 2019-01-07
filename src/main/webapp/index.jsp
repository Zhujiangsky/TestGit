<%--
  Created by IntelliJ IDEA.
  User: zhujiang
  Date: 2019/1/2
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var page = "";
        var requset = new XMLHttpRequest();
        $.ajax({
            "url": "MyServlet",                      // 要提交的URL路径
            "type": "post",                     // 发送请求的方式
            "data": "op=show&pageIndex=1",                      // 要发送到服务器的数据
            "dataType": "json",                   // 指定传输的数据格式
            "success": function (result) {// 请求成功后要执行的代码
                $.each(result.dataList, function () {
                    $("#div").append("<table border=\"1px\">\n" +
                        "        <tr>\n" +
                        "            <td>作者：" + this.author + "</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td>#<br>" + this.message + "</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "             <td>发表：" + this.date + "</td>\n" +
                        "        </tr>\n" +
                        "    </table>");
                    page = "<div id='div1'><a onclick=\"onn(1)\" href=\"javascript:void(0)\">首页</a>&nbsp;" + "<a onclick=\"onn(" + (result.currentPage - 1 == 0 ? "1" : (result.currentPage - 1)) + ")\" href=\"javascript:void(0)\">上一页</a>&nbsp;<a onclick=\"onn(" + (result.currentPage == result.pageCount ? (result.pageCount) : (result.currentPage + 1)) + ")\" href=\"javascript:void(0)\">下一页</a>&nbsp;<a onclick=\"onn(" + result.pageCount + ")\" href=\"javascript:void(0)\"\">尾页</a>&nbsp;<p>共" + result.pageCount + "页，当前为第" + result.currentPage + "页</p></div>";
                });
            $("#div1").append(page);
            },
            "error": function () {           // 请求失败后要执行的代码
            }
        });
    });
    var request = new XMLHttpRequest();

    function onn(e) {
        request.open("post", "MyServlet", true);
        request.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded");
        request.send("op=show&pageIndex=" + e);
    }

    request.onreadystatechange = function (ev) {
        $("#div").find("*").remove();
        $("#div1").remove();
        var s = "";
        var page = "";
        if (request.readyState == 4 && request.status == 200) {
            var ss = request.responseText;
            var json = eval("(" + ss + ")");
            $(json.dataList).each(function () {
                s += "<table border=\"1px\">\n" +
                    "        <tr>\n" +
                    "            <td>作者：" + this.author + "</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>#<br>" + this.message + "</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>发表：" + this.date + "</td>\n" +
                    "        </tr>\n" +
                    " </table>";
            });
            page = "<div id='div1'><a onclick=\"onn(1)\" href=\"javascript:void(0)\">首页</a>&nbsp;" + "<a onclick=\"onn(" + (json.currentPage - 1 == 0 ? "1" : (json.currentPage - 1)) + ")\" href=\"javascript:void(0)\">上一页</a>&nbsp;<a onclick=\"onn(" + (json.currentPage == json.pageCount ? (json.pageCount) : (json.currentPage + 1)) + ")\" href=\"javascript:void(0)\">下一页</a>&nbsp;<a onclick=\"onn(" + json.pageCount + ")\" href=\"javascript:void(0)\"\">尾页</a>&nbsp;<p>共" + json.pageCount + "页，当前为第" + json.currentPage + "页</p></div>";
            $("#div").append(s);
            $("#div").after(page);
        }
    }
</script>
<body>
<h1>YF1801留言板</h1>
<div id="div"></div>
<div id="div1"></div>
<form action="MyServlet?op=fb" method="post">
    用户名：<input type="text" name="name">
    留言信息:<input type="text" name="message">
    <input type="submit" value="提交">
</form>
</body>
</html>
