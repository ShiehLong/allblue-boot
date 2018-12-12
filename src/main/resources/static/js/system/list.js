var zTreeObject;

$(document).ready(function () {
    $("#tree-container").css("height", $(document).height() - 250 + "px");
    createTree();
});

function createTree() {
    var setting = {
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            selectedMulti: false
        },
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        edit: {
            enable: true,
            showRemoveBtn: showRemoveBtn,
            removeTitle: "删除节点",
            showRenameBtn: false
        },
        callback: {
            beforeRemove: beforeRemove,
            onRemove: onRemove //移除事件
        }
    };
    // 获取zTree的节点数据
    $.ajax({
        type: "get",
        dataType: "json",
        url: '/blueSystem/getSystemList',
        success: function (result) {
            if (result.status === 0) {
                var zNodes = result.data;
                zTreeObject = $.fn.zTree.init($("#regionZTree"), setting, zNodes);
            } else {
                console.log("权限数据加载失败，服务器内部异常！");
            }
        },
        error: function (data) {
            if (data.status === 403) {
                alert("没有权限！");
            }
        }
    });
}

function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");

    if (treeNode.editNameFlag || $("#editBtn_" + treeNode.tId).length > 0) return;

    var addStr = "<span class='button edit' id='editBtn_" + treeNode.tId
        + "' title='编辑节点' onfocus='this.blur();'></span>";
    sObj.after(addStr);

    var btn = $("#editBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function () {
        openEditModel(treeNode.id);
        return false;
    });
};

function removeHoverDom(treeId, treeNode) {
    $("#editBtn_" + treeNode.tId).unbind().remove();
};

function beforeRemove(treeId, treeNode) {
    return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
}

function onRemove(e, treeId, treeNode) {
    var code = treeNode.id;
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/blueSystem/delete/" + code,
        data: {},
        success: function (result) {
            if (result.status === 0) {
                zTreeObject.destroy();
                createTree();
            } else {
                console.log("保存失败，服务器内部异常！");
            }
        },
        error: function (data) {
            if (data.status === 403) {
                alert("没有权限！");
            }
        }
    });
}

function showRemoveBtn(treeId, treeNode) {
    return !(treeNode.level === 0);
}

// 打开创建弹窗
function openCreateModel() {

    $.ajax({
        type: "get",
        async: 'false',
        dataType: "json",
        url: '/blueSystem/getAllSystem',
        success: function (result) {
            if (result.status === 0) {
                var html = "";
                html += "<option value = ''>请选择所属系统</option>";
                for (var i = 0; i < result.data.length; i++) {
                    html += "<option value=" + result.data[i].id + ">"
                        + result.data[i].name + "</option>";
                }
                $("#create_parentCode").html(html);

                // 显示模态框
                $('#createSystem').modal('show');
            } else {
                console.log("获取权限信息失败，服务器内部异常！");
            }
        },
        error: function (data) {
            if (data.status === 403) {
                alert("没有权限！");
            }
        }
    });

    //模态框关闭前，清除上次校验样式
    $('#createSystem').on('hidden.bs.modal', function (e) {
        $('#create_name').val("");
        $('#create_code').val("");
        $("#create_parentCode").html("");
        $('#create_url').val("");
        $('#create_remark').val("");
    });
}

// 提交创建表单
function submitCreateForm() {

    var name = $('#create_name').val();
    var code = $('#create_code').val();
    var parent_code = $("#create_parentCode").val();
    var url = $('#create_url').val();
    var remark = $('#create_remark').val();

    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/blueSystem/save",
        data: {
            name: name,
            code: code,
            parent_code: parent_code,
            url: url,
            remark: remark
        },
        success: function (result) {
            if (result.status === 0) {
                $("#createSystem").modal('hide');
                zTreeObject.destroy();
                createTree();
            } else {
                console.log("保存失败，服务器内部异常！");
            }
        },
        error: function (data) {
            if (data.status === 403) {
                alert("没有权限！");
            }
        }
    });
}

// 打开编辑弹窗
function openEditModel(code) {

    $.ajax({
        type: "get",
        dataType: "json",
        url: '/blueSystem/detail/' + code,
        success: function (result) {
            if (result.status === 0) {
                var systemInfo = result.data;
                $('#edit_name').val(systemInfo.name);
                $('#edit_code').val(systemInfo.code);
                $("#edit_parentCode").val(systemInfo.parent_code);
                $('#edit_url').val(systemInfo.url);
                $('#edit_remark').val(systemInfo.remark);

                // 显示模态框
                $('#editSystem').modal('show');
            } else {
                console.log("获取权限信息失败，服务器内部异常！");
            }
        },
        error: function (data) {
            if (data.status === 403) {
                alert("没有权限！");
            }
        }
    });

    //模态框关闭前，清除上次校验样式
    $('#editSystem').on('hidden.bs.modal', function (e) {
        $('#edit_name').val("");
        $('#edit_code').val("");
        $("#edit_parentCode").val("");
        $('#edit_url').val("");
        $('#edit_remark').val("");
    });
}

// 提交编辑表单
function submitEditForm() {

    var name = $('#edit_name').val();
    var code = $('#edit_code').val();
    var parent_code = $("#edit_parentCode").val();
    var url = $('#edit_url').val();
    var remark = $('#edit_remark').val();

    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/blueSystem/update/" + code,
        data: {
            name: name,
            parent_code: parent_code,
            url: url,
            remark: remark
        },
        success: function (result) {
            if (result.status === 0) {
                $("#editSystem").modal('hide');
                zTreeObject.destroy();
                createTree();
            } else {
                console.log("保存失败，服务器内部异常！");
            }
        },
        error: function (data) {
            if (data.status === 403) {
                alert("没有权限！");
            }
        }
    });
}