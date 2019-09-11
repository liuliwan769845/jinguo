$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/productsku/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '商品编号', name: 'productId', index: 'product_id', width: 80 },
			{ label: '商品名称', name: 'productId', index: 'product_id', width: 80,formatter: function(cellvalue, options, rowdata) {
                var productname = '' ;
                if(rowdata.productId!=null&&rowdata.productId!=""){
                    $.ajax({
                        type: "POST",
                        async:false,
                        url: baseURL + "sys/product/info/"+rowdata.productId,
                        success: function(r){
                        	productname = r.product.name;

                        }
                    });
                }

                return productname;
            } },
			{ label: '规格', name: 'sku', index: 'sku', width: 80 }, 			
			{ label: '库存', name: 'stock', index: 'stock', width: 80 }, 			
			{ label: '金币', name: 'gold', index: 'gold', width: 80 }, 			
			{ label: '价格', name: 'price', index: 'price', width: 80 }, 			
			{ label: '原价', name: 'originalPrice', index: 'original_price', width: 80 }, 			
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
			productId:null
		},
		isshow:false,
		showList: true,
		title: null,
		productSku: {},
		isshow:false,
		skuList: [],
		skList:[],
		productSku:{
			skuIdList:[]
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.productSku = {};
//			vm.getsku("add");
			vm.productSku = {skuIdList:[]};
			//获取规格信息
            this.getSkuList();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
//            vm.getsku("update")
            //获取规格信息
            this.getSkuList();
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.productSku.id == null ? "sys/productsku/save" : "sys/productsku/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.productSku),
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
                        url: baseURL + "sys/productsku/delete",
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
			$.get(baseURL + "sys/productsku/info/"+id, function(r){
                vm.productSku = r.productSku;
            });
		},
		getSkuList: function(){
            $.get(baseURL + "sys/sku/alllist", function(r){
                vm.skuList = r.alllist;
            });
        },
        handleClick:function(id){
        	vm.isshow=true;
        	$.get(baseURL + "sys/sku/selectSkuId?skuid="+id, function(r){
        		console.log(r.list);
        		vm.skList = r.list;
        	});
		},
		
//		//查询规格信息
//		getsku: function(mn){
//            $("#sku").html("");
//            var name=null;
//            $.get(baseURL + "sys/sku/alllist", function(r){
//                var temp;
//                var skuid;
//                console.log(r.alllist)
//                $.each(r.alllist, function (i) {
//                    if(mn =='add' && i ==0){
//                    	 skuid=r.alllist[i].id;
//                    }
//                    temp +="<el-checkbox label='"+r.alllist[i].id+"'>"+r.alllist[i].name+"</el-checkbox>";
// //                  temp +="<option value='"+r.alllist[i].name+"'>"+r.alllist[i].name+"</option>";
//
//                });
//                $("#sku").append(temp);
//                
//                $("#sku1").html("");
//                $.get(baseURL + "sys/sku/selectSkuId?skuid="+skuid, function(r){
//                    var temp;
//                    console.log(r.list)
//                    $.each(r.list, function (i) {
//                        if(mn =='add' && i ==0){
//                        }
//                        temp +="<option value='"+r.list[i].id+"'>"+r.list[i].name+"</option>";
//
//                    });
//                    $("#sku1").append(temp);
//                });
//            });
//        },
        cg:function(){
        	vm.productSku.sku=vm.name1+":"+vm.name2;
        },
		
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'productId': vm.q.productId},
                page:page
            }).trigger("reloadGrid");
		}
	}
});