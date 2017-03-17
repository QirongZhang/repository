<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>身体</title>
    <script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
	<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
    <script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
    <style type="text/css">
        .container{width: 90%;background: #FFFFFF;margin: 0 auto}
    </style>
</head>
<body>
<s:debug></s:debug>
<div class="container">
    <div class="header">
    	<jsp:include page="header.jsp"></jsp:include>
    </div>
    <div class="content">
    	<div class="chap_title">
    	
    	</div>
    	<div class="table-responsive ">
    		<table class="table table-hover">
    			<s:iterator value="chapterList" status="chapter">
    				<tr>
    					<td><s:property value="#chapter.chapter_name"/></td>
    				</tr>
    			</s:iterator>
    		</table>
    	</div>
    </div>
    <div class="footer"></div>
</div>
</body>
</html>