<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="none"/>
    <title>无权限页面</title>
    <style>
        * {
            font-family: "Microsoft Yahei";
            margin: 0;
            font-weight: lighter;
            text-decoration: none;
            text-align: center;
            line-height: 2.2em;
        }

        html, body {
            height: 100%;
        }

        h1 {
            font-size: 100px;
            line-height: 1em;
        }

        table {
            width: 100%;
            height: 100%;
            border: 0;
        }
    </style>
</head>
<body>
<table cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <table cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <h1>权限不足</h1>
                        <h3>大事不妙啦！</h3>
                        <p>你没有访问页面的钥匙~<br/>
                            <a href="/view/index">回到过去 ></a>
                        </p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>