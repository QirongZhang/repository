<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.text.*"%>

<%@ page import="java.util.*"%>  
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

<title>添加角色</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>

<!-- bootstrap -->
<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<!-- bootstrap -->
<!-- ztree -->
<script type="text/javascript" src="<%=path%>/script/ztree_3.3/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/ztree_3.3/js/jquery.ztree.all-3.3.min.js"></script>

<script type="text/javascript" src="<%=path%>/script/jquery.json-2.4.js"></script>

</head>
<script language="javascript">
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	check: {
		enable: true
	}
	
};
var jsonStr = '${requestScope.jsonStr}';
var jsonArrayObj = jQuery.evalJSON(jsonStr);

var zNodes = jsonArrayObj;
$(document).ready(function(){
	$.fn.zTree.init($("#myTree"), setting, zNodes);
});

function saveData(){
	//获取勾选的角色的树形节点。
	var treeObj = $.fn.zTree.getZTreeObj("myTree");
	var nodeArray = treeObj.getCheckedNodes(true);
	if (nodeArray == null || nodeArray.length == 0){
		window.alert("添加角色时，必须勾选角色所对应的权限");
	}else{
		var idArray = new Array();
		for(var i = 0;i<nodeArray.length;i++){
			var treeNode = nodeArray[i];
			var id = treeNode.id;
			idArray.push(id);
		}
		var role_select_menuid = idArray.join(",");
		document.getElementById("role_select_menuid").value = role_select_menuid;
		
		
		var role_id = $("#role_id").val();
		var role_name = $("#role_name").val();
		var role_remark = $("#role_remark").val();
		var params = {
				"role_id":role_id,
				"role_name":role_name,
				"role_remark":role_remark,
				"role_select_menuid":role_select_menuid
		};	
		$.ajax({
			url:"<%=path%>/RoleAction!addRole",
			type: "POST", 
			data : params,
			dataType:'html',
			success: function(result) {
				$("#index-body-right-child-one").html(result);
			}
		       
		});
	}
	
}
</script>

<body>
	<div class="wrap">
		<div class="container-fluid">
			<h2 class="text-center" style="margin-right: 52px;">添加角色</h2>
		</div>
		<form class="form-horizontal" role="form" action="RoleAction!addRole" method="post" id="form1">
			<input type="hidden" name="opt" value="add">
			<div class="form-group">
				<label class="col-sm-2 control-label">角色编码</label>
				<div class="col-sm-8">
					<input name="role.role_id" id="role_id" type="text" class="form-control" placeholder="请输入角色编码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">角色名称</label>
				<div class="col-sm-8">
					<input name="role.role_name" id="role_name" type="text" class="form-control" placeholder="请输入角色名称">
				</div>
			</div>
			<s:fielderror name="addMassage" cssStyle="color:red;"></s:fielderror>
			<div class="form-group">
				<label class="col-sm-2 control-label">角色备注</label>
				<div class="col-sm-8">
					<input name="role.role_remark" id="role_remark" type="text" class="form-control" placeholder="角色备注">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">角色权限</label>
				<div class="col-sm-5">
					<div class="zTreeDemoBackground left" style="height: 240px;overflow-y: scroll;">
						<ul id="myTree" class="ztree"></ul>
					</div>
					<s:hidden name="role_select_menuid" id="role_select_menuid"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-8">
					<input type="button" class="btn btn-default" onclick="saveData();" value="提交">
					<input type="reset" class="btn btn-default" value="重置">
				</div>
			</div>
		</form>
	</div>
</body>
</html>
