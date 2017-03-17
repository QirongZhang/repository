<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>身体</title>
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<link rel="stylesheet"
	href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
.buttons_goup {
	width: 50%;
	margin: 50px auto;
	background: #c0c0c0;
	box-shadow: 0 0 10px black;
	height: 100px;
}
</style>
</head>
<body>
	<div class="buttons_goup">
		<div class="text-center">
			<a href="UserManageAction!new_user" class="btn btn-primary btn-lg text-center">继续添加</a>
			<a href="UserManageAction!list" class="btn btn-primary btn-lg">返回列表</a>
		</div>
	</div>
</body>
</html>