<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>角色列表</title>
    <style>
        table,table tr th, table tr td{
            border:1px solid #0094ff;
        }

        table th {
            text-align: center;
            background: #66677c;
        }
        table td{
            text-align: center;
            vertical-align: middle!important;;
        }

        table tr:hover {
            background-color: #C1EBFF;
        }

        /* 表格容器样式，用flex布局可保证table内容能铺满剩余空间 */
        .sti-tbl-container {
            height: 100%;
            overflow: hidden;
            display: flex;
            flex-direction: column;
            position: fixed;
        }

        /* 设置表格的布局方式，用于宽度对齐 */
        .sti-tbl-body > table, .sti-tbl-header > table {
            table-layout: fixed;
        }

        /* 设置表格内容容器，用于铺满整个剩余空间 */
        .sti-tbl-container .sti-tbl-body {
            flex-grow: 1;
            overflow-y: scroll;
        }

    </style>
</head>
<body style="overflow:hidden;">
<%@ include file="../common/header.jsp" %>
<div style="margin-top: 60px;">
    <div style="width:10%;float:left;position: fixed;margin-left: 30px;">
        <a href="/jsp/role/roleAdd.jsp" class="btn btn-primary" role="button">新增角色</a>
    </div>
    <div style="width:90%;float:right;">
        <div class="sti-tbl-container">
            <div class="sti-tbl-header" style="padding-right:17px;">
                <table class="table table-responsive" style="margin-bottom: 0px;border-bottom-style: none;">
                    <tr>
                        <th width="10%">角色ID</th>
                        <th width="10%">名称</th>
                        <th width="10%">性别</th>
                        <th width="10%">年龄</th>
                        <th width="20%">图片</th>
                        <th width="20%">描述</th>
                        <th width="10%">视频</th>
                        <th width="10%">操作</th>
                    </tr>
                </table>
            </div>
            <div class="sti-tbl-body" style="margin-bottom: 50px;">
                <table class="table table-responsive">
                    <tbody>
                    <c:if test="${list.size() le 0 }">
                        <tr>
                            <td colspan="8">目前还没有角色数据</td>
                        </tr>
                    </c:if>
                    <c:if test="${list.size() gt 0}">
                        <c:forEach items="${list}" var="role">
                            <tr>
                                <td width="10%">${role.id }</td>
                                <td width="10%">
                                    <a href="/role/${role.id}/detail">${role.name }</a>
                                </td>
                                <td width="10%">${role.sex }</td>
                                <td width="10%">${role.age }</td>
                                <td width="20%"><img id="image" src="${role.pic }" class="img-circle"
                                                     style="width: 128px;height: 128px;"></td>
                                <td width="20%">${role.description }</td>
                                <td width="10%">${role.video }</td>
                                <td width="10%">
                                    <a href="/role/${role.id }/update">更新</a>
                                    <a href="/role/${role.id }/delete">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>
