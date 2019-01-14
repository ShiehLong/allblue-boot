<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>详细信息</title>
    <style>
        body {
            margin: 0;
            padding: 0;
        }

        div.container {
            position: absolute;
            top: 60px;
            bottom: 0;
        }

        div#image, #photoDetail {
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
            top: 0;
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
<%@ include file="../common/header.jsp" %>
<div class="container">
    <div id="photoTab" class="row" style="display: block">
        <div id="image" class="col-xs-8 col-sm-6">
            <img src="${photoInfo.shootingPhoto}" class="autoSize">
        </div>
        <div id="photoDetail" class="col-xs-4 col-sm-6">
            <div class="table">
                <div class="row">
                    <div class="cell">主题:</div>
                    <div class="cell">${photoInfo.shootingTitle }</div>
                </div>
                <div class="row">
                    <div class="cell">地点:</div>
                    <div class="cell">${photoInfo.shootingLocation }</div>
                </div>
                <div class="row">
                    <div class="cell">时间:</div>
                    <div class="cell">${photoInfo.shootingTime }</div>
                </div>
                <div class="row">
                    <div class="cell">描述:</div>
                    <div class="cell">${photoInfo.description }</div>
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
        <video id="photoVideo" width="800" height="590" controls>
            <source src="/photos/photo/1.mp4" type="video/mp4">
        </video>
        <div class="close_video">
            <button id="closeVideo" type="button" class="close">×</button>
        </div>
    </div>
</div>
<script>
    $("#playVideo").click(function () {
        document.getElementById('photoTab').style.display = 'none';
        document.getElementById('videoTab').style.display = 'block';
    });
    $("#closeVideo").click(function () {
        document.getElementById('photoVideo').pause();
        document.getElementById('photoTab').style.display = 'block';
        document.getElementById('videoTab').style.display = 'none';
    });
</script>
</body>
</html>