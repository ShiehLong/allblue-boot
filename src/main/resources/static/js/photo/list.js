$(function () {
    //初始化表数据
    initTable();

    //实现预览上传功能
    $("#photo").change(function preview() {
        //获取文件框的第一个文件,因为文件有可能上传多个文件,这里是一个文件
        var file = document.getElementById("photo").files[0];
        //可以进行一下文件类型的判断
        var fileType = file.type.split("/")[0];
        if (fileType !== "image") {
            alert("请上传图片");
            return;
        }
        //图片大小的限制
        var fileSize = Math.round(file.size / 1024 / 1024);
        if (fileSize >= 3) {
            alert("请上传小于少于3M的图片");
            return;
        }
        //获取img对象
        var img = document.getElementById("image");
        //建一条文件流来读取图片
        var reader = new FileReader();
        //根据url将文件添加的流中
        reader.readAsDataURL(file);
        //实现onload接口
        reader.onload = function (e) {
            //获取文件在流中url
            url = reader.result;
            //将url赋值给img的src属性
            img.src = url;
        };
        var data = new FormData();
        data.append("uploadImage", file);
        $.ajax({
            type: "POST",
            url: "/photo/upload",
            data: data,
            dataType: 'JSON',
            processData: false,  // 告诉jQuery不要去处理发送的数据
            contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
            success: function (data) {
                if (data.status === 0) {
                    img.src = data.message;
                }
            }
        });
    });
});

function doQuery() {
    $('#table').bootstrapTable('refresh');    //刷新表格
}

//初始化Table
function initTable() {
    $('#table').bootstrapTable({
        url: '/photo/list',                  //请求后台的URL（*）
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
                searchContext: $("#search-context").val(),
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
        // search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        // showColumns: true,                  //是否显示所有的列
        // showRefresh: true,                  //是否显示刷新按钮
        // minimumCountColumns: 2,             //最少允许的列数
        // clickToSelect: true,                //是否启用点击选中行
        height: $(window).height() - 110,     //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        responseHandler: responseHandler,
        columns: [{
            field: 'id',
            title: '序号',
            align: 'center'
        }, {
            field: 'shootingTitle',
            title: '主题',
            align: 'center'
        }, {
            field: 'shootingLocation',
            title: '地点',
            align: 'center'
        }, {
            field: 'shootingTime',
            title: '时间',
            align: 'center'
        }, {
            field: 'shootingPhoto',
            title: '照片',
            align: 'center'
        }, {
            field: 'description',
            title: '描述',
            align: 'center',
        }, {
            field: 'video',
            title: '视频',
            align: 'center'
        }, {
            field: 'operation',
            title: '操作',
            align: 'center',
            events: operateEvents,//给按钮注册事件
            formatter: operateFormatter//表格中增加按钮
        }]
    });
};

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

// 修改按钮、删除按钮
function operateFormatter(value, row, index) {
    return [
        '<button id="btn-edit" type="button" class="btn btn-info" data-toggle="modal">修改</button>',
        '<button id="btn-delete" type="button" class="btn btn-warning" style="margin-left: 10px;">禁用</button>'
    ].join('');
}

window.operateEvents = {
    // 点击修改按钮执行的方法
    'click #btn-edit': function (e, value, row, index) {
        $.ajax({
            type: "GET",
            url: "/photo/detail/" + row['id'],
            data: {},
            dataType: 'JSON',
            success: function (data) {
                if (data.status !== 0) {
                    console.log(data.message);
                    return;
                }
                var PhotoInfo = data.data;
                document.getElementById("edit_id").value = PhotoInfo.id;
                document.getElementById("editModalLabel").innerText = "修改用户【" + PhotoInfo.name + "】信息";
                document.getElementById("image").src = PhotoInfo.photo;
                document.getElementById("edit_email").value = PhotoInfo.email;
                $("input[name=status][value='" + PhotoInfo.status + "']").attr("checked", true);
                // 显示模态框
                $('#editPhoto').modal('show');
            },
            error: function (data) {
                if (data.status === 403) {
                    alert("没有权限！");
                }
            }
        });
    },

    // 点击删除按钮执行的方法
    'click #btn-delete': function (e, value, row, index) {
        $.ajax({
            type: "GET",
            url: "/photo/delete/" + row['id'],
            data: {},
            dataType: 'JSON',
            success: function (data) {
                if (data.status !== 0) {
                    console.log(data.message);
                    return;
                }
                console.log('删除成功');
                $("#table").bootstrapTable('refresh');
            },
            error: function (data) {
                if (data.status === 403) {
                    alert("没有权限！");
                }
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
    var email = $("#create_email").val();
    if (name === "" && email === "") {
        alert("请填写用户信息!");
        return false;
    }

    //提交表单
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/photo/save",
        data: {
            name: name,
            email: email
        },
        success: function (result) {
            if (result.status === 0) {
                $("#createPhoto").modal('hide');
                $("#table").bootstrapTable('refresh');
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

    //关闭模态框后清空数据
    $('#createPhoto').on('hidden.bs.modal', function () {
        $('#create_name').val("");
        $('#create_email').val("");
    });
}

//修改用户信息保存操作
function submitEditForm() {

    var photo = $("#image").attr("src");
    var email = $("#edit_email").val();
    var status = $("input[name='status']:checked").val();
    var password = $("#password").val();
    var retryPassword = $("#retryPassword").val();
    var id = $("#edit_id").val();

    if (photo === "" && email === "" && password === "" && retryPassword === "") {
        alert("请填写需要变更信息！");
        return false;
    }

    if (retryPassword !== password) {
        alert("两次密码不一致！");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "/photo/save",
        dataType: 'json',
        data: {
            id : id,
            photo: photo,
            email: email,
            status: status,
            password: password
        },
        success: function (result) {
            if (result.status === 0) {
                $("#editPhoto").modal('hide');
                $("#table").bootstrapTable('refresh');
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

    //关闭模态框后清空数据
    $('#editPhoto').on('hidden.bs.modal', function () {
        $('#edit_id').val("");
        $('#create_email').val("");
        $('#image').src = "/img/default.jpg";
        $("input[name=status][value='0']").removeAttr('checked');
        $("input[name=status][value='1']").removeAttr('checked');
    });
}

// $("#table").bootstrapTable('remove', {field: 'id',values: [row['id']]});
// 表格中插入数据
// $("#table").bootstrapTable('insertRow', {index: i, row: result.data[i]});
// 更新某一行数据
// $("#table").bootstrapTable('updateRow', {index: indexT, row: rowT});