<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>角色[${roleInfo.name}]详细信息</title>
    <style>
        body {
            margin: 0px;
            padding: 0px;
        }

        div.container {
            position: absolute;
            top: 60px;
            bottom: 0px;
        }

        div#image, #roleDetail {
            width: 550px;
            height: 550px;
            max-width: 50%;
            max-height: 100%;
            text-align: center;
        }

        img {
            width: auto;
            height: auto;
            max-width: 100%;
            max-height: 100%;
        }

        .close_video {
            position: absolute;
            top: 0px;
            right: 200px;
        }

        .table {
            display: table;
            margin: 200px auto;
            /*display: table时padding会失效*/
        }

        .row {
            display: table-row;
            /*display: table-row时margin、padding同时失效*/
        }

        .cell {
            display: table-cell;
            padding: 5px;
            /*display: table-cell时margin会失效*/
        }
    </style>
</head>

<body>
<%@ include file="/jsp/common/header.jsp" %>
<div class="container">
    <div id="roleTab" class="row" style="display: block">
        <div id="image" class="col-xs-8 col-sm-6">
            <img src="${roleInfo.pic}" class="autoSize">
        </div>
        <div id="roleDetail" class="col-xs-4 col-sm-6">
            <div class="table">
                <div class="row">
                    <div class="cell">姓名:</div>
                    <div class="cell">${roleInfo.name }</div>
                </div>
                <div class="row">
                    <div class="cell">性别:</div>
                    <div class="cell">${roleInfo.sex }</div>
                </div>
                <div class="row">
                    <div class="cell">年龄:</div>
                    <div class="cell">${roleInfo.age }</div>
                </div>
                <div class="row">
                    <div class="cell">描述:</div>
                    <div class="cell">${roleInfo.description }</div>
                </div>
                <div class="row">
                    <div class="cell">视频:</div>
                    <div class="cell">
                        <button id="playVideo" class="btn btn-primary">播放视频</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="videoTab" style="text-align:center;display: none">
        <video id="roleVideo" width="800" height="590" controls>
            <source src="/photos/role/1.mp4" type="video/mp4">
        </video>
        <div class="close_video">
            <img id="closeVideo" width='16' height='16' src="/img/close.gif" style="vertical-align:middle"/>
        </div>
    </div>
</div>
<script>
    $("#playVideo").click(function () {
        document.getElementById('roleTab').style.display = 'none';
        document.getElementById('videoTab').style.display = 'block';
    });
    $("#closeVideo").click(function () {
        document.getElementById('roleVideo').pause();
        document.getElementById('roleTab').style.display = 'block';
        document.getElementById('videoTab').style.display = 'none';
    });
</script>
</body>
</html>