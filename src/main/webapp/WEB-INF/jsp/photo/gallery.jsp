<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Blue</title>
</head>

<body onload="loadRoleList()" style="background: #d2d6de;">
<%@ include file="../common/header.jsp" %>
<div>
    <article class="htmleaf-container">
        <section id="gallery-wrapper" class="wrapper"></section>
    </article>
</div>
<script src="/js/photo/pinterest_grid.js"></script>
<script type="text/javascript">
    $(function () {
        var userId = "${blueUser.id}";
        if (userId !== null && userId !== "") {
            //瀑布流插件
            $("#gallery-wrapper").pinterest_grid({
                no_columns: 4,
                padding_x: 10,
                padding_y: 10,
                margin_bottom: 50,
                single_column_breakpoint: 700
            });
        }
    });

    function loadRoleList() {
        $.ajax({
            url: "/photo/list",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {},    //参数值
            type: "POST",   //请求方式
            success: function (datas) {
                var data = datas.data;
                var str = "";
                //先将元素对应清空
                $('#gallery-wrapper').empty();

                if (data.length === 0) {
                    str += '<tr><td colspan="8">目前还没有数据</td></tr>';
                } else {
                    for (var i = 0; i < data.length; i++) {
                        var photo = data[i];
                        str += '<article class="white-panel"><a href="/role/' + photo.id + '/detail">' +
                            '<img class="thumbnail gallerybox" src="' + photo.shootingPhoto + '"></a></article>';
                    }
                }
                //将html动态拼接到对应的div上面
                $('#gallery-wrapper').append(str);
            },
            error: function (data) {
                if (data.status === 403) {
                    alert("没有权限！");
                }
            }
        });
    }
</script>
</body>
</html>
