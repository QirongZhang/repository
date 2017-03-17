<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加章节</title>
<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>

<link rel="stylesheet" href="<%=path%>/view/css/All.css">

<script type="text/javascript" charset="utf-8"
	src="<%=path%>/script/ueditor1.3.6/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=path%>/script/ueditor1.3.6/ueditor.all.min.js">
	
</script>
<script type="text/javascript" charset="utf-8"
	src="<%=path%>/script/ueditor1.3.6/lang/zh-cn/zh-cn.js"></script>

<link rel="stylesheet"
	href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
	function getPlainTxt() {
		var arr = [];
		arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
		arr.push("内容为：");
		arr.push(UE.getEditor('editor').getPlainTxt());
		alert(arr.join('\n'))
	}
	
	var saveDta = function() {
		var from1 = $("#form1");
		var plainTXT = getPlainTxt();
		form1.submit();
	}
	
	var init = function() {
		var configJson = {
			toolbars : ['fullscreen', 'source', '|', 'undo', 'redo', '|',
		                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
		                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
		                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
		                'directionalityltr', 'directionalityrtl', 'indent', '|',
		                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
		                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
		                'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe','insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
		                'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
		                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
		                'print', 'preview', 'searchreplace', 'help', 'drafts' ],
			autoHeightEnabled : false
		};
		UE.getEditor('edit_story', configJson);
	}
</script>
<body onload="init();" id = "form1">
<form action="">
	<div class="form-group" style="margin-bottom: 20px">
		<label class="col-sm-2 control-label">章节名</label>
		<div class="col-sm-10">
			<input name="user.password" type="password" class="form-control"
				placeholder="请输入章节名">
		</div>
	</div>

	<div>
		<table>
			<tr>
				<td><script id="edit_story" name="edit_story"
		type="text/plain" style="height:400px"></script></td>
			</tr>
		</table>
	</div>

	<div class="form-group" style="margin-top: 20px;">
		<label class="col-sm-2 control-label">上传章节</label>
		<div class="col-sm-10">
			<s:file ></s:file>
		</div>
	</div>
	<div class="form-group" style="margin-top: 20px;">
		<div class="col-sm-10">
			<input type="button" onclick="" value="提交">
			<input type="reset" value="重置">
		</div>
	</div>
</form>
</body>
</html>