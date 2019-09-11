$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/orders/list',
        datatype: "json",
        colModel: [			
			{ label: '用户名称', name: 'userId', index: 'user_id', width: 80,formatter: function(cellvalue, options, rowdata) {
                var username = '' ;
                if(rowdata.userId!=null&&rowdata.userId!=""){
                    $.ajax({
                        type: "POST",
                        async:false,
                        url: baseURL + "sys/wxuser/info/"+rowdata.userId,
                        success: function(r){
                        	username = r.wxuser.username;

                        }
                    });
                }

                return username;
            } },
			{ label: '商品名称', name: 'productName', index: 'product_name', width: 80 }, 			
			{ label: '订单号', name: 'sn', index: 'sn', width: 80 }, 			
			{ label: '微信订单号', name: 'wxSn', index: 'wx_sn', width: 80 }, 			
			{ label: '订单金额', name: 'price', index: 'price', width: 80 }, 			
			{ label: '交易金币', name: 'gold', index: 'gold', width: 80 }, 			
			{ label: '商品实际价格', name: 'productPrice', index: 'product_price', width: 80 }, 			
			{ label: '实际付款金额', name: 'totlePrice', index: 'totle_price', width: 80 }, 			
			{ label: '邮费', name: 'mailPrice', index: 'mail_price', width: 80 }, 			
			{ label: '详细地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '联系方式', name: 'userMobile', index: 'user_mobile', width: 80 }, 			
			{ label: '联系人', name: 'username', index: 'username', width: 80 }, 			
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '发货时间', name: 'deliveryDate', index: 'delivery_date', width: 80 }, 			
			{ label: '付款时间', name: 'paymentDate', index: 'payment_date', width: 80 }, 			
			{ label: '0待付款 1已付款 2已发货 3已完成', name: 'state', index: 'state', width: 80 }, 			
			{ label: '订单规格', name: 'sku', index: 'sku', width: 80 }, 			
			{ label: '物流公司', name: 'logistics', index: 'logistics', width: 80 }, 			
			{ label: '物流单号', name: 'logisticsOdd', index: 'logistics_odd', width: 80 }			
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
			sn:null
        },
		showList: true,
		title: null,
		orders: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.orders = {};
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
                var url = vm.orders.id == null ? "sys/orders/save" : "sys/orders/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.orders),
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
                        url: baseURL + "sys/orders/delete",
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
			$.get(baseURL + "sys/orders/info/"+id, function(r){
                vm.orders = r.orders;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'sn': vm.q.sn},
                page:page
            }).trigger("reloadGrid");
		}
	}
});