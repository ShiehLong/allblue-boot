<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>照片列表</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="row" style="margin-bottom: 10px;">
                <div class="col-md-12">
                    <div class="collapse navbar-collapse form-inline">
                        <button type="button" class="btn btn-primary" data-target="#photoModal" data-toggle="modal"
                                style="margin-right: 50px;">
                            新增
                        </button>
                        <div class="form-group">
                            <input id="search-context" type="text" name="searchContext" class="form-control"
                                   placeholder="请输入查询信息" value="${searchContext}">
                        </div>
                        <button type="submit" class="btn btn-primary" id="searchPhoto" onclick="doQuery();"
                                style="margin-left: 10px;">查询
                        </button>
                    </div>
                </div>
            </div>
            <table id="table"></table>
        </div>

        <div id="photoModal" class="modal fade" role="dialog" aria-labelledby="photoModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button id="photo-close" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 id="photoModalLabel">照片信息</h4>
                        <input type="text" id="photo-id" value="" style="display: none">
                    </div>
                    <div class="modal-body">
                        <form id="photo_form" class="form-horizontal">
                            <div class="form-group text-center">
                                <label>
                                    <img id="photo-image" src="/img/default.jpg" class="img-circle"
                                         style="width: 128px;height: 128px;" alt="photo">
                                    <input type="file" id="photo-file" style="width: 128px;display: none">
                                </label>
                            </div>
                            <div class="form-group">
                                <label for="photo-title" class="col-sm-2 control-label">主题</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="photo-title" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="photo-location" class="col-sm-2 control-label">地点</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="photo-location" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="photo-time" class="col-sm-2 control-label">时间</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="photo-time">
                                </div>

                            </div>
                            <div class="form-group">
                                <label for="photo-desc" class="col-sm-2 control-label">描述</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="photo-desc">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="photo-video" class="col-sm-2 control-label">视频</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="photo-video">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                        <button class="btn btn-primary" onclick="submitForm()">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/photo/list.js"></script>
</body>
</html>