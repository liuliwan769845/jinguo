$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/gfe/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户编号', name: 'userId', index: 'user_id', width: 80 },
			{ label: '用户名称', name: 'userId', index: 'user_id', width: 80,formatter: function(cellvalue, options, rowdata) {
                var username = '' ;
                if(rowdata.userId!=null&&rowdata.userId!=""){
                    $.ajax({
                        type: "POST",
                        async:false,
                        url: baseURL + "sys/wxuser/info/"+rowdata.userId,
                        success: function(r){
                        	username = r.product.name;

                        }
                    });
                }

                return username;
            } },
			{ label: '数量', name: 'num', index: 'num', width: 80 }, 			
//			{ label: '0收获 1兑换', name: 'type', index: 'type', width: 80 },
			{ label: '获取类型', name: 'type', index: 'type', width: 60,formatter: function(item, index) {
                if (item === 0) {
                    return '<span class="label label-warning">收获</span>';
                }
                if (item === 1) {
                    return '<span class="label label-success">兑换</span>';
                }
            } },
			{ label: '单价', name: 'unitPrice', index: 'unit_price', width: 80 }, 			
//			{ label: '0进行中 1已完成', name: 'state', index: 'state', width: 80 }, 
			{ label: '进度', name: 'state', index: 'state', width: 60,formatter: function(item, index) {
                if (item === 0) {
                    return '<span class="label label-warning">进行中</span>';
                }
                if (item === 1) {
                    return '<span class="label label-success">已完成</span>';
                }
            } },
			{ label: '总价', name: 'countPrice', index: 'count_price', width: 80 }, 			
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '名称', name: 'name', index: 'name', width: 80 }			
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			userId:null
		},
		showList: true,
		title: null,
		gfe: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.gfe = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.gfe.id == null ? "sys/gfe/save" : "sys/gfe/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.gfe),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
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
                        url: baseURL + "sys/gfe/delete",
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
			$.get(baseURL + "sys/gfe/info/"+id, function(r){
                vm.gfe = r.gfe;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'userId': vm.q.userId},
                page:page
            }).trigger("reloadGrid");
		}
	}
});