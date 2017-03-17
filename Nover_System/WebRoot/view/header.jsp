<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% String path = request.getContextPath(); %>
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
<nav class="navbar  navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="<%=path%>/view/index.jsp" style="font-size: 25px;">首页</a>
        </div>
        <ul class="navbar-nav nav navbar-inverse navbar-right">
            <li><a href="#"><span class="glyphicon glyphicon-user"></span>注册</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-log-in"></span>登录</a></li>
        </ul>
    </div>
</nav>
<nav class="navbar" role="navigation">
    <div class="container-fluid">
       	<div>
       		<img alt="" style="width: 20%;float: left;" src="<%=path %>/view/imgs/读客中文网.png">
       	</div>
        <div>
            <form class="navbar-form text-center" role="search">
                <div class="form-group">
                    <div class="text-center">
                        <input type="text" class="form-control input-lg" placeholder="Search">
                    </div>
                </div>
                <button type="submit" class="btn btn-default">提交</button>
                <div class="btn btn-default navbar-right" style="font-size: 20px;color:black;">
                    <a href="#" style="text-decoration: none"><span class="	glyphicon glyphicon-book"></span>我的书架</a>
                </div>
            </form>
        </div>
    </div>
</nav>

</body>
</html>