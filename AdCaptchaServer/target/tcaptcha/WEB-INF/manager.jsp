<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Key Management</title>
<script type="text/javascript" src="script/mootools-1.2.3-core-yc.js"></script>
<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div id="page">
<div id="header">
		<h1>Times Captcha Server</h1>
	</div>
	<div id="pagebody">
		<table id="manager">
			<tr>
				<th>Application Name</th>
				<th>Application Key</th>
				<th>Del</th>
			</tr>
			<c:forEach items="${apikeys}" var="apikey">
				<tr>
					<td>${apikey.appName}</td>
					<td>${apikey.apikey}</td>
					<td><a href="manager?action=del&id=${apikey.id}" onclick="return confirm('Sure?')">X</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="footer">
	    <p>A highly configurable and scalable captcha service<br/>
	    Created and maintained by <em>Sun Ning / SNDA</em>, 2009 <a href="http://bitbucket.org/sunng/tcaptcha">http://bitbucket.org/sunng/tcaptcha</a><br/>
	    with Java, Servlet, Maven, Guice, EhCache.<br/></p>
	    <p>Clone the respository with: <br/><em>$ hg clone https://sunng@bitbucket.org/sunng/tcaptcha/</em></p>
	</div>
</div>
</body>
</html>