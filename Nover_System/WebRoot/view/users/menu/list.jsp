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
<title>菜单管理</title>
<!-- treetable -->
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/jqtreetable/Treetable_files/jqtreetable.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/script/jqtreetable/Treetable_files/jqtreetable.css" />
<!-- bootstrap -->
<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/jquery.json-2.4.js"></script>
</head>
<script type="text/javascript" charset="utf-8">
	function deleteMenu(menu_id){
		var flag  = window.confirm("确定删除角色的数据");
		if (flag == true){
			var deleteURL = "<%=path%>/MenuAction!del?menu_id="+menu_id+"";
			window.location.href = deleteURL;
		}
	}
	
	$(function(){
		// 这里要说明一下，虽然很简单
		// 比如现在有5行，其中第二行和第一行平级，第三行是第二行的下级行，第四行是第三行的下级
		// 第五行和第三行平级，也就是说是第二行的下级行，应该如下所示：
		//      1
		//      2
		//         3
		//            4
		//         5
		// 这样的话，map应该是这样的
		// 行号：1					 2				 3                 4				 5
		// map： 0    ,              0,              2,                3,                2
		// 说明：1行的上级的行为0    2行的上级也为0  3行的上级是第2行  4行的上级是第3行  第5行的上级是第2行
		// 这里有20行，原理是一样的
		
		var jsonStr = '${requestScope.jsonStr}';
		var jsonArrayObj = jQuery.evalJSON(jsonStr);
		var map = jsonArrayObj;
		
		//声明参数选项
		var options = {
					openImg: "<%=path%>/script/jqtreetable/images/TreeTable/tv-collapsable.gif", 
					shutImg: "<%=path%>/script/jqtreetable/images/TreeTable/tv-expandable.gif", 
					leafImg: "<%=path%>/script/jqtreetable/images/TreeTable/tv-item.gif", 
					lastOpenImg: "<%=path%>/script/jqtreetable/images/TreeTable/tv-collapsable-last.gif", 
					lastShutImg: "<%=path%>/script/jqtreetable/images/TreeTable/tv-expandable-last.gif", 
					lastLeafImg: "<%=path%>/script/jqtreetable/images/TreeTable/tv-item-last.gif", 
					vertLineImg: "<%=path%>/script/jqtreetable/images/TreeTable/vertline.gif", 
					blankImg: "<%=path%>/script/jqtreetable/images/TreeTable/blank.gif", 
					collapse: false, column: 0, 
					striped: true, highlight: true,  
					state:false
		};
		/* 对于options中的各个参数，copy原作者的解释，有权威，不翻译了
		openImg: Relative url of the image to use for an expanded parent node.
		shutImg: Relative url of the image to use for a collapsed parent node.
		leafImg: Relative url of the image to use for a child node.
		lastOpenImg: Relative url of the image to use for the last expanded parent node. Required if using lines. If not set this to the same as openImg.
		lastShutImg: Relative url of the image to use for the last collapsed parent node. Required if using lines. If not set this to the same as shutImg.
		lastLeafImg: Relative url of the image to use for the last child node. Required if using lines. If not set this to the same as leafImg.
		vertLineImg: Relative url of the image to use for the vertical lines. If not using lines, set this to the same as blankImg.
		blankImg: Relative url of the image to use for the filler that creates the indenting.
		collapse: An array of parent row numbers whose children are collapsed initially. Must be an array even if you only want one parent collapsed
		column: A zero based index of the column you want to show the treetable effect in, i.e. first column is 0.
		striped: Boolean indicating whether you want a striped effect. Set the colour in the css with an even class selector.
		highlight: Boolean indicating whether you want a highlighting effect when hovering over the rows. Set the colour in the css with an over class selector.
		state: Boolean indicating whether you want the collapsed state of each parent to be set in a cookie. Set this to false if there is likely to be a change in the relationshiop between the child nodes and their parents.
		*/
	   if(map!=null&&map.length>0)
		{
		  //根据参数显示树
		  // 注意treet1这个名字是 下文中 tbody 的名字
		  $("#treet1").jqTreeTable(map, options);
		}
	  }
);
</script>
<body>
<s:debug></s:debug>
	<div class="btn-group">
		<a href="MenuAction!add" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span>增加</a>
	</div>
	<table class="treetable table" style="width:98%">
		<thead>
			<tr>
				<th width="20%">菜单编码</th>
				<th width="20%">菜单名称</th>
				<th width="25%">菜单链接</th>
				<th>级别</th>
				<th>是否细级</th>
			</tr>
		</thead>
		<tbody id="treet1">
			<s:iterator value="#menuList">
				<tr>
					<td><s:property value="menu_id"/> </td>
					<td><s:property value="menu_name" escape="false"/></td>
					<td><s:property value="menu_href"/></td>
					<td><s:property value="grade"/></td>
					<td><s:property value="tempLeaf"/></td>	
					<td>
						<a href="<%=path%>/MenuAction!edit?menu_id=<s:property value="menu_id"/>">修改</a>
						<a href="javascript:void(0);" onclick="deleteMenu('<s:property value="menu_id"/>');">删除</a>
					</td>	
				</tr>
			</s:iterator>
			<s:fielderror name="deleteMessage" cssStyle="color:red;"></s:fielderror>
		</tbody>
	</table>
</body>
</html>
