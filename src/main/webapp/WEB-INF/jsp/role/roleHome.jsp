<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<div style="margin-top: 30px;">
    <article class="htmleaf-container">s
        <header class="htmleaf-header">
            <h1>图片列表</h1>
        </header>
        <section id="gallery-wrapper" class="wrapper">
            <c:if test="${list.size() le 0 }">
                <tr>
                    <td colspan="8">目前还没有角色数据</td>
                </tr>
            </c:if>
            <c:if test="${list.size() gt 0}">
                <c:forEach items="${list}" var="role">
                    <article class="white-panel">
                        <a href="/role/${role.id}/detail">
                            <img class="thumbnail gallerybox" src="${role.pic }">
                        </a>
                    </article>
                </c:forEach>
            </c:if>

        </section>
    </article>
</div>
<script src="/js/role/pinterest_grid.js"></script>
<script type="text/javascript">
    //瀑布流插件
    $(function () {
        $("#gallery-wrapper").pinterest_grid({
            no_columns: 4,
            padding_x: 10,
            padding_y: 10,
            margin_bottom: 50,
            single_column_breakpoint: 700
        });

    });
</script>
</body>
</html>
