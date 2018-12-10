<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Blue | Registration Page</title>
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
<body class="register-page" style="padding-top: 0px;">
<div class="register-box">
    <div class="register-logo">
        <a href=""><b>All Blue</b></a>
    </div>

    <div class="register-box-body">
        <p class="login-box-msg">注&nbsp;&nbsp;册&nbsp;&nbsp;会&nbsp;&nbsp;员</p>

        <div class="form-group has-feedback">
            <input type="text" class="form-control" id="name" placeholder="用户名">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="email" class="form-control" id="email" placeholder="邮箱">
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="password" class="form-control" id="password" placeholder="密码">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="password" class="form-control" id="retryPassword" placeholder="确认密码">
            <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
        </div>
        <div class="row">
            <div class="col-xs-8">
                <div class="checkbox">
                    <label>
                        <input type="checkbox">&nbsp;同意 <a href="#">条款</a>
                    </label>
                </div>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">
                <button id="register" type="submit" class="btn btn-primary btn-block btn-flat">注&nbsp;&nbsp;册</button>
            </div>
            <!-- /.col -->
        </div>

        <a href="/view/login" class="text-center">已有账户</a>
    </div>
    <!-- /.form-box -->
</div>
<!-- /.register-box -->

<!-- jQuery 3 -->
<script src="/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/js/bootstrap.min.js"></script>
<!--sha1加密-->
<script type="text/ecmascript" src="/js/sha1.js"></script>
<script>
    $(document).ready(function () {
        $('button').click(function () {
            var name = $("#name").val();
            var email = $("#email").val();
            var password = $("#password").val();
            var retryPassword = $("#retryPassword").val();

            if (name === "") {
                alert("用户名不能为空！");
                return false;
            }
            if (email === "") {
                alert("邮箱不能为空！");
                return false;
            }
            if (password === "") {
                alert("密码不能为空！");
                return false;
            }
            if (retryPassword === "") {
                alert("确认密码不能为空！");
                return false;
            }
            if (retryPassword !== password) {
                alert("两次密码不一致！");
                return false;
            }
            var url = "/blueUser/register";
            $.post(url,
                {
                    name: name,
                    email: email,
                    password: hex_sha1(password)
                },
                function (data) {
                    if (data["result"] === "success") {
                        alert(data["msg"]);
                        window.location.href = "/view/login";
                    } else if (data["result"] === "fail") {
                        alert(data["msg"]);
                    }
                });
        });
    });
</script>
</body>
</html>

