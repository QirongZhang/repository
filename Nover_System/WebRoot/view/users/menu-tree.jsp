<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<!-- 
	<link rel="stylesheet" href="<%=path %>/script/ztree_3.3/css/demo.css" type="text/css">
	 -->
	<link rel="stylesheet" href="<%=path %>/script/ztree_3.3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=path %>/script/ztree_3.3/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=path %>/script/ztree_3.3/js/jquery.ztree.all-3.3.min.js"></script>
	<script type="text/javascript" src="<%=path%>/script/jquery.json-2.4.js"></script>
	<SCRIPT type="text/javascript">
		var curMenu = null, zTree_Menu = null;
		var setting = {
			view: {
				showLine: false,
				selectedMulti: false,
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onNodeCreated: this.onNodeCreated,
				beforeClick: this.beforeClick,
				onClick: this.zTreeOnClick
			}
		};
		
		var jsonString = '${jsonString }';
		//window.alert(jsonString);
		var jsonArrayObj = JSON.parse(jsonString);
		var zNodes = jsonArrayObj;
		
		var urlMap = {};
		jsonArrayObj.forEach(function(item){
			if(item.menu_href){
				urlMap[item.id] = item.menu_href;
			}
		});
		loadPageViaHash();
		
		function beforeClick(treeId, node) {
			if (node.isParent) {
				if (node.level === 0) {
					var pNode = curMenu;
					while (pNode && pNode.level !==0) {
						pNode = pNode.getParentNode();
					}
					if (pNode !== node) {
						var a = $("#" + pNode.tId + "_a");
						a.removeClass("cur");
						zTree_Menu.expandNode(pNode, false);
					}
					a = $("#" + node.tId + "_a");
					a.addClass("cur");

					var isOpen = false;
					for (var i=0,l=node.children.length; i<l; i++) {
						if(node.children[i].open) {
							isOpen = true;
							break;
						}
					}
					if (isOpen) {
						zTree_Menu.expandNode(node, true);
						curMenu = node;
					} else {
						zTree_Menu.expandNode(node.children[0].isParent?node:node, true);
						curMenu = node.children[0];
					}
				} else {
					zTree_Menu.expandNode(node);
				}
			}
			return !node.isParent;
		}
		
		//function zTreeOnClick(event, treeId, treeNode) {
		//    var isLeaf = treeNode.isLeaf;
		//    if (isLeaf == "1"){
		//   		var menu_href = treeNode.menu_href;
		//   		menu_href = '<%=path%>' + menu_href;
		//   		window.alert(menu_href);
		//   		showAtRight(menu_href);
		//    }
		//};
		
		function zTreeOnClick(event, treeId, treeNode) {
			var isLeaf = treeNode.isLeaf;
			if(isLeaf == "1"){
				window.location.hash = treeNode.id;
			}
		}
		
		function showAtRight(url) {
			$("#index-body-right-child-one").load(url);	
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
			curMenu = zTree_Menu.getNodes()[0].children[0];
			zTree_Menu.selectNode(curMenu);
			var a = $("#" + zTree_Menu.getNodes()[0].tId + "_a");
			a.addClass("cur");
		});
	</SCRIPT>
	<style type="text/css">
.ztree li {line-height: 20px;}
.ztree * { font-family: Verdana,Arial,Helvetica,AppleGothic,sans-serif;font-size: 18px;}
.ztree li a.level0 {width:100%;height: 40px; text-align: center; display:block; background-color: #333333; border:1px silver solid;}
.ztree li a.level0.cur {background-color: #447f91; }
.ztree li a.level0 span {display: block; color: white; padding-top:3px; font-size:18px; font-weight: bold;word-spacing: 2px;margin-top: 8px;}
.ztree li a.level0 span.button {	float:right; margin-left: 10px; visibility: visible;display:none;}
.ztree li span.button.switch.level0 {display:none;}
ul.ztree {height: 450px;margin-top: 0;}
	</style>

<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree" style="width: 250px;"></ul>
	</div>
</div>