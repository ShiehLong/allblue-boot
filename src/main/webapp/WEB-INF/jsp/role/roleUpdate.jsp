<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改角色信息</title>
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
    <meta http-equiv="Content-Type" content="multipart/form-data;charset=utf-8"/>
</head>
<body class="hold-transition register-page">
<div class="register-box">
    <div class="box box-primary">
        <div class="register-logo">
            <h3 class="box-title">修&nbsp;改&nbsp;${roleInfo.name}&nbsp;信&nbsp;息</h3>
        </div>
        <div class="box-body">
            <form action="/role/${roleInfo.id}/update" method="post" enctype="multipart/form-data" role="form">
                <div class="form-group" style="text-align: center;">
                    <label>
                        <img id="image" src="/img/default.jpg" class="img-circle" style="width: 128px;height: 128px;">
                        <input type="file" name="pic" id="pic" style="width: 128px;display: none">
                    </label>
                </div>
                <div class="form-group">
                    <label for="name">名称</label>
                    <input type="text" class="form-control" name="name" id="name" value="${roleInfo.name}">
                </div>
                <div class="form-group">
                    <label for="sex">性别</label>
                    <input type="text" class="form-control" name="sex" id="sex" value="${roleInfo.sex}">
                </div>
                <div class="form-group">
                    <label for="age">年龄</label>
                    <input type="text" class="form-control" name="age" id="age" value="${roleInfo.age}">
                </div>
                <div class="form-group">
                    <label for="description">描述</label>
                    <input type="text" class="form-control" name="description" id="description" value="${roleInfo.description}">
                </div>
                <div class="form-group">
                    <label for="video">视频</label>
                    <input type="text" class="form-control" name="video" id="video" value="${roleInfo.video}">
                </div>
                <div class="box-footer">
                    <button type="submit" class="btn btn-primary btn-block">Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- jQuery 3 -->
<script src="/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        // 初始化内容
        var url = "${roleInfo.pic}";
        if (url !== "") {
            var img = document.getElementById("image");
            img.src = url;
        }

        //实现预览功能
        $("#pic").change(function preview() {
            //获取文件框的第一个文件,因为文件有可能上传多个文件,咱这里是一个文件
            var file = document.getElementById("pic").files[0];
            //可以进行一下文件类型的判断
            var fileType = file.type.split("/")[0];
            if (fileType != "image") {
                alert("请上传图片")
                return;
            }
            //图片大小的限制
            var fileSize = Math.round(file.size / 1024 / 1024);
            if (fileSize >= 3) {
                alert("请上传小于少于3M的图片");
                return;
            }
            //获取img对象
            var img = document.getElementById("image");
            //建一条文件流来读取图片
            var reader = new FileReader();
            //根据url将文件添加的流中
            reader.readAsDataURL(file);
            //实现onload接口
            reader.onload = function (e) {
                //获取文件在流中url
                url = reader.result;
                //将url赋值给img的src属性
                img.src = url;
            };
        });

        $('button').click(function () {
            var pic = document.getElementById("pic").files[0];
            var name = $("#name").val();
            var sex = $("#sex").val();
            var age = $("#age").val();
            var description = $("#description").val();
            var video = $("#video").val();

            if (pic === "" && name === "" && sex === "" && age === "" && description ==="" && video ==="") {
                alert("请填写需要变更信息！");
                return false;
            }
        });
    });
</script>
</body>
</html>

