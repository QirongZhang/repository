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

<title>测试分页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/jquery.json-2.4.js"></script>
<script src="<%=path%>/script/dataTables/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="<%=path%>/script/dataTables/jquery.dataTables.min.css">
<script type="text/javascript">
$(function() {
	var urlMap ={};
	urlMap.add_url = '/AdminAction';
	alert(urlMap.add_url);
	
	$('#resultfile_datatable').DataTable(//创建一个Datatable
        {
                            
                    ajax : {//通过ajax访问后台获取数据
                        "url": "<%=path%>/ResultfilesAction!getAllResultFiles",//后台地址
                        "dataSrc": function (json) {//获取数据之后处理函数，json就是返回的数据
                            var dataSet = new Array();
                            dataSet=json.data;
                            alert(dataSet);
                             //...//对数据处理过程
                            return dataSet;//再将数据返回给datatable控件使用
                        }
                    },
                    iDisplayLength: 6,//每页显示10条数据
                    //bPaginate : true,// 分页按钮
                    pagingType: "simple_numbers",//设置分页控件的模式 
                    serverSide: true,//如果是服务器方式，必须要设置为true
                    processing: true,//设置为true,就会有表格加载时的提示
                    columns : [	{"data" : "admin_id"}, //各列对应的数据列
                                {"data" : "admin_name"}, 
                                {"data" : "sex"}, 
                                {"data" : "phone_num"}, 
                                {"data" : null} ],
                    columnDefs : [ {//列渲染，可以添加一些操作等
                        targets : 4,//表示是第8列，所以上面第8列没有对应数据列，就是在这里渲染的。
                        render : function(data, type, row) {//渲染函数                                                      
                            var html = '&nbsp;<button type="button" class="btn btn-info btn-sm" ">修改</button>&nbsp;<button type="button" class="btn btn-danger btn-sm" ">删除</button>';//这里加了两个button，一个修改，一个删除
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
                        $("#mytool").append('<button type="button" class="btn btn-warning btn-sm" id="importfilebutton" onclick="jumpimportfilepage();">添加</button>');//这里在表格的上面加了一个添加标签
                    }
                });
});
</script>

</head>
<body>
<s:debug></s:debug>
	<table style="" id="resultfile_datatable" class="table text-center" >
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
	
</body>
</html>
