<%--
  Created by IntelliJ IDEA.
  User: colin
  Date: 2017/8/21
  Time: 5:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>compare</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript">


        function getdba() {

            //ajax获得数据库Map后渲染至页面
            $.ajax({
                url: 'getdba.do',
                type: 'post',
                data: {dba_name:"pass"},
                dataType: 'json',
                success: function (data) {

                    $("#context1").children().remove();//删除原

                    $.each(data,function(ci,cs){

                        //ci为表名，cs为column的对象list集合
                        $("#context1").append("<table id='"+ci+"'></table>");

                        var body=document.getElementById(ci);
                        body.innerHTML+="<tr><th colspan='6' >"+ci+"</th></tr><tr bgcolor='#f5f5f5' ><th>Field</th><th>Type</th><th>Null</th><th>Key</th><th>Default</th><th>Extra</th></tr>";

                        $.each(cs,function(i,c){

                            body.innerHTML+="<tr><td>"+c['field']+"</td><td>"+c['type']+"</td><td>"+c['null']+"</td><td>"+c['key']+"</td><td>"+c['default']+"</td><td>"+c['extra']+"</td></tr>";

                        });


                    });
                }
            });
        }

    </script>
    <style type="text/css">
table {
    overflow:hidden;
    border:1px solid #d3d3d3;
    background:#fefefe;
    width:70%;
    margin:5% auto 0;
    -moz-border-radius:5px; /* FF1+ */
    -webkit-border-radius:5px; /* Saf3-4 */
    border-radius:5px;
    -moz-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
    -webkit-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
}

th, td {padding:18px 28px 18px; text-align:center; }

