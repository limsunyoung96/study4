<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
 	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html >
<html>
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>test.jsp</title>
</head>
<body>
<%@ include file ="/WEB-INF/inc/top.jsp" %>
<div class = "container">
	<div class="page-header">
		<h2>임선영 - <small>Sunyoung Lim</small></h2>
	</div>
	<div>
		메롱!ㅋㅋㅋㅋㅋ켘<span class="text-blue">${msg}</span>
		<pre>${board}</pre>
	
	</div>
	
</div> <!-- container -->
</body>
</html>