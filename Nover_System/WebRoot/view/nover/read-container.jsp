<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>阅读窗口</title>
<link rel="stylesheet" href="<%=path%>/view/css/All.css">
</head>
<body>
<div class="read-body">
	<div class="mini-nav">
		<jsp:include page="../mini-nav.jsp" flush="true"></jsp:include>
	</div>
	<div class="nav-box">
		<div class="left-float-bar-btn-list">
			<ul class="bar-list"></ul>
		</div>
		<div class="right-float-bar-btn-list">
			<ul class="bar-list"></ul>
		</div>
	</div>
	<s:debug></s:debug>
	<div class="read-wrap">
		<div class="book-nav"></div>
		<div class="read-content">
			<div class="story-title" style="width: 100%; float: left;">
				<div>
					<s:property value="chapterBean.chapter_name"/>
				</div>
				<div>
					<ul class="navbar-nav nav navbar-left">
			            <li><a href="#"><span class="glyphicon glyphicon-book"></span><s:property value="storyBean.story_name"/></a></li>
			            <li><a href="#"><span class="glyphicon glyphicon-user"></span><s:property value="storyBean.author"/></a></li>
			        </ul>
					<s:property value="chapterBean.publish_time"/>
				</div>
			</div>
			<div class="story-wrap">
				<pre>
					<p>
						<s:property value="story" />
					</p>
				</pre>
			</div>
		</div>
		<div class="next-page-box">
			<a id="pagePrevBottomBtn" class="prev" 
			href="<%=path %>/ChapterAction!findByID?chapter_id=${chapterBean.chapter_id -1 }">上一章</a>
			<a id="dirBottomBtn" class="dir" 
			href="<%=path %>/ChapterAction!findList?story_id=<s:property value="storyBean.story_id"/>">目录</a>
			<a id="pageNextBottomBtn" class="next" 
			href="<%=path %>/ChapterAction!findByID?chapter_id=${chapterBean.chapter_id +1 }">下一章</a>
		</div>
	</div>
</div>

</body>
</html>