th {padding-top:22px; text-shadow: 1px 1px 1px #fff; background:#e8eaeb;}

td {border-top:1px solid #e0e0e0; border-right:1px solid #e0e0e0;}

tr.odd-row td {background:#f6f6f6;}

td.first, th.first {text-align:left}

td.last {border-right:none;}

/*
Background gradients are completely unnecessary but a neat effect.
*/

td {
    background: -moz-linear-gradient(100% 25% 90deg, #fefefe, #f9f9f9);
    background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f9f9f9), to(#fefefe));
}

tr.odd-row td {
    background: -moz-linear-gradient(100% 25% 90deg, #f6f6f6, #f1f1f1);
    background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f1f1f1), to(#f6f6f6));
}

th {
    background: -moz-linear-gradient(100% 20% 90deg, #e8eaeb, #ededed);
    background: -webkit-gradient(linear, 0% 0%, 0% 20%, from(#ededed), to(#e8eaeb));
}

/*
I know this is annoying, but we need additional styling so webkit will recognize rounded corners on background elements.
Nice write up of this issue: http://www.onenaught.com/posts/266/css-inner-elements-breaking-border-radius

And, since we've applied the background colors to td/th element because of IE, Gecko browsers also need it.
*/

tr:first-child th.first {
    -moz-border-radius-topleft:5px;
    -webkit-border-top-left-radius:5px; /* Saf3-4 */
}

tr:first-child th.last {
    -moz-border-radius-topright:5px;
    -webkit-border-top-right-radius:5px; /* Saf3-4 */
}

tr:last-child td.first {
    -moz-border-radius-bottomleft:5px;
    -webkit-border-bottom-left-radius:5px; /* Saf3-4 */
}

tr:last-child td.last {
    -moz-border-radius-bottomright:5px;
    -webkit-border-bottom-right-radius:5px; /* Saf3-4 */
}

    </style>

</head>
<body>

<!-- Header -->
<div id="header">
    <span class="logo icon fa-paper-plane-o"></span>
    <h1>招聘题测项目.</h1>
    <p>该项目已完成传至本人站点 <a href="http://www.colinst.xin.com">COLINST.XIN</a>
        <br />
        使用云数据库（MySQL） <a href="http://rm-uf6y70f8z4zrabhvro.mysql.rds.aliyuncs.com">mysql.rds.aliyuncs.com</a>.</p>
</div>

<!-- Main -->
<div id="main">

    <footer class="major container 75%">
        <h3>操作说明</h3>
        <p>输入两个数据库的名称不选择子表时默认对比两数据库中同名表<br>
            当选择子表进行一键对比进行单表对比</p>
        <ul class="actions">
            <!--<li><a href="#" class="button">Join our crew</a></li>-->
        </ul>
    </footer>

    <div class="container 75%">

        <form method="post" action="#">
        		<!--数据库连接-->
        		<div class="row">
                <div class="6u 12u(mobilep)">
                    <br>
                    <input type="text" id="dba_url1" placeholder="dba_url1" />
                    <input type="text" id="dba_user1" placeholder="dba_user1" />
                    <input type="text" id="dba_password1" placeholder="dba_password1" />
                    <input type="text" id="dba_name1" placeholder="dba_name1" />
                </div>
                <div class="6u 12u(mobilep)">
                    <br>
                    <input type="text" id="dba_url2" placeholder="dba_url2" />
                    <input type="text" id="dba_user2" placeholder="dba_user2" />
                    <input type="text" id="dba_password2" placeholder="dba_password2" />
                    <input type="text" id="dba_name2" placeholder="dba_name2" />
                </div>
            </div>
            <div class="row">
                <div class="12u">
                    <ul class="actions">
                        <li><input type="button" value="测试链接" onclick="" /></li>
                    </ul>
                </div>
            </div>

            <!--选择所属表-->
            <div class="row">
                <div class="6u 12u(mobilep)">

                    <select id="dba1_holder">
                        <option value="">选择数据表</option>
                        <option value="audi">Audi</option>
                    </select>
                </div>
                <div class="6u 12u(mobilep)">

                    <select id="dba2_holder">
                        <option value="">选择数据表</option>
                        <option value="audi">Audi</option>
                    </select>
                </div>
            </div>

            <div class="row" style="display:none;" >
                <div class="12u">
                    <textarea name="message" placeholder="Message" rows="6"></textarea>
                </div>
            </div>

            <div class="row">
                <div class="12u">
                    <ul class="actions">
                        <li><input type="button" value="一键对比" onclick="getdba()" /></li>
                    </ul>
                </div>
            </div>
        </form>

    </div>


</div>

<!-- Footer -->
<div id="footer">
    <div class="container ">

        <header class="major last">
            <h2>对比结果</h2>
        </header>

        <form method="post" action="">

            <!--对比结果展示-->
            <div class="row ">

                <div class="4u 8u(mobilep)">
                    <input type="email" value="基本库" style="height:25px; font-size:28px; font-family:黑体; text-align: center" disabled="disabled"/>
                </div>
                <div class="4u 8u(mobilep)">
                    <input type="email" value="两表差异" style="height:25px; font-size:28px; font-family:黑体; text-align: center"disabled="disabled"/>
                </div>
                <div class="4u 8u(mobilep)">
                    <input type="email" value="目标库" style="height:25px; font-size:28px; font-family:黑体;text-align: center"disabled="disabled" />
                </div>

                <div id="context1">
                <table  class="imagetable" align="center">
                        <tbody  id="">
                        <tr><th>tablename</th></tr>
                        <tr>
                            <th>Field</th>
                            <th>Type</th>
                            <th>Null</th>
                            <th>Key</th>
                            <th>Default</th>
                            <th>Extra</th>
                        </tr>

                        <tr>
                            <td class="action">_status</td>
                            <td class="2u">int(12)</td>
                            <td class="u">Yes</td>
                            <td class="2u"></td>
                            <td class="u">Null</td>
                            <td class="3u"></td>
                        </tr>
                        </tbody>
                </table>
                </div>

                <table id="context2"></table>
                <table id="context3"></table>


            </div>

        </form>


    </div>
</div>

<!-- Scripts -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/main.js"></script>

</body>
</html>