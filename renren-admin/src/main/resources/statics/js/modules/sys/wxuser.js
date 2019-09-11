$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/wxuser/list',
        datatype: "json",
        colModel: [			
			{ label: '用户编号', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: 'openid', name: 'wxOpenId', index: 'wx_open_id', width: 80 }, 			
			{ label: '头像', name: 'imgUrl', index: 'img_url', width: 80,formatter: function(cellvalue, options, rowdata) {
				if(rowdata.imgUrl!=null&&rowdata.imgUrl!=''){
			//		var host = "https://jrjx.ctolab.cn:440";
                        return '<img src='+rowdata.imgUrl+' width="50" height="50"/>';
                    }else{
                        return '';
                    }
                } },
			{ label: '金币数量', name: 'goldNum', index: 'gold_num', width: 80 }, 			
			{ label: '余额', name: 'balance', index: 'balance', width: 80 }, 			
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '金果总数量', name: 'goldenFruitCount', index: 'golden_fruit_count', width: 80 }, 			
			{ label: '剩余金果数量', name: 'goldenFruitNum', index: 'golden_fruit_num', width: 80 }, 			
			{ label: '金果开始计算时间(时间戳/秒)', name: 'goldenFruitTime', index: 'golden_fruit_time', width: 80 }, 			
			{ label: '手机号', name: 'mobile', index: 'mobile', width: 80 }			
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
			name:null
		},
		showList: true,
		title: null,
		wxuser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wxuser = {};
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
                var url = vm.wxuser.id == null ? "sys/wxuser/save" : "sys/wxuser/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.wxuser),
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
                        url: baseURL + "sys/wxuser/delete",
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
			$.get(baseURL + "sys/wxuser/info/"+id, function(r){
                vm.wxuser = r.wxuser;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		}
	}
});