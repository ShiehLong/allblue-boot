<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Blue</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            text-align: center;
        }

        .container {
            top: 25%;
            max-width: 1200px;
            width: 92%;
            margin: 0 auto;
            position: relative;
            zoom: 1;
            z-index: 2;
        }

        .patern {
            width: 50%;
            height: 2px;
            background: #fff;
            margin: 0 auto;
            display: block;
        }

        .home-img {
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            position: absolute;
            background: url(/img/home.jpg) no-repeat fixed 50% 0;
            background-size: 100% 100%;
        }
    </style>
</head>

<body>
<%@ include file="common/header.jsp" %>
<div class="container">
    <h3 style="color: white; font-size: 20px">建站资源共享学习平台</h3>
    <h1 style="color: white; font-size: 60px">欢迎访问 <span style="color: #f54f36">AllBlue</span></h1>
    <div class="patern">
        <span></span>
    </div>
</div>
<div class="home-img"></div>
</body>
</html>
