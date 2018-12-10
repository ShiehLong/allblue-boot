<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>404错误页面</title>
    <meta name="Keywords" content="404错误页面"/>
    <meta name="Description" content="404错误页面"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link href="/css/error404.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">setTimeout(function () {
        top.location = '/view/index';
    }, 5000)</script>

</head>
<body>
<div class="error404">
    <div class="info">
        <h1>404</h1>
        <h2>抱歉，您访问的页面不存在或已被删除！ (｡•ˇ‸ˇ•｡)</h2>
        <p class="p1">5秒钟后将带您返回首页</p>

        <a href="/view/index" class="btn">返回首页</a>
        <a href="#" class="btn btn-brown">返回上一步</a>
    </div>
    <div class="pic">
        <img src="/img/404.gif" alt=" "/>
    </div>
</div>
</body>
</html>