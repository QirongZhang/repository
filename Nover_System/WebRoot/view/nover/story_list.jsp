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
<title>小说列表</title>
<link rel="stylesheet"
	href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>
	<s:debug></s:debug>
	<s:iterator status="myStatus" value="storyList">
		<div
			style="width: 32%;height: 150px;background-color: #c0c0c0;float: left;margin: 10px 0 0 10px;">
			<a
				href="<%=path%>/ChapterAction!findList?story_id=<s:property value='story_id' />"
				style="text-decoration: none;cursor:hand;">
				<div
					style="width: 25%;height: 130px;background-color: black;float: left;margin: 10px 5px">
					<img alt="" src="">
				</div>
				<div
					style="width: 68%;height: 130px;background-color: white;margin: 10px 5px;float: left;">
					<div class="text-left lead"
						style="font-family: 华文楷体;margin-top: 5px;">
						<s:property value="story_name" />
					</div>
					<div>
						<s:property value='cover_url' />
					</div>
					<div style="">
						<span class="glyphicon glyphicon-user"></span>
						<s:property value='author' />
					</div>
				</div>
			</a>
		</div>
	</s:iterator>
	<button onclick="tiaozhuan()">按钮</button>
</body>
</html>