<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" charset="utf-8" src="<%=path%>/script/ueditor1.3.6/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/script/ueditor1.3.6/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/script/ueditor1.3.6/lang/zh-cn/zh-cn.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/script/My97DatePicker4.7/WdatePicker.js"></script>


<script language="javascript">
function saveData(){
	
}
function init(){
	var configJson = {
			toolbars:[
	            ['fullscreen', 'source', '|', 'undo', 'redo', '|',
	                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	                'directionalityltr', 'directionalityrtl', 'indent', '|',
	                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
	                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	                'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe','insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
	                'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
	                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
	                'print', 'preview', 'searchreplace', 'help', 'drafts']
	        ]
	        ,autoHeightEnabled:false	
	};
	UE.getEditor('notice_content',configJson);
}
</script>
<title>公告添加</title>
</head>
<body onload="init();">
修改公告
<s:form method="post" namespace="/" action="noticeAction!save" id="form1" 
		enctype="multipart/form-data">
	<table width="100%" border="1" style="font-size:12px;">
		<tr>
			<td width="25%">公告标题</td>
			<td><s:textfield name="notice_title"/> </td>
		</tr>
		<tr>
			<td>添加人员</td>
			<td><s:textfield name="notice_adduser"/> </td>
		</tr>
		<tr>
			<td>添加时间</td>
			<td><s:textfield name="notice_addtime" onclick="WdatePicker();" cssClass="Wdate"/></td>
		</tr>
		<tr>
			<td>公告内容</td>
			<td>
			<script id="notice_content" name="notice_content" 
					type="text/plain" 
					style="width:700px;height:200px;">${notice_content}</script>
			</td>
		</tr>
		<tr>
			<td>公告附件</td>
			<td><s:file name="attch" size="40"/></td>
		</tr>		
		<tr>
			<td colspan="2">
				<Input type="submit" value="保存数据"/>
				<Input type="reset" value="重新输入"/>
			</td>
		</tr>								
	</table>
</s:form>
</body>
</html>