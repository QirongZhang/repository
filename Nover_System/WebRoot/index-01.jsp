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

<title>登录页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet"
	href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
</head>
<script type="text/javascript">
    function change(){

        alert("aaaaaaaaaaa");
        $("#left").css({'width':'0px','margin-right':'0px'});
        $("#right").css({'margin-left':'0px'});
    };
</script>
<style>
    .g-bd1{margin:0 0 10px;}
    .g-sd1{position:relative;float:left;width:190px;margin-right:-190px;height: 500px;background-color: #003eff;}
    .g-mn1{float:right;width:100%;}
    .g-mn1c{margin-left:200px;height: 500px;background-color: #003eff;}
</style>
<body>
<div class="g-bd1 f-cb">
    <div class="g-sd1" id="left">
        <p>左侧定宽</p>
    </div>
    <div class="g-mn1">
        <div class="g-mn1c" id="right">
            <p>右侧自适应</p>
        </div>
    </div>
    <input type="button" id="btn" value="asdas" onclick="change()">
</div>
</body>
</html>
