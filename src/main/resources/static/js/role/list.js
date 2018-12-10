$(function () {
    //初始化表数据
    initTable();
});

function doQuery() {
    $('#table').bootstrapTable('refresh');    //刷新表格
}

//初始化Table
function initTable() {
    $('#table').bootstrapTable({
        url: '/blueRole/getRoleList',       //请求后台的URL（*）
        method: 'post',                     //请求方式（*）
        // toolbar: '#toolbar',             //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortName: "id",                     //排序列
        sortOrder: "asc",                   //排序方式
        contentType: 'application/x-www-form-urlencoded',
        queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
        queryParams: function (params) {
            return {
                searchContext: $("#search_context").val(),
                offset: params.offset,  //页码
                limit: params.limit,   //页面大小
                order: params.order, //排序
                sort: params.sort //排序
            };
        },           //传递参数（*）
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: false,                //是否启用点击选中行
        height: $(window).height() - 110,     //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        responseHandler: responseHandler,
        columns: [{
            field: 'id',
            title: '序号',
            align: 'center'
        }, {
            field: 'name',
            title: '角色名称',
            align: 'center'
        }, {
            field: 'code',
            title: '角色编码',
            align: 'center'
        }, {
            field: 'status',
            title: '状态',
            align: 'center',
            formatter: function (value, row, index) {
                if (row['status'] === 1) {
                    return '正常';
                }
                if (row['status'] === 0) {
                    return '禁用';
                }
                return value;
            }
        }, {
            field: 'creator',
            title: '创建人',
            align: 'center'
        }, {
            field: 'created_time',
            title: '创建时间',
            align: 'center',
            formatter: function (value, row, index) {
                return changeDateFormat(value);
            }
        }, {
            field: 'modifier',
            title: '修改人',
            align: 'center'
        }, {
            field: 'modified_time',
            title: '修改时间',
            align: 'center',
            formatter: function (value, row, index) {
                return changeDateFormat(value);
            }
        }, {
            field: 'operation',
            title: '操作',
            align: 'center',
            events: operateEvents,//给按钮注册事件
            formatter: operateFormatter//表格中增加按钮
        }]
    });
}

//刷新页面后返回数据
function responseHandler(res) {
    if (res.data !== null) {
        return {
            "rows": res.data,
            "total": res.message
        };
    } else {
        return {
            "rows": [],
            "total": 0
        };
    }
}

