<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>用户列表页面</title>
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path%>/script/dataTables/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="<%=path%>/script/dataTables/jquery.dataTables.min.css">
<style type="text/css">
	.admin-list{width : 95%;margin: 0 auto;}
	table>thead>tr>th{text-align:center;} 
	table>thead{background-color: #E65097;border: 1px solid black;font-size: 20px;font-family: "华文楷体" ;}
	table>tbody{font-size:20px;text-align: center;}
</style>
</head>
<script type="text/javascript">

	var jumpimportfilepage2 = function(url) {
		alert(url);
		$('#myModal').modal('toggle');
		$("#sure").click(function() {
			$.ajax({
				url: url ,
				type:'GET',
				dataType:'html',
				success:function(result){
					//window.history.pushState(state, state.title, state.url);
			        $("#index-body-right-child-one").html(result);
			    }
			});
		});
		
	};


	var jumpimportfilepage = function(url) {
		alert(url);
			$.ajax({
				url: url ,
				type:'GET',
				dataType:'html',
				success:function(result){
					//window.history.pushState(state, state.title, state.url);
			        $("#index-body-right-child-one").html(result);
			    }
			});
		};
	
	$(function(){
		table=$('#resultfile_datatable').DataTable(//创建一个Datatable
		        {
		        			"lengthMenu": [ 6, 15, 20],     
		                    ajax : {//通过ajax访问后台获取数据
		                        "url": "<%=path%>/ResultfilesAction!getAllResultFiles",//后台地址
		                        "dataSrc": function (json) {//获取数据之后处理函数，json就是返回的数据
		                            var dataSet = new Array();
		                            dataSet=json.data;
		                            //alert(dataSet);
		                             //...//对数据处理过程
		                            return dataSet;//再将数据返回给datatable控件使用
		                        }
		                    },
		                    iDisplayLength: 7,//每页显示10条数据
		                    //bPaginate : true,// 分页按钮
		                    pagingType: "simple_numbers",//设置分页控件的模式 
		                    serverSide: true,//如果是服务器方式，必须要设置为true
		                    processing: true,//设置为true,就会有表格加载时的提示
		                    columns : [	{"data" : "admin_id"}, //各列对应的数据列
		                                {"data" : "admin_name"}, 
		                                {"data" : "sex"}, 
		                                {"data" : "phone_num"}, 
		                                {"data" : null} ],
		                    columnDefs : [ 
		                       {
		                    	   orderable:false,
		                    	   targets : 4
		                       },
		                       {//列渲染，可以添加一些操作等
		                        targets : 4,//表示是第8列，所以上面第8列没有对应数据列，就是在这里渲染的。
		                        data: null,
		                        render : function(data, type, row) {//渲染函数                
		                        	var id =  row.admin_id;
		                      
		                       		//alert(id);
		                            var html = "&nbsp;<a class='btn btn-info btn-sm' id='btn-edit' onclick=\"jumpimportfilepage(\'<%=path%>/AdminAction!edit?admin_id= "+id+"\');\" >修改</a>"
		                            + "&nbsp;<a class='btn btn-danger btn-sm' id='btn-delete' onclick=\"jumpimportfilepage2(\'<%=path%>/AdminAction!delete?admin_id= "+id+"\');\"  >删除</a>";//这里加了两个button，一个修改，一个删除
		                            return html;//将改html语句返回，就能在表格的第8列显示出来了
		                        }
		                    },{
		                         orderable:true//禁用排序
		                         //targets:[1,2]   //指定的列
		                     }
		                    ],
		                     "language": {//国际化
		                            "processing":"<p style=\"font-size: 20px;color: #F79709;\">正在玩命加载中。。。。请稍后！</p>",//这里设置就是在加载时给用户的提示
		                            "lengthMenu": "_MENU_ 条记录每页",
		                            "zeroRecords": "没有找到记录",
		                            "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
		                            "infoEmpty": "无记录",
		                            "infoFiltered": "(从 _MAX_ 条记录过滤)",
		                            "paginate": {
		                                "previous": "上一页",
		                                "next": "下一页"
		                            }
		                        },
		                    "dom": "<'row'<'col-xs-2'l><'#mytool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",//给表格改样式
		                    
		                    initComplete : function() {//表格完成时运行函数                      
		                        $("#mytool").append('<button type="button" class="btn btn-warning btn-sm" id="importfilebutton" onclick="jumpimportfilepage(\'<%=path%>/AdminAction!showAddJsp\');">添加</button>');//这里在表格的上面加了一个添加标签
		                    }
		                });
	});
</script>
<body>
	<div class="admin-list">
		<div class="table text-center" style="margin-top: 20px;">
			<table class="table table-hover" id="resultfile_datatable">
				<thead>
				    <tr>
						<th class="col-md-2">用户账号</th>
						<th class="col-md-2">用户名</th>
						<th class="col-md-2">性别</th>
						<th class="col-md-2">电话号码</th>
						<th class="col-md-3">操作</th>
				     </tr>
				  </thead>
				  <tbody>
				  </tbody>
			</table>
		</div>
		<!-- table结束 -->
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" id="cancle" value="false" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">在线小说阅读系统</h4>
	            </div>
	            <div class="modal-body">您确认要删除本条记录？</div>
	            <div class="modal-footer">
	                <button type="button" id="cancle" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" id="sure" class="btn btn-primary">确定</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	<!-- 模态框（Modal） -->
</body>
</html>
