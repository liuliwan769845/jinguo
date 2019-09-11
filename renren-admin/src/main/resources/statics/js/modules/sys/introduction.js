$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/introduction/list',
        datatype: "json",
        colModel: [			
			{ label: '标题', name: 'title', index: 'title', width: 80 }, 			
			// { label: '具体介绍', name: 'content', index: 'content', width: 80 },
			{ label: '状态', name: 'state', index: 'state', width: 80,formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-success">正常</span>' :
                        '<span class="label label-danger">禁用</span>';
                } },
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    layui.use('layedit', function(){
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			title:null
		},
		showList: true,
		title: null,
		introduction: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.introduction = {};
            var layedits = layui.layedit;
            layedits.set({
                //暴露layupload参数设置接口 --详细查看layupload参数说明
                uploadImage: {
                    url: baseURL + 'sys/upload/uploadImgs',
                    accept: 'image',
                    acceptMime: 'image/*',
                    exts: 'jpg|png|gif|bmp|jpeg',
                    size: '10240'
                }
                //右键删除图片/视频时的回调参数，post到后台删除服务器文件等操作，
                //传递参数：
                //图片： imgpath --图片路径
                //视频： filepath --视频路径 imgpath --封面路径
                , calldel: {
                    url: '/Attachment/DeleteFile'
                }
                //开发者模式 --默认为false
                , devmode: true
                //插入代码设置
                , codeConfig: {
                    hide: true,  //是否显示编码语言选择框
                    default: 'javascript' //hide为true时的默认语言格式
                }
                , tool: [
                    'html', 'code', 'strong', 'italic', 'underline', 'del', 'addhr', '|', 'fontFomatt', 'colorpicker', 'face'
                    , '|', 'left', 'center', 'right', '|', 'link', 'unlink', 'image_alt', 'anchors'
                    , '|', 'fullScreen'
                ]
                , height: '90%'
            });
            var layedits1 = layedits.build('layeditDemo', {
                height: 500 //设置编辑器高度
            });
            layedits.setContent(layedits1,'', false);
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            var layedits = layui.layedit;
            layedits.set({
                //暴露layupload参数设置接口 --详细查看layupload参数说明
                uploadImage: {
                    url: baseURL + 'sys/upload/uploadImgs',
                    accept: 'image',
                    acceptMime: 'image/*',
                    exts: 'jpg|png|gif|bmp|jpeg',
                    size: '10240'
                }
                //右键删除图片/视频时的回调参数，post到后台删除服务器文件等操作，
                //传递参数：
                //图片： imgpath --图片路径
                //视频： filepath --视频路径 imgpath --封面路径
                , calldel: {
                    url: '/Attachment/DeleteFile'
                }
                //开发者模式 --默认为false
                , devmode: true
                //插入代码设置
                , codeConfig: {
                    hide: true,  //是否显示编码语言选择框
                    default: 'javascript' //hide为true时的默认语言格式
                }
                , tool: [
                    'html', 'code', 'strong', 'italic', 'underline', 'del', 'addhr', '|', 'fontFomatt', 'colorpicker', 'face'
                    , '|', 'left', 'center', 'right', '|', 'link', 'unlink', 'image_alt', 'anchors'
                    , '|', 'fullScreen'
                ]
                , height: '90%'
            });
            var layedits1 = layedits.build('layeditDemo', {
                height: 500 //设置编辑器高度
            });
            $.get(baseURL + "sys/introduction/info/"+id, function(r){
                vm.introduction = r.introduction;
                layedits.setContent(layedits1,r.introduction.content, false);
            });
            // vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.introduction.id == null ? "sys/introduction/save" : "sys/introduction/update";
                vm.introduction.content=$(window.frames["LAY_layedit_1"].document).find('body').html();
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.introduction),
                    success: function(r){
                        if(r.code === 0){
                             // layer.msg("操作成功", {icon: 1});
                             // vm.reload();
                             // $('#btnSaveOrUpdate').button('reset');
                             // $('#btnSaveOrUpdate').dequeue();
                             // location.reload()
                            layer.msg('操作成功',{icon: 1},function(){
                                location.reload();
                            });
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "sys/introduction/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "sys/introduction/info/"+id, function(r){
                vm.introduction = r.introduction;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
		}
	}
});