<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Blue | Log in</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/css/allblue.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="login-page" style="padding-top: 0px;">
<div class="login-box">
    <div class="login-logo">
        <a href=""><b>All Blue</b></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">扬&nbsp;&nbsp;帆&nbsp;&nbsp;起&nbsp;&nbsp;航</p>
        <div class="form-group has-feedback">
            <input type="text" class="form-control" name='username' id="username" placeholder="用户名">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="password" class="form-control" name='password' id="password" placeholder="密码">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="row">
            <div class="col-xs-8">
                <div class="checkbox">
                    <label>
                        <input name="_spring_security_remember_me" type="checkbox"> &nbsp;记住用户名
                    </label>
                </div>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">
                <button type="submit" id="login" class="btn btn-primary btn-block btn-flat">登录</button>
            </div>
            <!-- /.col -->
        </div>

        <a href="#">忘记密码</a><br>
        <a href="/view/register" class="text-center">注册会员</a>

    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 3 -->
<script src="/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/js/bootstrap.min.js"></script>
<!--sha1加密-->
<script type="text/ecmascript" src="/js/sha1.js"></script>
<script>
    $(function () {
        $('button').click(function () {
            var name = $("#username").val();
            var password = $("#password").val();

            if (name === "") {
                alert("用户名不能为空！");
                return false;
            }
            if (password === "") {
                alert("密码不能为空！");
                return false;
            }
            var url = "/blueUser/login";
            $.post(url,
                {
                    name: name,
                    password: hex_sha1(password)
                },
                function (data) {
                    if (data["result"] === "success") {
                        console.log(data["msg"]);
                        window.location.href = "/view/index";
                    } else {
                        alert(data["msg"]);
                    }
                });

        });
    });
</script>
</body>
</html>

