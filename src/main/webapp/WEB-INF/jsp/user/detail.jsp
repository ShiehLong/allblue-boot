<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户[${blueUser.name}]详细信息</title>
    <style type="text/css">
        td {
            text-align: center;
        }
    </style>
</head>

<body>
<%@ include file="../common/header.jsp" %>
<div class="table-responsive" style="margin: 60px auto;width: 40%;">
    <table class="table table-striped">
        <tr>
            <td>头像:</td>
            <td><img id="image" src="${blueUser.photo}" style="width: 128px;height: 128px" class="img-circle"
                     alt="photo"></td>

        </tr>
        <tr>
            <td>用户标识:</td>
            <td>${blueUser.id }</td>
        </tr>
        <tr>
            <td>用户名:</td>
            <td>${blueUser.name }</td>
        </tr>
        <tr>
            <td>用户邮箱:</td>
            <td>${blueUser.email }</td>
        </tr>
    </table>
    <div style="text-align: center;">
        <a href="" class="btn btn-primary" role="button">更新</a>
    </div>
</div>
</body>
</html>