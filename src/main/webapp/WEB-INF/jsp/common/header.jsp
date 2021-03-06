<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!-- 引入bootstrap样式 -->
<link href="/css/bootstrap.min.css" rel="stylesheet">
<!-- 引入bootstrap-table样式 -->
<link href="/css/bootstrap-table.min.css" rel="stylesheet">
<!-- Theme style -->
<link rel="stylesheet" href="/css/allblue.css">

<!-- jquery -->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>

<!-- bootstrap-table.min.js -->
<script src="/js/bootstrap-table.min.js"></script>
<!-- 引入中文语言包 -->
<script src="/js/bootstrap-table-zh-CN.min.js"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<!-- Google Font -->
<link rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 30px;">
            <a class="navbar-brand" href="/view/index">
                <span><b>All</b>Blue</span>
            </a>
        </div>
        <div>
            <ul class="nav navbar-nav navbar-right" style="margin-right: 30px;">
                <li class="dropdown">
                    <a href="/view/photo/gallery">图片画廊</a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        数据中心
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/view/user/list">用户列表</a></li>
                        <li><a href="/view/role/list">角色列表</a></li>
                        <li><a href="/view/system/list">菜单列表</a></li>
                        <li class="divider"></li>
                        <li><a href="/view/photo/list">角色列表</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="${blueUser.photo}" id="topPhoto" class="img-circle" alt="photo"
                             style="width: 18px;height: 18px">
                        <span>${blueUser.name}</span>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#" data-target="#detail" data-toggle="modal">详情</a></li>
                        <li class="divider"></li>
                        <li><a href="/user/logout"> 退出</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div id="detail" class="modal fade" role="dialog" aria-labelledby="detailModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;text-align: center">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 id="detailModalLabel">
                    用户详情
                </h4>
            </div>
            <div class="modal-body">
                <form id="user_form" class="form-horizontal">
                    <div class="form-group">
                        <img src="${blueUser.photo}" style="width: 128px;height: 128px" class="img-circle"
                             alt="photo">
                    </div>
                    <div class="form-group">
                        <p>${blueUser.name }</p>
                        <p>${blueUser.email }</p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>
