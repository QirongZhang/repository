<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>身体</title>
	<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
    <script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
    <script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="chapter-list-header">
	<s:include value="../header.jsp"></s:include>
</div>
<div>
	<div class="btn-group" id ="right">
		<h2>章节名</h2>
		<hr style="width: 90%;"/>
		<ul class="nav nav-list" style="font-size: 18px;">
			<s:iterator value="chapterList">
				<li class="col-md-4 text-center" style="border:2px; ">
					<a href="<%=path%>/ChapterAction!findByID?chapter_id=<s:property value='chapter_id'/>">
						<s:property value="chapter_name"/>
					</a>
				</li>
			</s:iterator>
		</ul>
	</div>
</div>
</body>
</html>