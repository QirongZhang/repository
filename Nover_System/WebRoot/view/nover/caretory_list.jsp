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
<title>类别中的</title>
<link rel="stylesheet"
	href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<table class="table">
		<s:iterator status="myStatus" value="categoryList">
			<tr>
				<td>
					<div class="text-center" style="font-size: 20px;">
						<a
							onclick="showAtRight('<%=path%>/StoryAction!findList?category_id=<s:property value='category_id' />')"
							style="text-decoration: none;cursor:hand;"> <s:property value="category_name" />
						</a>
					</div>
				</td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>