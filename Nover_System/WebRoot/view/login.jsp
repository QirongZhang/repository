<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>登录页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<link rel="stylesheet"
	href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/script/jquery.validate.min.js"></script>
<link href="<%=path %>/script/jquery.slideunlock/slide-unlock.css" rel="stylesheet">
<script src="<%=path %>/script/jquery.slideunlock/jquery.slideunlock.js"></script> 
<script type="text/javascript">

	$.validator.setDefaults({
		submitHandler : function() {
			var signupForm = document.getElementById("signupForm");
			signupForm.submit();
		}
	});

	$().ready(function() {
		// 在键盘按下并释放及提交后验证提交表单
		$("#signupForm").validate({
			rules : {
				//"user.user_name": "required",
				"user.password" : {
					required : true,
					rangelength : [ 3, 15 ]
				},
				validatedCode : {
					required : true,
					rangelength : [ 4, 4 ]
				}
			},
			messages : {
				// "user.user_name": "请输入您的用户名",
				"user.password" : {
					required : "请输入密码",
					rangelength : "密码必需由3-15个字母组成"
				},
				validatedCode : {
					required : "请输入验证码",
					rangelength : "密码必需由4个字母组成"
				}

			}
		});
	});
</script>
<style type="text/css">
.error {
	color: red;
}

.login-wrap {
	width: 50%;
	margin: 100px auto;
	background: #F8F8F8;
	box-shadow: 0 0 20px #C0C0C0;
	padding: 20px;
}

body {
	background: #292933;
}

.login-title {
	margin: 20px auto;
}

#slider {
		margin: 0 auto;
	}
</style>
</head>

<body>
	<s:debug></s:debug>
	<div class="login-wrap">
		<div class="login-title">
			<h2 class="text-center">登录页面</h2>
		</div>
		<form class="form-horizontal" role="form" action="LoginAction"
			id="signupForm">
			<div class="form-group">
				<label class="col-sm-2 control-label">用户名</label>
				<div class="col-sm-9">
					<input name="user_name" type="text"
						class="form-control username" placeholder="请输入用户名">
				</div>
			</div>
			<s:fielderror fieldName="user.user_name"></s:fielderror>

			<div class="form-group">
				<label class="col-sm-2 control-label">密码</label>
				<div class="col-sm-9">
					<input name="password" type="password" class="form-control"
						placeholder="请输入密码">
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label> <input type="checkbox">请记住我的密码
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="radio">
						<label><input type="radio" name="user" checked="checked" value="1">读者</label> 
						<label><input type="radio" name="user" value="2">作者</label>
						<label><input type="radio" name="user" value="3">管理员</label>
					</div>
				</div>
			</div>
			<!-- 验证码 -->
			<div class="text-center">
				<div id="slider">
				<div id="slider_bg"></div>
					<span id="label">>></span> <span id="labelTip">拖动滑块验证登录</span>
				</div>
				
				  <script>
				    $(function () {
				        var slider = new SliderUnlock("#slider",{
								successLabelTip : "欢迎访问小说阅读系统"	
							},function(){
								alert("验证成功,即将跳转至小说阅读系统首页");
				            	//window.location.href="<%=path %>/LoginAction";
				        	});
				        slider.init();
				    })
				</script> 
			</div>
			<!-- 验证码 -->
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default submit">登录</button>
				</div>
			</div>
			
		</form>
	</div>

</body>
</html>
