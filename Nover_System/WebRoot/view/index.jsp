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
<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
<script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
  
<link rel="stylesheet" href="<%=path%>/view/css/index.css">
<title>首页</title>

</head>
<style type="text/css">
    .container{width: 75%;background: #cf3;margin: 0 auto}
    .header{margin-top: 5px;}
    .content{overflow: auto;background-color: #9acfea;margin-top:20px;padding:10px 20px;}
    .content-left{float: left;width: 100%;}
    .content-right{height: 500px;background: #009933;width: 190px;float: right;margin-left: -190px}
    .content-left-child{margin-right: 200px;height: 500px;background: #a94442;}
</style>
<script type="text/javascript">

function showCategory(url) {
    var xmlHttp;

    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlHttp = new XMLHttpRequest();	//创建 XMLHttpRequest对象
    }
    else {
        // code for IE6, IE5
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlHttp.onreadystatechange = function () {
        //onreadystatechange — 当readystate变化时调用后面的方法

        if (xmlHttp.readyState == 4) {
            //xmlHttp.readyState == 4	——	finished downloading response

            if (xmlHttp.status == 200) {
                //xmlHttp.status == 200		——	服务器反馈正常

                document.getElementById("index-body-left-child").innerHTML = xmlHttp.responseText;	//重设页面中id="content"的div里的内容
                executeScript(xmlHttp.responseText);	//执行从服务器返回的页面内容里包含的JavaScript函数
            }
            //错误状态处理
            else if (xmlHttp.status == 404) {
                alert("出错了☹   （错误代码：404 Not Found），……！");
                /* 对404的处理 */
                return;
            }
            else if (xmlHttp.status == 403) {
                alert("出错了☹   （错误代码：403 Forbidden），……");
                /* 对403的处理  */
                return;
            }
            else {
                alert("出错了☹   （错误代码：" + request.status + "），……");
                /* 对出现了其他错误代码所示错误的处理   */
                return;
            }
        }

    }

    //把请求发送到服务器上的指定文件（url指向的文件）进行处理
    xmlHttp.open("GET", url, true);		//true表示异步处理
    xmlHttp.send();
};

function showAtRight(url) {
    var xmlHttp;

    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlHttp = new XMLHttpRequest();	//创建 XMLHttpRequest对象
    }
    else {
        // code for IE6, IE5
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlHttp.onreadystatechange = function () {
        //onreadystatechange — 当readystate变化时调用后面的方法

        if (xmlHttp.readyState == 4) {
            //xmlHttp.readyState == 4	——	finished downloading response

            if (xmlHttp.status == 200) {
                //xmlHttp.status == 200		——	服务器反馈正常

                document.getElementById("index-body-right-child-one").innerHTML = xmlHttp.responseText;	//重设页面中id="content"的div里的内容
                executeScript(xmlHttp.responseText);	//执行从服务器返回的页面内容里包含的JavaScript函数
            }
            //错误状态处理
            else if (xmlHttp.status == 404) {
                alert("出错了☹   （错误代码：404 Not Found），……！");
                /* 对404的处理 */
                return;
            }
            else if (xmlHttp.status == 403) {
                alert("出错了☹   （错误代码：403 Forbidden），……");
                /* 对403的处理  */
                return;
            }
            else {
                alert("出错了☹   （错误代码：" + request.status + "），……");
                /* 对出现了其他错误代码所示错误的处理   */
                return;
            }
        }

    }

    //把请求发送到服务器上的指定文件（url指向的文件）进行处理
    xmlHttp.open("GET", url, true);		//true表示异步处理
    xmlHttp.send();
}

function executeScript(html)
{
    
	var reg = /<script[^>]*>([^\x00]+)$/i;
    //对整段HTML片段按<\/script>拆分
    var htmlBlock = html.split("<\/script>");
    for (var i in htmlBlock) 
    {
        var blocks;//匹配正则表达式的内容数组，blocks[1]就是真正的一段脚本内容，因为前面reg定义我们用了括号进行了捕获分组
        if (blocks = htmlBlock[i].match(reg)) 
        {
            //清除可能存在的注释标记，对于注释结尾-->可以忽略处理，eval一样能正常工作
            var code = blocks[1].replace(/<!--/, '');
            try 
            {
                eval(code) //执行脚本
            } 
            catch (e) 
            {
            }
        }
    }
}
</script>
<body onload="showCategory('<%=path%>/CategoryAction!findList');">
<div class="index-header">
	<s:include value="header.jsp"></s:include>
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
</html>