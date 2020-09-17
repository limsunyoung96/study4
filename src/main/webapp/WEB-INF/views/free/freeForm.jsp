<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/inc/header.jsp"%>
<title>글 등록</title>

<script type="text/javascript">

	$(document).ready(function() {
		// 파일 추가버튼 클릭 이벤트
		$('#id_btn_new_file').click(function(){
			$('.file_area').append('<div class="form-inline">'
				+ '<input type="file" name="boFiles" class="form-control">'
				+ ' <button type="button" class="btn_delete btn btn-sm">삭제</button>'
				+ '</div>');
		}); // #id_btn_new_file.click
		
		//파일 삭제버튼 클릭 이벤트 (동적으로 추가된 객체의 이벤트 사용시 on...) 
		$('.file_area').on('click', '.btn_delete', function() {
			$(this).closest('div').remove();
		}); // .btn_delete_click
		
		$('.btn_delete').click(function(){
			$(this).closest('div').remove();
		});
	}); // document.ready
</script>

</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp" %>
<div class="container">
	<div class="page-header">
		<h3>글 등록</h3>
	</div>
	<div class="row" >
	<%-- <form action="freeRegist.wow" method="post"> --%>
	<form:form action="freeRegist.wow" modelAttribute="boardVO"
				 method="post" enctype="multipart/form-data">
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
			<form:select path="boCategory" cssClass="form-control input-sm">
				<option value="" >--- 선택하세요 ---</option>
					<form:options items="${categoryList}" itemLabel="commNm" itemValue="commCd" />
			</form:select>
			<form:errors path="boCategory"/>
				<%-- <select name="boCategory" class="form-control input-sm" required="required">
				<c:forEach items="${categoryList}" var="code">
				<option value="${code.commCd}">${code.commNm}</option>
				</c:forEach>
				</select> --%>
				<form:errors path="boCategory" />
			</td>
		</tr>
		<tr>
			<th>IP</th>
			<td>${pageContext.request.remoteAddr}</td>
		</tr>
		<tr>
			<th>첨부파일
				<button type="button" id="id_btn_new_file">추가</button>
			</th>
			<td class="file_area">
				<div class="form-inline">
					<input type="file" name="boFiles" class="form-control">
					<button type="button" class="btn_delete btn btn-sm">
						삭제</button>
				</div>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<%-- <textarea rows="3" name="boContent" class="form-control"></textarea> --%>
				<form:textarea path="boContent" cssClass="form-control" />
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


