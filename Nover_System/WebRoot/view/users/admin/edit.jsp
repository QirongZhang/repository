<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加用户页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<link rel="stylesheet"
	href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/jquery.json-2.4.js"></script>
<script type="text/javascript"
	src="<%=path%>/script/jquery.validate.min.js"></script>
</head>

<script type="text/javascript">
	$.validator.setDefaults({
		submitHandler : function() {
			//var form1 = document.getElementById("form1");
			//form1.submit();
		}
	});

	// 身份证号码验证
	jQuery.validator.addMethod("isIdCardNo", function(value, element) {
		return this.optional(element) || isIdCardNo(value);
	}, "请正确输入您的身份证号码");

	// 电话验证规则
	jQuery.validator.addMethod("phone", function(value, element) {
		var phone = /^0\d{2,3}-\d{7,8}$/;
		return this.optional(element) || (phone.test(value));
	}, "电话格式如：0371-68787027");

	//手机验证规则  
	jQuery.validator.addMethod("mobile", function(value, element) {
		var mobile = /^1[3|4|5|7|8]\d{9}$/;
		return this.optional(element) || (mobile.test(value));
	}, "手机格式不对");

	$().ready(function() {
		// 在键盘按下并释放及提交后验证提交表单
		$("#signupForm").validate({
			rules : {
				"user.user_name" : "required",
				"user.native_place" : {
					required : true
				},
				"user.email" : {
					required : true,
					email : true
				},
				"user.address" : {
					required : true
				}
			},
			messages : {
				"user.user_name" : "请输入您的用户名",
				"user.native_place" : {
					required : "请输入籍贯"
				},
				"user.email" : "请输入一个正确的邮箱",
				"user.address" : {
					required : "请输入地址",
				}
			}
		});
	});

	function saveData() {
		var chk_value = [];
		$('input[name="role_check"]:checked').each(function() {
			chk_value.push($(this).val());
		});
		//alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 

		if (chk_value == null || chk_value.length == 0) {
			window.alert("添加用户时，必须勾选用户所对应的权限");
		} else {
			var idArray = new Array();
			for (var i = 0; i < chk_value.length; i++) {
				var id = chk_value[i];
				idArray.push(id);
			}
			var admin_select_role = idArray.join(",");
			alert("admin_select_role = " + admin_select_role);
			document.getElementById("admin_select_role").value = admin_select_role;

			var form1 = document.getElementById("form1");
			form1.submit();
		}
	}
</script>
<body>
<s:debug></s:debug>
	<div class="admin-add-body" style="background: #F8F8F8;">
		<div class="container-fluid">
			<h2 class="text-center" style="margin-right: 10%;">修改用户</h2>
		</div>
		<form class="form-horizontal" role="form"
			action="<%=path%>/AdminAction!save" method="post" id="form1">
			<!-- 用户账号 -->
			<div class="form-group">
				<label class="col-sm-2 control-label">用户账号</label>
				<div class="col-sm-8">
					<input name="adminBean.admin_id" type="text" class="form-control"
						placeholder="请输入用户账号" value="${adminBean.admin_id }" disabled="true">
				</div>
			</div>
			<!-- 用户账号 -->
			<!-- 用户名-->
			<div class="form-group">
				<label class="col-sm-2 control-label">用户名</label>
				<div class="col-sm-8">
					<input name="adminBean.admin_name" type="text" class="form-control"
						placeholder="请输入用户名" value="${adminBean.admin_name }">
				</div>
			</div>
			<!-- 用户名-->
			<!-- 密码-->
			<div class="form-group">
				<label class="col-sm-2 control-label">用户名</label>
				<div class="col-sm-8">
					<input name="adminBean.password" type="password" class="form-control"
						placeholder="请输入密码" value="${adminBean.password }">
				</div>
			</div>
			<!-- 密码-->
			<!-- 性别 -->
			<div class="form-group">
				<label class="col-sm-2 control-label">性别</label>
				<div class="radio">
					<label> <input name="adminBean.sex" type="radio" name="sex"
						value="1" <s:if test="adminBean.sex == 1">checked</s:if>>男
					</label> 
					<label> <input name="adminBean.sex" type="radio" name="sex"
						value="0" <s:if test="adminBean.sex == 0">checked</s:if>>女
					</label>
				</div>
			</div>
			<!-- 性别 -->
			<!-- 电话号码 -->
			<div class="form-group">
				<label class="col-sm-2 control-label">电话号码</label>
				<div class="col-sm-8">
					<input name="adminBean.phone_num" type="text" class="form-control phone"
						placeholder="请输入电话号码" value="${adminBean.phone_num }">
				</div>
			</div>
			<!-- 电话号码 -->
			<!-- 用户权限  -->
			<s:if test="flag == true">
			<div class="form-group">
				<label class="col-sm-2 control-label">用户权限</label>
				<div class="col-sm-10">
					<table class="table">
						<s:iterator value="roleList" status="myStatus">
							<tr>
								<td><s:property value="#myStatus.count" /></td>
								<td><input type="checkbox" name="role_check"
									value="<s:property value="role_id"/>"></td>
								<td><s:property value="role_name" /></td>
								<td><s:property value="role_remark" /></td>
							</tr>
						</s:iterator>
						<s:hidden name="admin_select_role" id="admin_select_role" />
					</table>
				</div>
			</div>
			</s:if>
			<!-- 用户权限  -->
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class="btn btn-default submit"
						onclick="saveData()" value="提交"> <input type="reset"
						class="btn btn-default" value="重置">
				</div>
			</div>
		</form>
	</div>
</body>
</html>