//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval !== null) {
        var date = new Date(parseInt(dateVal, 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}

// 修改按钮、删除按钮
function operateFormatter(value, row, index) {
    return [
        '<button id="btn_system" type="button" class="btn btn-info" data-toggle="modal">权限管理</button>',
        '<button id="btn_user" type="button" class="btn btn-info" data-toggle="modal" style="margin-left: 10px;">关联用户</button>',
        '<button id="btn_edit" type="button" class="btn btn-info" data-toggle="modal" style="margin-left: 10px;">修改角色</button>',
        '<button id="btn_delete" type="button" class="btn btn-warning" style="margin-left: 10px;">禁用角色</button>'
    ].join('');
}

window.operateEvents = {
    // 点击权限管理按钮执行的方法
    'click #btn_system': function (e, value, row, index) {
        var roleId = row['id'];
        //1-打开弹窗
        $("#role-system-model").modal("show");

        $("#role-system-roleId").val(roleId);

        //模态框关闭销毁table，避免缓存
        $('#role-system-model').on('hidden.bs.modal', function () {
            zTreeObject.destroy();
        });
        //2-初始化权限树
        createTree(roleId);
    },
    // 点击关联用户按钮执行的方法
    'click #btn_user': function (e, value, row, index) {
        var roleId = row['id'];

        $('#user-role-model').modal('show');
        createUserRoleTable(roleId);
        $("#user-role-roleId").val(roleId);

        //模态框关闭销毁table，避免缓存
        $('#user-role-model').on('hidden.bs.modal', function () {
            $("#create-user-role").val("");
            $("#user-role-context").removeClass("has-error");
            $("#user-code-error-message").text("");
            $("#role-user").bootstrapTable("destroy");
        });
    },
    // 点击修改角色按钮执行的方法
    'click #btn_edit': function (e, value, row, index) {
        $.ajax({
            type: "GET",
            url: "/blueRole/" + row['id'] + "/detail",
            data: {},
            dataType: 'JSON',
            success: function (data) {
                if (data.status !== 0) {
                    console.log(data.message);
                    return;
                }
                var roleInfo = data.data;
                document.getElementById("edit_id").value = roleInfo.id;
                document.getElementById("edit_name").value = roleInfo.name;
                document.getElementById("edit_code").value = roleInfo.code;
                $("input[name=status][value='" + roleInfo.status + "']").attr("checked", true);
                document.getElementById("edit_remark").value = roleInfo.remark;
                // 显示模态框
                $('#editRole').modal('show');
            }
        });

        //关闭模态框后清空数据
        $('#editRole').on('hidden.bs.modal', function () {
            $('#edit_id').val("");
            $('#edit_name').val("");
            $('#edit_code').val("");
            $("input[name=status][value='0']").removeAttr('checked');
            $("input[name=status][value='1']").removeAttr('checked');
            $('#edit_remark').val("");
        });
    },

    // 点击禁用角色按钮执行的方法
    'click #btn_delete': function (e, value, row, index) {
        $.ajax({
            type: "GET",
            url: "/blueRole/" + row['id'] + "/delete",
            data: {},
            dataType: 'JSON',
            success: function (data) {
                if (data.status !== 0) {
                    console.log(data.message);
                    return;
                }
                console.log('删除成功');
                $("#table").bootstrapTable('refresh');
            }
        });
        return false;
    }
};

$(window).resize(function () {
    $("#table").bootstrapTable('resetView', {
        height: $(window).height() - 100
    });
});

//新建用户保存操作
function submitCreateForm() {
    var name = $("#create_name").val();
    var code = $("#create_code").val();
    var remark = $("#create_remark").val();
    if (name === "" && remark === "") {
        alert("请填写角色信息!");
        return false;
    }

    //提交表单
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/blueRole/save",
        data: {
            name: name,
            code: code,
            remark: remark
        },
        success: function (result) {
            if (result.status === 0) {
                $("#createRole").modal('hide');
                $("#table").bootstrapTable('refresh');
            } else {
                console.log("保存失败，服务器内部异常！");
            }
        },
        error: function () {
            console.log("操作失败，请检查网络！");
        }
    });

    //关闭模态框后清空数据
    $('#createRole').on('hidden.bs.modal', function () {
        $('#create_name').val("");
        $('#create_code').val("");
        $('#create_remark').val("");
    });
}

//修改用户信息保存操作
function submitEditForm() {

    var name = $("#edit_name").val();
    var code = $("#edit_code").val();
    var status = $("input[name='status']:checked").val();
    var remark = $("#edit_remark").val();
    var id = $("#edit_id").val();

    if (name === "" && code === "" && remark === "") {
        alert("请填写需要变更信息！");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "/blueRole/" + id + "/update",
        dataType: 'json',
        data: {
            name: name,
            code: code,
            status: status,
            remark: remark
        },
        success: function (result) {
            if (result.status === 0) {
                $("#editRole").modal('hide');
                $("#table").bootstrapTable('refresh');
            } else {
                console.log("保存失败，服务器内部异常！");
            }
        },
        error: function () {
            console.log("操作失败，请检查网络！");
        }
    });
}

// $("#table").bootstrapTable('remove', {field: 'id',values: [row['id']]});
// 表格中插入数据
// $("#table").bootstrapTable('insertRow', {index: i, row: result.data[i]});
// 更新某一行数据
// $("#table").bootstrapTable('updateRow', {index: indexT, row: rowT});

/*
*角色-用户关联关系js
*/

// 初始化角色的所属用户列表
function createUserRoleTable(roleId) {
    $("#role-user").bootstrapTable({
        url: '/blueRole/queryUserRoleInfoByPage',                      //请求后台的URL（*）
        method: 'post',                                            //请求方式（*）
        //toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortName: "created_time",
        sortable: true,                     //是否启用排序
        sortOrder: "desc",                   //排序方式
        edit: true,
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                        //每页的记录行数（*）
        pageList: [10, 30, 50, 70],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列（选择显示的列）
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,              //最少允许的列数
        clickToSelect: true,                 //是否启用点击选中行
        uniqueId: "id",                      //每一行的唯一标识，一般为主键列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                     //是否显示详细视图
        detailView: false,                   //是否显示父子表
        contentType: 'application/x-www-form-urlencoded',
        queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
        responseHandler: responseHandler,
        queryParams: function (params) {
            return {
                roleId: roleId,
                searchContext: $("#user-role-search-context").val(),
                offset: params.offset,  //页码
                limit: params.limit,   //页面大小
                order: params.order, //排序
                sort: params.sort //排序
            };
        },           //传递参数（*）
        columns: [{
            field: 'id',
            title: '序号',
            halign: 'center',
            align: 'center'
        }, {
            field: 'userName',
            title: '用户名称',
            halign: 'center',
            align: 'center'
        }, {
            field: 'roleName',
            title: '角色名称',
            halign: 'center',
            align: 'center'
        }, {
            field: 'creator',
            halign: "center",
            align: 'center',
            title: '创建人',
        }, {
            field: 'createdTime',
            halign: "center",
            align: 'center',
            title: '创建时间',
            formatter: function (value, row, index) {
                return changeDateFormat(value);
            }
        }, {
            field: 'operation',
            title: '操作',
            width: 120,
            align: 'center',
            valign: 'middle',
            formatter: userRoleActionFormatter
        }]
    });
}

