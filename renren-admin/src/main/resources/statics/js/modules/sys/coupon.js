$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/coupon/list',
        datatype: "json",
        colModel: [			
//			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '优惠卷名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '优惠卷编号', name: 'couponSn', index: 'coupon_sn', width: 80 }, 			
			{ label: '过期时间', name: 'expirationTime', index: 'expiration_time', width: 80 }, 			
			{ label: '0折扣券 1代金券 2兑换券', name: 'type', index: 'type', width: 80 }, 			
			{ label: '所属品牌', name: 'brandId', index: 'brand_id', width: 80,formatter: function(cellvalue, options, rowdata) {
                var brandname = '' ;
                if(rowdata.brandId!=null&&rowdata.brandId!=""){
                    $.ajax({
                        type: "POST",
                        async:false,
                        url: baseURL + "sys/brand/info/"+rowdata.brandId,
                        success: function(r){
                        	brandname = r.brand.name;

                        }
                    });
                }

                return brandname;
            } },
			{ label: '折扣率(%)', name: 'rebateNum', index: 'rebate_num', width: 80 }, 			
			{ label: '金额', name: 'price', index: 'price', width: 80 }, 			
			{ label: '兑换(1,2)商品集合 用字符串分割', name: 'exchangeProduct', index: 'exchange_product', width: 80 }, 			
			{ label: '详情介绍', name: 'content', index: 'content', width: 80 }, 			
			{ label: '优惠券数量', name: 'count', index: 'count', width: 80 }, 			
//			{ label: '状态 ', name: 'state', index: 'state', width: 80 },
			{ label: '状态', name: 'state', width: 80, formatter: function(value, options, row){
				return value === 1 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			name:null
		},
		showList: true,
		title: null,
		coupon: {},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.getbrand("add");
			vm.coupon = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getbrand("update");
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.coupon.id == null ? "sys/coupon/save" : "sys/coupon/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.coupon),
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
                        url: baseURL + "sys/coupon/delete",
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
			$.get(baseURL + "sys/coupon/info/"+id, function(r){
                vm.coupon = r.coupon;
            });
		},
		
		//查询商品所有品牌信息
        getbrand: function(mn){
            $("#brandId").html("");
            $.get(baseURL + "sys/brand/alllist", function(r){
                var temp;
                $.each(r.alllist, function (i) {
                    if(mn =='add' && i ==0){
                        vm.coupon.brandId = r.alllist[i].id;
                    }
                    temp +="<option value='"+r.alllist[i].id+"'>"+r.alllist[i].name+"</option>";

                });
                $("#brandId").append(temp);

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