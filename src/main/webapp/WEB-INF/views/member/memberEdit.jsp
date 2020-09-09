<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/inc/header.jsp" %>
	<title>memberEdit.jsp </title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp" %>
 <div class="container" >	
	<h3>회원 정보 수정 </h3>		
	<form:form action="memberModify.wow" modelAttribute="mem">
		<form:hidden path="memId"/>
	<table class="table table-striped table-bordered">
		<tbody>
			<tr>
				<th>아이디</th>
				<td>${mem.memId}</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><%-- <input type="password" name="memPass" class="form-control input-sm"
				     pattern="\w{4,}" title="알파벳과 숫자로 4글자 이상 입력"  > --%>
				   <form:password path="memPass" cssClass="form-control input-sm"/>
					<form:errors path="memPass"/>
				</td>
			</tr>
			<tr>
				<th>회원명</th>
				<td><%-- <input type="text" name="memName" class="form-control input-sm" 
				          value="${mem.memName}"
				          required="required" pattern="[가-힣]{2,}" title="한글로 2글자 이상 입력" > --%>
				   <form:input path="memName" cssClass="form-control input-sm"/>
					<form:errors path="memName"/>      
				</td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><%-- <input type="text" name="memZip"  class="form-control input-sm" value='${mem.memZip}'   --%>
					<form:input path="memZip" cssClass="form-control input-sm"/>
					<form:errors path="memZip"/>      
				</td>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td><%-- <input type="text" name="memAdd1" class="form-control input-sm" value="${mem.memAdd1}" >
					  <input type="text" name="memAdd2" class="form-control input-sm" value="${mem.memAdd2}" > --%>
					<form:input path="memAdd1" cssClass="form-control input-sm"/>
					<form:errors path="memAdd1"/> 
					<form:input path="memAdd2" cssClass="form-control input-sm"/>
					<form:errors path="memAdd2"/> 
				</td>
			</tr>			
			<tr>
				<th>생일</th>
				<td><input type="date" name="memBir" class="form-control input-sm" value="${mem.memBir}"></td>
			</tr>
			<tr>
				<th>메일</th>
				<td><%-- <input type="email" name="memMail" class="form-control input-sm" required="required" value="${mem.memMail}" > --%>
					<form:input path="memMail" cssClass="form-control input-sm"/>
					<form:errors path="memMail"/> 
				</td>
			</tr>
			<tr>
				<th>핸드폰</th>
				<td><input type="tel" name="memHp" class="form-control input-sm" value="${mem.memHp}"></td>
			</tr>
			<tr>
				<th>직업</th>
				<td>
					<%-- <select name="memJob" class="form-control input-sm">
						<option value="">-- 직업 선택 --</option>
						<c:forEach items="${jobList}" var="code">
							<option value="${code.commCd}" ${code.commCd eq mem.memJob ? "selected='selected'": "" } >${code.commNm}</option>
						</c:forEach>
					</select>	 --%>	
					<form:select path="memJob" cssClass="form-control input-sm">
						<option value="" >--- 선택하세요 ---</option>
						<form:options items="${jobList}" itemLabel="commNm" itemValue="commCd" />
					</form:select>
					<form:errors path="memJob"/> 		
				</td>
			</tr>
			<tr>
				<th>취미</th>
				<td>
					<%-- <select name="memLike" class="form-control input-sm">
						<option value="">-- 취미 선택 --</option>
						<c:forEach items="${hobbyList}" var="code">
							<option value="${code.commCd}" ${code.commCd eq mem.memLike ? "selected='selected'": "" }>${code.commNm}</option>
						</c:forEach>
					</select>	 --%>	
					<form:select path="memLike" cssClass="form-control input-sm">
						<option value="" >--- 선택하세요 ---</option>
						<form:options items="${hobbyList}" itemLabel="commNm" itemValue="commCd" />
					</form:select>
					<form:errors path="memLike"/> 	
				</td>
			</tr>
			<tr>
					<th>마일리지</th>
					<td>${mem.memMileage}</td>
			</tr>		
			<tr>
					<th>탈퇴여부</th>
					<td>${mem.memDelete}</td>
				</tr>	
			<tr>
				<td colspan="2">
					<a href="memberList.wow" class="btn btn-info btn-sm" >
						<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
						&nbsp; 목록 
					</a>
					
					<button type="submit"  formaction="memberDelete.wow" class="btn btn-sm btn-danger"> 
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                &nbsp;&nbsp;삭제
              </button>
					
					<button type="submit" class="btn btn-primary"  >
					  <span class="glyphicon glyphicon-heart-empty" aria-hidden="true"></span>
						&nbsp; 저장  
					</button>
				</td>
			</tr>
		</tbody>	
	</table>	
	</form:form>
</div>

</body>
</html>