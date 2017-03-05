<%--
  Created by IntelliJ IDEA.
  User: zhangdongli
  Date: 16-11-28
  Time: 上午8:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="msapplication-tap-highlight" content="no"/>

    <title>计算机图形复习题</title>

    <script src="assets/js/tools/jquery-1.11.2.min.js" type="text/javascript"></script>
    <script type="text/javascript">

        //ajax 请求
        var ajaxFunc = function (keywordV) {
            $.get("/action/question.html", {keyword: keywordV}, function (ret) {
                        ret = $.parseJSON(ret)

                        var questionsUl = $("#questions");
                        questionsUl.empty();

                        if (ret.code == 200 && ret.data != null && ret.data.length > 0) {
                            var questionLi = null;
                            $.each(ret.data, function (i, data) {
                                questionLi = $("<li><span></span><b></b></li>");
                                $('span', questionLi).text(data.title);
                                $('b', questionLi).text(data.anwers != undefined && $.isArray(data.anwers) ? "答案：" + data.anwers.join('|') : '');
                                questionsUl.append(questionLi);
                            });
                        }
                    }
            );
        }

        //键盘事件绑定
        $(function () {
            $("#keyword").bind('change select keydown keypress keyup error', function (e) {
                setTimeout(function () {
                    ajaxFunc($("#keyword").val());
                }, 300);
            });
        });

    </script>

    <style type="text/css">
        #questions {
            list-style: none;
            margin: 0px;
            padding: 2px 5px;
        }

        #questions li {
            text-align: left;
            padding: 5px 0px;
        }

        #questions li span {
        }

        #questions li b {
            color: #ff0000;
        }
    </style>

</head>
<body>
<div>
    <div align="center">
        <input id="keyword" type="text" placeholder="汉字/拼音/拼音首字母/发布测试1" style="width: 100%;">
    </div>
    <hr>
    <div align="center">
        <ul id="questions">
        </ul>
    </div>
</div>
</body>
</html>
