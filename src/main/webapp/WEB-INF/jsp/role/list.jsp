<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>角色列表</title>
    <link rel="stylesheet" type="text/css" href="/css/metroStyle/metroStyle.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="row" style="margin-bottom: 10px;">
                <div class="col-md-12">
                    <div class="collapse navbar-collapse form-inline">
                        <button type="button" class="btn btn-primary" data-target="#createRole" data-toggle="modal"
                                style="margin-right: 50px;">
                            新建
                        </button>
                        <div class="form-group">
                            <input id="search_context" type="text" name="searchContext" class="form-control"
                                   placeholder="请输入查询信息" value="${searchContext}">
                        </div>
                        <button type="submit" class="btn btn-primary" id="searchRole" onclick="doQuery();"
                                style="margin-left: 10px;">查询
                        </button>
                    </div>
                </div>
            </div>
            <table id="table"></table>
        </div>

    </div>
</div>

<%--新建model--%>
<div id="createRole" class="modal fade" role="dialog" aria-labelledby="createModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 id="createModalLabel">
                    新建角色
                </h4>
            </div>
            <div class="modal-body">
                <form id="create_role_form" class="form-horizontal">
                    <div class="form-group">
                        <label for="create_name" class="col-sm-2 control-label">角色名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="create_name" placeholder="请输入角色名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create_code" class="col-sm-2 control-label">角色编码</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="create_code" placeholder="请输入角色编码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create_remark" class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-10">
                            <textarea rows="3" cols="20" class="form-control" id="create_remark"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                <button class="btn btn-primary" onclick="submitCreateForm()">保存</button>
            </div>
        </div>
    </div>
</div>

<%--修改model--%>
<div id="editRole" class="modal fade" role="dialog" aria-labelledby="editModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 id="editModalLabel">
                    修改角色信息
                </h4>
                <input type="text" id="edit_id" value="" style="display: none">
            </div>
            <div class="modal-body">
                <form id="edit_role_form" class="form-horizontal">
                    <div class="form-group">
                        <label for="edit_name" class="col-sm-2 control-label">角色名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="edit_name" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit_code" class="col-sm-2 control-label">角色编码</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="edit_code" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                            <input type="radio" name="status" value="1">有效
                            <input type="radio" name="status" value="0">无效
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit_remark" class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-10">
                            <textarea rows="3" cols="20" class="form-control" id="edit_remark"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                <button class="btn btn-primary" onclick="submitEditForm()">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 权限操作弹窗 -->
<div id="role-system-model" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">权限操作</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <input id="role-system-roleId" type="hidden" name="id" value="">
                        <div class=" col-md-12">
                            <ul id="role-system-tree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveRoleSystem()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--用户-角色model--%>
<div id="user-role-model" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">用户关联</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row .menu-row" style="margin: 15px 0px;">
                        <div class="col-md-12">
                            <div class="collapse navbar-collapse">
                                <form id="user-role-form" class="form-inline">
                                    <input type="hidden" id="user-role-roleId" name="roleId" value="">

                                    <div id="user-role-context" class="input-group" style="margin-right: 50px">
                                        <input id="create-user-role" type="text" name="userCode" class="form-control" placeholder="请输入用户名">
                                        <span class="input-group-btn">
                                <button class="btn btn-success" type="button" onclick="createUserRole()">新增关联</button>
                            </span>
                                    </div>

                                    <div class="form-group">
                                        <input id="user-role-search-context" type="text" name="searchContext" class="form-control"
                                               placeholder="请输入查询信息">
                                    </div>
                                    <button type="button" class="btn btn-primary" onclick="searchUserRole()">查询</button>
                                </form>
                                <div id="user-code-error-message" style="color:#a94442;font-size:85%;padding-top: 5px;"></div>
                            </div>
                        </div>

                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <table id="role-user"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/jquery.ztree.all.min.js"></script>
<script src="/js/role/list.js"></script>
</body>
</html>