// 所属用户弹窗操作列格式化
function userRoleActionFormatter(value, row, index) {
    var id = row.id;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs ' onclick=\"deleteUserRole('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
    return result;
}

// 新建用户角色关系
function createUserRole() {

    var roleId = $("#user-role-roleId").val();

    var userName = $("#create-user-role").val().trim();

    if (!userRoleFormValidater(userName)) {
        return;
    }

    $.ajax({
        type: "post",
        url: "/blueRole/saveUserRole",
        data: {
            roleId: roleId,
            userName: userName
        },
        dataType: 'JSON',
        success: function (result) {
            if (result.status === 0) {
                $("#create-user-role").val("");
                $("#role-user").bootstrapTable('refresh', {silent: true});
            } else {
                $("#user-role-context").addClass("has-error");
                $("#user-code-error-message").text(result.message);
            }
        },
        error: function () {
            console.log("关联关系保存失败，请检查网络！");
        }
    });
}

// 查询用户关系
function searchUserRole() {
    $("#role-user").bootstrapTable('refresh', {silent: true});
}

// 删除用户角色关系记录
function deleteUserRole(id) {
    $.ajax({
        type: "get",
        url: "/blueRole/" + id + "/deleteUserRoleById",
        data: {},
        dataType: 'JSON',
        success: function (result) {
            if (result.status === 0) {
                $("#role-user").bootstrapTable('refresh', {silent: true});
            } else {
                alert("关联关系删除失败，服务器内部异常！");
            }
        },
        error: function () {
            console.log("关联关系删除失败，请检查网络！");
        }
    });
}

// 自定义所属用户名输入框校验器
function userRoleFormValidater(userName) {
    if (userName.length === 0) {
        $("#user-role-context").addClass("has-error");
        $("#user-code-error-message").text("用户名不能为空");
        return false;
    } else if (userName.length > 40) {
        $("#user-role-context").addClass("has-error");
        $("#user-code-error-message").text("用户名最大长度为40");
        return false;
    } else {
        $("#user-role-context").removeClass("has-error");
        $("#user-code-error-message").text("");
        $("#user-role-context").addClass("has-success");
        return true;
    }
}

/*
 * 此部分为角色权限操作JS
 */
// 权限树对象
var zTreeObject = {}

// 创建树
function createTree(roleId) {
    // zTree配置选项
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            radioType: "all"
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    // 获取zTree的节点数据
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/blueSystem/" + roleId + "/getZTreeNodesForAuthAction",
        success: function (result) {
            if (result.status === 0) {
                var zNodes = result.data;
                zTreeObject = $.fn.zTree.init($("#role-system-tree"), setting, zNodes);
            } else {
                alert("权限数据加载失败，服务器内部异常！");
            }
        },
        error: function () {
            console.log("操作失败，请检查网络！");
        }
    });
}

// 保存权限操作
function saveRoleSystem() {
    var checkedNodes = zTreeObject.getCheckedNodes(true);
    var authorityIds = "";
    if (checkedNodes.length > 0) {
        for (var i = 0; i < checkedNodes.length; i++) {
            authorityIds += checkedNodes[i].id + ","
        }
        authorityIds = authorityIds.substring(0, authorityIds.length - 1);
    }

    var roleId = $("#role-system-roleId").val();

    $.ajax({
        url: '/blueRole/saveAuthorityByRoleId',
        type: "post",
        dataType: "json",
        data: {
            authorityIds: authorityIds,
            roleId: roleId
        },
        success: function (result) {
            if (result.status === 0) {
                $("#role-system-model").modal('hide');
                zTreeObject.destroy();
            } else {
                alert("权限保存失败！");
            }
        },
        error: function () {
            console.log("权限保存失败，请检查网络！");
        }
    });
}