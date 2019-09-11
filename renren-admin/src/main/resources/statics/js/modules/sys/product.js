$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/product/list',
        datatype: "json",
        colModel: [			
			{ label: '商品编号', name: 'id', index: 'id', width: 50, key: true },
			{ label: '分类', name: 'classifyId', index: 'classify_id', width: 80,formatter: function(cellvalue, options, rowdata) {
                var Pctitle = '' ;
                if(rowdata.classifyId!=null&&rowdata.classifyId!=""){
                    $.ajax({
                        type: "POST",
                        async:false,
                        url: baseURL + "sys/productclassify/info/"+rowdata.classifyId,
                        success: function(r){
                        	Pctitle = r.productClassify.title;

                        }
                    });
                }

                return Pctitle;
            } },
			{ label: '分类编号', name: 'classifyId', index: 'classify_id', width: 80 },
			{ label: '品牌', name: 'brandId', index: 'brand_id', width: 80,formatter: function(cellvalue, options, rowdata) {
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
			{ label: '商品名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '商品标题', name: 'title', index: 'title', width: 80 }, 			
			{ label: '商品主图', name: 'imgUrl', index: 'img_url', width: 80,formatter: function(cellvalue, options, rowdata) {
				if(rowdata.imgUrl!=null&&rowdata.imgUrl!=''){
			//		var host = "https://jrjx.ctolab.cn:440";
                        return '<img src='+rowdata.imgUrl+' width="50" height="50"/>';
                    }else{
                        return '';
                    }
                } },
//			{ label: '图片路径', name: 'imgUrlLj', index: 'img_url_lj', width: 80 },
//			{ label: '商品介绍', name: 'content', index: 'content', width: 80 }, 			
			{ label: '商品购买单价', name: 'price', index: 'price', width: 80 }, 			
			{ label: '商品购买金币数量', name: 'gold', index: 'gold', width: 80 }, 			
			{ label: '商品原价', name: 'originalPrice', index: 'original_price', width: 80 }, 			
			{ label: '库存', name: 'stock', index: 'stock', width: 80 }, 			
			{ label: '推荐类型', name: 'type', index: 'type', width: 60,formatter: function(item, index) {
                if (item === 0) {
                    return '<span class="label label-warning"></span>';
                }
                if (item === 1) {
                    return '<span class="label label-success">主推</span>';
                }
                if (item === 2) {
                    return '<span class="label label-success">主推一</span>';
                }
                if (item === 3) {
                    return '<span class="label label-success">主推二</span>';
                }
                if (item === 4) {
                    return '<span class="label label-success">主推三</span>';
                }
            } },
			{ label: '商品创建时间', name: 'createDate', index: 'create_date', width: 80 }			
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
			classifyId:null
        },
        fileList1: [],
		showList: true,
		title: null,
		product: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.fileList1 = [];
			vm.product = {};
			vm.getpcfy("add");
			vm.getbrand("add");
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
            vm.getpcfy("update");
			vm.getbrand("update");
//           vm.getInfo(id)
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
            $.get(baseURL + "sys/product/info/"+id, function(r){
                vm.product = r.product;
                layedits.setContent(layedits1,r.product.content, false);
            });
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.product.id == null ? "sys/product/save" : "sys/product/update";
                vm.product.imgUrl='';
                if(vm.fileList1.length>0){
                    vm.product.imgUrl = vm.fileList1[0].url;
                }
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.product),
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
                        url: baseURL + "sys/product/delete",
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
		
		handleRemove1: function(file, fileList) {
            console.log(fileList);
            vm.fileList1 = [];
            if(fileList.length>0){
                for(var i=0;i<fileList.length;i++){
                    vm.fileList1.push({name: fileList[i].name,url: fileList[i].url});
                }
            }
            console.log(vm.fileList1);

        },
        handlePreview: function(file) {
            console.log(file);
        },

        handsuccess1: function(response, file, fileList) {
            vm.fileList1.push({name:response.url,url:response.url});
        },
		
		getInfo: function(id){
			$.get(baseURL + "sys/product/info/"+id, function(r){
                vm.product = r.product;
            });
		},
		
       //查询商品所有分类信息
		getpcfy: function(mn){
            $("#classifyId").html("");
            $.get(baseURL + "sys/productclassify/alllist", function(r){
                var temp;
                $.each(r.alllist, function (i) {
                    if(mn =='add' && i ==0){
                        vm.product.classifyId = r.alllist[i].id;
                    }
                    temp +="<option value='"+r.alllist[i].id+"'>"+r.alllist[i].title+"</option>";

                });
                $("#classifyId").append(temp);

            });
        },
        //查询商品所有品牌信息
        getbrand: function(mn){
            $("#brandId").html("");
            $.get(baseURL + "sys/brand/alllist", function(r){
                var temp;
                $.each(r.alllist, function (i) {
                    if(mn =='add' && i ==0){
                        vm.product.brandId = r.alllist[i].id;
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
				postData:{'classifyId': vm.q.classifyId},
                page:page
            }).trigger("reloadGrid");
		}
	}
});