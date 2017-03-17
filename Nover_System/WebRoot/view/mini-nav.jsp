<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>头部</title>
	<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
    <script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
    <script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">首页</a>
            <a class="navbar-brand" href="#">搜索</a>
        </div>
        <ul class="navbar-nav nav navbar-default navbar-right">
            <li><a href="#"><span class="glyphicon glyphicon-user"></span>注册</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-log-in"></span>登录</a></li>
        </ul>
    </div>
</nav>
</body>
</html>