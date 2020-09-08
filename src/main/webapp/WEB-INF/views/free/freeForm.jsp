<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/inc/header.jsp"%>
<title>글 등록</title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp" %>
<div class="container">
	<div class="page-header">
		<h3>글 등록</h3>
	</div>
	<div class="row" >
	<%-- <form action="freeRegist.wow" method="post"> --%>
	<form:form action="freeRegist.wow" modelAttribute="boardVO">
	<table class="table table-striped table-bordered">
		<colgroup>
			<col width="20%" />
			<col/>
		</colgroup>
		<tr>
			<th>제목</th>
			<td><!-- <input type="text" name="boTitle" value="" class="form-control input-sm" > -->
				<form:input path="boTitle" cssClass="form-control input-sm"/>
				<form:errors path="boTitle" />
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><!-- <input type="text" name="boWriter" value=""  class="form-control input-sm" > -->
				<form:input path="boWriter" cssClass="form-control input-sm"/>
				<form:errors path="boWriter" />
			</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><!-- <input type="password" name="boPass" value="" class="form-control input-sm"> -->
				<form:password path="boPass" cssClass="form-control input-sm"/>
				<span >수정 또는 삭제시에 필요합니다.</span>
				<form:errors path="boPass" />
			</td>
		</tr>
		<tr>
			<th>분류</th>
			<td>
				<select name="boCategory" class="form-control input-sm" required="required">
				<c:forEach items="${categoryList}" var="code">
				<option value="${code.commCd}">${code.commNm}</option>
				</c:forEach>
				</select>
				<form:errors path="boCategory" />
			</td>
		</tr>
		<tr>
			<th>IP</th>
			<td>${pageContext.request.remoteAddr}</td>
		</tr>			
		<tr>
			<th>내용</th>
			<td><%-- <textarea rows="3" name="boContent" class="form-control"></textarea> --%>
			<form:textarea path="boContent" cssClass="form-control"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
					<div class="pull-left">
						<a href="freeList.wow" class="btn btn-sm btn-default">목록으로</a>
					</div>
					<div class="pull-right">
						<button type="submit" class="btn btn-sm btn-primary">저장하기</button>
					</div>
			</td>
		</tr>
	</table>
	</form:form>
	</div>
	
</div>
</body>
</html>


