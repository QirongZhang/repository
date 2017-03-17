<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>jQuery拖拽滑动验证码插件 slideunlock.js - 素材家园 - www.sucaijiayuan.com</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE-edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="<%=path %>/script/jquery.slideunlock/slide-unlock.css" rel="stylesheet">
<style>
	#slider {
		margin: 0 auto;
	}
</style>
</head>
<body>
<div id="slider">
	<div id="slider_bg"></div>
	<span id="label">>></span> <span id="labelTip">拖动滑块验证登录</span>
</div>
    
  <script src="<%=path %>/script/jquery-3.1.1.min.js"></script> 
  <script src="<%=path %>/script/jquery.slideunlock/jquery.slideunlock.js"></script> 
  <script>
    $(function () {
        var slider = new SliderUnlock("#slider",{
				successLabelTip : "欢迎访问小说阅读系统"	
			},function(){
				alert("验证成功,即将跳转至小说阅读系统首页");
            	window.location.href="#"
        	});
        slider.init();
    })
</script> 
</body>
</html>