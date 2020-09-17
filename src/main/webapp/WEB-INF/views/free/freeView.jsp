<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>freeView.jsp</title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<div class="page-header">
			<h3>
				자유게시판 - <small>상세보기</small>
			</h3>
		</div>

		<table class="table table-striped table-bordered">
			<tbody>
				<tr>
					<th>글번호</th>
					<td>${boardVo.boNo}</td>
				</tr>
				<tr>
					<th>글제목</th>
					<td>${boardVo.boTitle}</td>
				</tr>
				<tr>
					<th>글분류</th>
					<td>${boardVo.boCategoryNm}</td>
				</tr>
				<tr>
					<th>작성자명</th>
					<td>${boardVo.boWriter}</td>
				</tr>
				<!-- 비밀번호는 보여주지 않음  -->

				<tr>
					<th>첨부파일</th>
					<td><c:forEach var="f" items="${boardVo.attaches}" varStatus="st">
							<div>
								# 파일 ${st.count} 
								<a href="<c:url value='/attach/download/${f.atchNo}' />"target="_blank">
									<span class="glyphicon glyphicon-save" aria-hidden="true"/> 
									${f.atchOriginalName}
								</a>
								 Size : ${f.atchFancySize} 
								 Down : ${f.atchDownHit}
							</div>
						</c:forEach></td>
				</tr>

				<tr>
					<th>내용</th>
					<td><pre>${boardVo.boContent}</pre></td>
				</tr>
				<tr>
					<th>등록자 IP</th>
					<td>${boardVo.boIp}</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${boardVo.boHit}</td>
				</tr>
				<tr>
					<th>등록일자</th>
					<td>${boardVo.boRegDate}</td>
				</tr>
				<tr>
					<th>수정일자</th>
					<td>${boardVo.boModDate}</td>
				</tr>
				<tr>
					<th>삭제여부</th>
					<td>${boardVo.boDelYn}</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="pull-left">
							<a href="freeList.wow" class="btn btn-default btn-sm"> <span
								class="glyphicon glyphicon-list" aria-hidden="true"></span>
								&nbsp;&nbsp;목록
							</a>
						</div>
						<div class="pull-right">
							<a href="freeEdit.wow?boNo=${boardVo.boNo}"
								class="btn btn-success btn-sm"> <span
								class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
								&nbsp;&nbsp;수정
							</a>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- container -->

	<div class="container">


		<!-- // START : 댓글 등록 영역  -->
		<div class="panel panel-default">
			<div class="panel-body form-horizontal">
				<form name="frm_reply" action="<c:url value='/reply/replyRegist' />"
					method="post" onclick="return false;">
					<input type="hidden" name="reParentNo" value="${boardVo.boNo}">
					<input type="hidden" name="reCategory" value="FREE">
					<div class="form-group">
						<label class="col-sm-2  control-label">댓글</label>
						<div class="col-sm-8">
							<textarea rows="3" name="reContent" class="form-control"></textarea>
						</div>
						<div class="col-sm-2">
							<button id="btn_reply_regist" type="button"
								class="btn btn-sm btn-info">등록</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- // END : 댓글 등록 영역  -->


		<!-- // START : 댓글 목록 영역  -->
		<div id="id_reply_list_area"></div>

		<div class="row text-center" id="id_reply_list_more">
			<button id="btn_reply_list_more">
				<span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
				더보기
			</button>
		</div>
		<!-- // END : 댓글 목록 영역  -->

		<!-- START : 댓글 수정용 Modal-->
		<div class="modal fade" id="id_reply_edit_modal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form name="frm_reply_edit"
						action="<c:url value='/reply/replyModify' />" method="post"
						onclick="return false;">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">×</button>
							<h4 class="modal-title">댓글수정</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" name="reNo" value="">
							<textarea rows="3" name="reContent" class="form-control"></textarea>
						</div>
						<div class="modal-footer">
							<button id="btn_reply_modify" type="button"
								class="btn btn-sm btn-info">저장</button>
							<button type="button" class="btn btn-default btn-sm"
								data-dismiss="modal">닫기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- END : 댓글 수정용 Modal-->

	</div>
	<!-- END : 더보기 버튼 영역-->



	</div>
	<!-- END : 댓글 관련 영역-->

	<!-- START : 댓글 처리 스크립트-->
	<script type="text/javascript">
	// 상단에 전역변수 - 공통함수 - 이벤트함수 - 초기화처리
		var replyParam={"curPage":1, "rowSizePerPage":10,
							"reCategory":"FREE", "reParentNo":${boardVo.boNo}
							}
	 
		/* var curPage = 1;
		var rowSizePerPage = 10;
		var parentNo = ${boardVo.boNo};*/

		// 댓글목록을 구하는 함수
		function fn_reply_list() {
			$.ajax({
				type :"POST" 												// 전송 방식 설정 (Defaut : GET)
				, url : '<c:url value="/reply/replyList"/>'			// 요청 페이지 URL정보
				, dataType : 'json' 										// 서버로부터 전달받을 데이터 유형 (html, xml, json, script)
				, data : replyParam											// 서버에 전송할 파라미터 정보
				, success : function(data) {
					console.log('data', data);
					if(data.result){
						$reply_list_area = $('#id_reply_list_area')
						$.each(data.data, function(i, el){
							// find : (.+) repl : str += '$1';
							var str = '';
							str += '<div class="row" data-re-no="'+ el.reNo +'">';
							str += '    <div class="col-sm-2 text-right" >'+ el.reMemName +'</div>';
							str += '      <div class="col-sm-6"><pre>'+ el.reContent +'</pre></div>';
							str += '      <div class="col-sm-2" >'+ el.reRegDate +'</div>';
							str += '      <div class="col-sm-2">';
							if(el.reMemId=='${sessionScope.USER_INFO.userId}'){
								str += '          <button name="btn_reply_edit" type="button" class=" btn btn-sm btn-info" >수정</button>';
								str += '          <button name="btn_reply_delete" type="button" class="btn btn-sm btn-danger" >삭제</button>';
							}
							str += '      </div>';
							str += '  </div>';
							$reply_list_area.append(str);
						});
						replyParam.curPage +=1;
						// 더보기 버튼
						if(data.count < replyParam.rowSizePerPage){
							$('#btn_reply_list_more').hide();
						}
					}
				}  																// 요청에 성공한 경우 호출되는 함수 (data, status, xhr )
				, error : function(req, st, err) {
					console.log("------------------------")
					console.log('request', req);
					console.log('status', st);
					console.log('errors', err);
					console.log("------------------------")
				}																// 요청에 실패한 경우 호출되는 함수 (xhr, status, error)
			}); // ajax
		} // fn_reply_list
		
		$(document).ready(
			function() {
				// 수정버튼 클릭
				$('#id_reply_list_area').on('click','button[name=btn_reply_edit]', function(e) {
					// 모달창 띄우기, 현재 클릭한 버튼의 영역에서 reNo, reContext를
					//this : javascript 객체 -> jQuery 객체 $(this)
					$btn = $(this);
					$div = $btn.closest('div.row');
					
					// 모달창 내의 폼에 복사
					// var f = document.forms.frm_reply_edit;
					// f.reNo.value = $div.data('re-no');
					$('form[name=frm_reply_edit] input[name=reNo]').val($div.data('re-no'));
					$('form[name=frm_reply_edit] textarea[name=reContent]').val($div.find('div pre').text());
					$('#id_reply_edit_modal').modal();
					
				}); // btn_reply_edit.click
						
				// 모달창의 (수정)저장버튼 btn_reply_modify 클릭
				$("#btn_reply_modify").click(function(e) {
					// 성공하면
					// 예) 25 댓글 수정 성공
					// 모달 폼에 있는 textarea의 값을
					// div.class[data-re-no=35] div pre 그 이하의 교체
					// 모달 폼의 reNo, reContent는 '' 설정
					// 모달창 $('#모달아이디').modal('hide')					
					
					txt = $('form[name=frm_reply_edit] textarea[name=reContent]').val();
/* 					$btn = $(this);
					$div = $('#id_reply_list_area')
 */					rNo = $('form[name=frm_reply_edit] input[name=reNo]').val();
					params = "reNo=" + rNo +"&reContent=" + txt; //$div.data('re-no');
					$.ajax({ 
						  type :"POST"
						, url : '<c:url value="/reply/replyModify" />' 		
						, dataType : 'json' 
						, data : params	
						, success : function(data) {
								console.log('data', data);
								if(data.result){
									// 수정
									$('div.row[data-re-no='+ rNo +']').find('div pre').text(txt);
									
									$('form[name=frm_reply_edit] input[name=reNo]').val('');
									$('form[name=frm_reply_edit] textarea[name=reContent]').val('');
									
									$('#id_reply_edit_modal').modal('hide');
								}else{
									alert(data.msg);
								}
						  }  
						, error : function(req, st, err) {
								console.log('----------------------------');
								console.log('request', req);
								console.log('status', st);
								console.log('errors', err);
								console.log('----------------------------');
							}	 	
					}); // ajax			
				}); // btn_reply_modify.click
				
				// 삭제버튼 클릭
				$('#id_reply_list_area').on('click',
						'button[name=btn_reply_delete]', function(e) {
					$btn = $(this);
					$div = $btn.closest('div.row');
					
					e.preventDefault();
					res = confirm("글을 삭제하시겠습니까?");
					if(res){
						params = "reNo=" + $div.data('re-no');
						$.ajax({ 
							  type :"POST"
							, url : '<c:url value="/reply/replyDelete" />' 		
							, dataType : 'json' 
							, data : params	
							, success : function(data) {
									console.log('data', data);
									if(data.result){
										// 삭제
										$div.remove();
									}else{
										alert(data.msg);
									}
							  }  
							, error : function(req, st, err) {
									console.log('----------------------------');
									console.log('request', req);
									console.log('status', st);
									console.log('errors', err);
									console.log('----------------------------');
								}	 	
						}); // ajax			
					}  // confirm		
					
				}); // btn_reply_delete.click
				
				// 더보기 버튼 클릭
				$('#btn_reply_list_more').click(function(e) {
					fn_reply_list();
				}); // #btn_reply_list_more.click
				
				// 등록버튼 클릭
				$("#btn_reply_regist").click(function(e) {
					e.preventDefault();
					res = confirm("글을 등록하시겠습니까?");
					if(res){
						params = $('form[name=frm_reply]').serialize();
						$.ajax({ 
							  type :"POST"
							, url : '<c:url value="/reply/replyRegist" />' 		
							, dataType : 'json' 
							, data : params	
							, success : function(data) {
									console.log('data', data);
									if(data.result){
										replyParam.curPage = 1;
										document.forms.frm_reply.reContent.value = '';
										//$('form[name=frm_reply] textarea[name=reContent]').val('');
										
										// 현재 목록 영역 remove()
										$('#id_reply_list_area').html('');
										// 목록조회 함수 호출
										fn_reply_list();
										//alert(data.msg);
									}else{
										alert(data.msg);
									}
							  }  
							, error : function(req, st, err) {
									console.log('----------------------------');
									console.log('request', req);
									console.log('status', st);
									console.log('errors', err);
									console.log('----------------------------');
								}	 	
						}); // ajax			
					}  // confirm		
				}); // #btn_reply_regist.click
				
				// 초기화 함수 호출
				fn_reply_list();
			}); // ready
	</script>
	<!-- END : 댓글 처리 스크립트-->




</body>
</html>






