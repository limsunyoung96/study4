<%@page import="com.study.exception.BizPasswordNotMatchedException"%>
<%@page import="com.study.free.service.FreeBoardServiceImpl"%>
<%@page import="com.study.free.service.IFreeBoardService"%>
<%@page import="com.study.exception.BizNotFoundException"%>
<%@page import="com.study.exception.BizNotEffectedException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/inc/header.jsp" %>
	<title>freeDelete.jsp </title>
</head>
<body>
 <div class="container">	
	<h3>글 삭제</h3>
	<jsp:useBean id="board" class="com.study.free.vo.FreeBoardVO" />
	<jsp:setProperty property="*" name="board" />
	<% 
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	try {
		freeBoardService.removeBoard(board);
	%>
	<div class="alert alert-warning">
		<h4>삭제 완료.</h4>
		정상적으로 글이 삭제되었습니다.
	</div>
	<%
	} catch (BizNotEffectedException ex) {
		%>
		<div class="alert alert-warning">
			<h4>삭제가 완료되지 않았습니다.</h4>
		</div>
		<%
	} catch (BizNotFoundException ex) {
		%>
		<div class="alert alert-warning">
			<h4>해당 글이 존재하지 않습니다.</h4>
			올바르게 접근해주세요.
		</div>
		<%
	} catch (BizPasswordNotMatchedException ex) {
		%>
		<div class="alert alert-warning">
			<h4>비밀번호가 일치하지 않습니다.</h4>
			올바른 비밀번호를 입력해 주세요.
		</div>
		<%
	}
	%>
	<a href="freeList.jsp" class="btn btn-info btn-sm">
	<span class="glyphicon glyphicon-list" aria-hidden="true"></span>&nbsp;&nbsp;목록</a>
</body>
</html>