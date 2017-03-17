<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" charset="utf-8" src="<%=path%>/script/ueditor1.3.6/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/script/ueditor1.3.6/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/script/ueditor1.3.6/lang/zh-cn/zh-cn.js"></script>

<script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
<!-- bootstrap datetimepicker -->
<script type="text/javascript" charset="utf-8" src="<%=path%>/script/bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="<%=path%>/script/bootstrap/datetimepicker/bootstrap-datetimepicker.min.css">
<!-- bootstrap datetimepicker -->

<script type="text/javascript">
function save_Data(){
	var notice_title = $("#notice_title").val();
	var notice_adduser = $("#notice_adduser").val();
	var notice_addtime = $("#notice_addtime").val();
	var attch = $('#attch')[0].files[0];
	
    //var FileController = "<%=path%>/NoticeAction!save";
	var form = new FormData();

	form.append("notice_title",notice_title);
	form.append("notice_adduser",notice_adduser);
	form.append("notice_addtime",notice_addtime);
    form.append("attch", attch);
    /*
	var params = {
			"notice_title":notice_title,
			"notice_adduser":notice_adduser,
			"notice_addtime":notice_addtime,
			"attch":attch
	};	*/

	/*
	var xhr = new XMLHttpRequest();
    xhr.open("post", FileController, true);
    xhr.onload = function () {
        alert("上传完成!");
    };
    xhr.onreadystatechange = function () {
    	if(xhr.readyState === 4 && (xhr.status == 200 ||xhr.status == 304)){
    		 document.getElementById("index-body-right-child-one").innerHTML = xhr.responseText;
    	}
    };
    xhr.send(form);
    */
    alert(attch);
	$.ajax({
		url:"<%=path%>/NoticeAction!save",
		type: "POST", 
		data : form,
		cache: false,
		processData: false,
	    contentType: false,
		dataType:'html',
		success: function(result) {
			$("#index-body-right-child-one").html(result);
		}
	});
};


function init(){
	//UEDITOR_CONFIG.UEDITOR_HOME_URL = '<%=path %>/script/ueditor1.3.6/'; //一定要用这句话，否则你需要去ueditor.config.js修改路径的配置信息
	
    textarea:'notice_content';
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
	                'print', 'preview', 'searchreplace',  'drafts']
	        ]
			,autoHeightEnabled: false
		    ,autoFloatEnabled: true
		    ,initialFrameWidth:700
		    ,initialFrameHeight: 300
	};
	//var editor = new UE.ui.Editor({ initialFrameWidth:700 });
	//editor.render("notice_content",configJson);
	UE.getEditor('notice_content',configJson);
}
</script>
<div class="text-center">
	<h2>添加公告</h2>
</div>
<table style="font-size:18px;width:93%;margin-left:2.5%" class="table">
	<tr>
		<td width="25%">公告标题</td>
		<td>
			<div class="col-sm-8">
				<input name="notice_title" id="notice_title" type="text" class="form-control" placeholder="请输入公告标题">
			</div>
		</td>
	</tr>
	<tr>
		<td>添加人员</td>
		<td>
			<div class="col-sm-8">
				<input name="notice_adduser" id="notice_adduser" type="text" class="form-control" placeholder="请输入添加人员">
			</div>
		</td>
	</tr>
	<tr>
		<td>添加时间</td>
		
		
		<td>
			<div class="col-sm-8">
				<!-- 
				<input name="notice_addtime" value="${notice_addtime}" data-provide="datepicker"
						id="notice_addtime" type="text" class="form-control" placeholder="请输入时间">
						 -->
                <div class="controls input-append date form_datetime"
                     data-date-format="dd MM yyyy - HH:ii p" data-link-field="dtp_input1">
                     <input name="notice_addtime" value="${notice_addtime}"id="notice_addtime" 
                     	type="text" class="form-control" placeholder="请输入时间" readonly>
                    <span class="add-on"><i class="icon-remove"></i></span>
    				<span class="add-on"><i class="icon-th"></i></span>
                </div>
				<input type="hidden" id="dtp_input1" value="" /><br/>
				<script type="text/javascript">
				
					$('.form_datetime').datetimepicker({
				        //language:  'fr',
				        weekStart: 1,
				        todayBtn:  1,
						autoclose: 1,
						todayHighlight: 1,
						startView: 2,
						forceParse: 0,
				        showMeridian: 1
				    });
				</script>     
			</div>
			<!--  onclick="WdatePicker();" cssClass="Wdate" -->
			<!-- 
			<s:textfield name="notice_addtime" value="%{#notice_addtime}"/>
			 -->
		</td>

	</tr>
	<tr>
		<td>公告内容</td>
		<td style="padding-left: 2%">
			<button class="btn btn-primary" data-toggle="modal" data-target="#myModal">添加公告内容</button>
		</td>
	</tr>
	<tr>
		<td>公告附件</td>
		<td>
			<input type="file" name="attch" size="40" id="attch" multiple="multiple">
			<!-- 
			<s:file name="attch" size="40" id="attch"/>
			 -->
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<Input type="button" class="btn btn-default" onclick="save_Data();" value="保存数据"/>
			<Input type="reset" class="btn btn-default" value="重新输入"/>
		</td>
	</tr>								
</table>
<!-- 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 735px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
            </div>
            <div class="modal-body">
            	<textarea name="notice_content" id="notice_content"></textarea>
				<script>init();</script>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 模态框 -->
<s:debug></s:debug>
