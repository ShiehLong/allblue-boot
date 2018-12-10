<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head> 
    <meta charset="UTF-8">
    <title>视频</title> 
</head>
<body>


<div style="text-align:center">
    <button onclick="playPause()">播放/暂停</button>
    <button onclick="makeBig()">放大</button>
    <button onclick="makeSmall()">缩小</button>
    <button onclick="makeNormal()">普通</button>
    <br>
    <video id="video1" width="800" height="600" controls>
        <source src="/photos/role/1.mp4" type="video/mp4">
    </video>
</div>

<script>
    var myVideo=document.getElementById("video1");

    function playPause()
    {
        if (myVideo.paused)
            myVideo.play();
        else
            myVideo.pause();
    }

    function makeBig()
    {
        myVideo.width=560;
    }

    function makeSmall()
    {
        myVideo.width=320;
    }

    function makeNormal()
    {
        myVideo.width=420;
    }
</script>
</body>
</html>
