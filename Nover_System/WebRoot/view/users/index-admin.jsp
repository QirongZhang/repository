<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>

<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<title>首页</title>

</head>
<style type="text/css">
.index-body { margin: 0 0 10px;}
.index-body-left {position: relative;float: left;width: 250px;margin-right: -250px;}
.index-body-right {float: right;width: 100%;}
.index-body-right-child {margin-left: 270px;}
</style>
<script type="text/javascript">

function showZtree(url) {
	$.ajax({
		url:url + '?admin_id=${session.admin.admin_id}',
		type:'GET',
		dataType:'html',
		success:function(result){
	        $("#index-body-left-child").html(result);
	    }
	});
};

function showAtRight(url) {
	$("#index-body-right-child-one").load(url);	
}

function loadPageViaHash(){
	var hash = window.location.hash.slice(1);
	
	if(hash in urlMap){
		showAtRight("<%=path%>" + urlMap[hash]);
	}
}
window.addEventListener('hashchange', loadPageViaHash, false);

</script>
<body onload="showZtree('<%=path%>/MainAction');">
<div class="index-header">
	<s:include value="header-admin.jsp"></s:include>
</div>
<div class="index-body">
    <div class="index-body-left" id = "index-body-left">
    	<div class="index-body-left-child" id="index-body-left-child"></div>
    </div>
    <div class="index-body-right">
        <div class="index-body-right-child">
        	<div id="index-body-right-child-one">
        		<div style="width: 32%;height: 150px;background-color: red;float: left;margin-left: 10px"></div>
        		<div style="width: 32%;height: 150px;background-color: red;float: left;margin-left: 10px"></div>
        		<div style="width: 32%;height: 150px;background-color: red;float: left;margin-left: 10px"></div>
        	</div>
        </div>
    </div>
</div>
<div class="index-bottom"></div>
</body>
<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
</html>