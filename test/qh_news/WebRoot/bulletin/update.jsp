<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'update.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

	</head>

	<body>

		<form method="get" action="bulletin/update" name="f">
			<p>
				&nbsp;
			</p>
			<p>
				&nbsp;&nbsp; 标题:
				<input type="text" name="title">
				<br>
			</p>
			<p>
				内容url:
				<input type="text" name="content">
			</p>
			<p>
				&nbsp;&nbsp; 时间:
				<input type="text" name="time">
				&nbsp; (2013-12-30-13-40-01&nbsp; yyyy-MM-dd-HH-mm-ss) 
				<br>
			</p>
			<p>
				允许的:<input type="text" name="filter"> &nbsp; (空为所有[{a:1},{a:2,v:0.1}]) 
			</p>
			<p>
				appId: all
				<input type="checkbox" value="all" name="appid">
				<br>
			</p>
			<p>
				赢三张:
				<input type="checkbox" value="1" name="appid">
				&nbsp;
			</p>
			<p>
				&nbsp;&nbsp;&nbsp; 版本:
				<input type="text" name="yszv">
				(分号隔开 0.1;0.2,不填写表示全部)
			</p>
			<p>
				&nbsp;&nbsp;&nbsp; 渠道:
				<input type="text" name="yszc">
				(a;b)
			</p>
			<p>
				斗牛:
				<input type="checkbox" value="2" name="appid">




			</p>
			<p>
				&nbsp;&nbsp;&nbsp; 版本:
				<input type="text" name="yszv">
				(分号隔开 0.1;0.2,不填写表示全部)
			</p>
			<p>
				&nbsp;&nbsp;&nbsp; 渠道:
				<input type="text" name="yszc">
				(a;b)
			</p>

			<p>
				&nbsp;
			</p>
			<p>
				&nbsp;
			</p>
			<p>
				&nbsp;
				<input type="submit" value="提交">
			</p>
		</form>
		<form method="get" action="bulletin/update">
		
		<input type="submit" value="仅重新读取数据库">
		</form>
	</body>
</html>
