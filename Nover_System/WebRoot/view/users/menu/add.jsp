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

<title>添加菜单</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>

<!-- bootstrap -->
<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=path%>/css/all.css">

<!-- ztree -->
<link rel="stylesheet" href="<%=path%>/script/ztree_3.3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path%>/script/ztree_3.3/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/ztree_3.3/js/jquery.ztree.all-3.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/jquery.json-2.4.js"></script>

<script type="text/javascript"  src="<%=path%>/script/jquery.validate.min.js"></script>
</head>

<script language="javascript">

var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	check: {
		enable: true,
		chkStyle: "radio"
	}
};
var jsonStr = '${requestScope.jsonStr}';
var jsonArrayObj = jQuery.evalJSON(jsonStr);
window.alert(jsonArrayObj);

var zNodes = jsonArrayObj;

$(document).ready(function(){
	$.fn.zTree.init($("#myTree"), setting, zNodes);
	
	// 在键盘按下并释放及提交后验证提交表单
	  $("#signupForm").validate({
		    rules: {
		      //"user.user_name": "required",
		      "menu.menu_id": "required" ,
		      "menu.menu_name": "required" ,
		      "menu.menu_href": "required" ,
		      "menu.grade": "required" ,
		      validatedCode:{
		    	  required: true,
		       	  rangelength:[4,4]
		      }
		    },
		    messages: {
		     // "user.user_name": "请输入您的用户名",
		      "menu.menu_id": "请输入菜单编码" ,
		      "menu.menu_name": "请输入菜单名称" ,
		      "menu.menu_href": "请输入菜单路径" ,
		      "menu.grade": "请输入菜单等级" ,
		      validatedCode:{
		    	  required: "请输入验证码",
		       	  rangelength: "验证码必需由4个字母组成"
		      }
		    }
		});
	
	
	 
});

$.validator.setDefaults({
    submitHandler: function() {
    	var treeObj = $.fn.zTree.getZTreeObj("myTree");
    	var nodeArray = treeObj.getCheckedNodes(true);
    	if (nodeArray == null || nodeArray.length == 0){
    		window.alert("添加菜单时，必须勾选菜单所对应的父节点");
    	}else{
    		var idArray = new Array();
    		for(var i = 0;i<nodeArray.length;i++){
    			var treeNode = nodeArray[i];
    			var id = treeNode.id;
    			idArray.push(id);
    		}
    		var select_parent_id = idArray.join(",");
    		window.alert(select_parent_id);
    		document.getElementById("select_parent_id").value = select_parent_id;
    	
    	
    	
    	var signupForm = document.getElementById("signupForm");
    	signupForm.submit();
    	}
    }
});

/*
function saveData(){
	//获取勾选的角色的树形节点。
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodeArray = treeObj.getCheckedNodes(true);
	if (nodeArray == null || nodeArray.length == 0){
		window.alert("添加菜单时，必须勾选菜单所对应的父节点");
	}else{
		var idArray = new Array();
		for(var i = 0;i<nodeArray.length;i++){
			var treeNode = nodeArray[i];
			var id = treeNode.id;
			idArray.push(id);
		}
		var select_parent_id = idArray.join(",");
		window.alert(select_parent_id);
		document.getElementById("select_parent_id").value = select_parent_id;
		
		var form1 = document.getElementById("signupForm");
		form1.submit();
		
	}
	
}
		*/

</script>
<style type="text/css">
	.error{
		color:red;
	}
</style>
<body>
	<div class="wrap">
		<div class="container-fluid">
			<h2 class="text-center">添加菜单</h2>
		</div>
		<form class="form-horizontal cmxform" role="form" action="MenuAction!add" method="post" id="signupForm">
			<div class="form-group">
				<label class="col-sm-2 control-label">菜单编码</label>
				<div class="col-sm-10">
					<input name="menu.menu_id" type="text" class="form-control" placeholder="请输入菜单编码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">菜单名称</label>
				<div class="col-sm-10">
					<input name="menu.menu_name" type="text" class="form-control" placeholder="请输入角色名称">
				</div>
			</div>
			<s:fielderror name="addMassage" cssStyle="color:red;"></s:fielderror>
			<div class="form-group">
				<label class="col-sm-2 control-label">菜单路径</label>
				<div class="col-sm-10">
					<input name="menu.menu_href" type="text" class="form-control" placeholder="请输入菜单路径">
				</div>
			</div>
			<input name="menu.menu_target" type="hidden" class="form-control" value="rightFrame">
			<div class="form-group">
				<label class="col-sm-2 control-label">所属父节点</label>
				<div class="col-sm-10">
					<!-- 
					<input name="menu.parent_id" type="text" class="form-control" placeholder="请输入父节点编码">
					 -->
					<div class="zTreeDemoBackground left" style="height: 180px;overflow-y: scroll;">
						<ul id="myTree" class="ztree"></ul>
					</div>
					<s:hidden name="select_parent_id" id="select_parent_id"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">等级</label>
				<div class="col-sm-10">
					<input name="menu.grade" type="text" class="form-control" placeholder="请输入等级">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">是否是子节点</label>
				<div class="radio">
					<label>
						<input name="menu.isleaf" type="radio" value="1" checked>是
					</label> 
					<label>
						<input name="menu.isleaf" type="radio" value="0">否
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-default submit" value="提交">
					<input type="reset" class="btn btn-default" value="重置">
				</div>
			</div>
		</form>
	</div>
</body>
</html>
