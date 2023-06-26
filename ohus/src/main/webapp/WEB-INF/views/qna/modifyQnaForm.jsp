<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품문의 게시판 글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#modify_form').submit(function(){
			if($('#qna_title').val().trim() == ''){
				alert('제목을 입력하세요.');
				$('#qna_title').val('').focus();
				return false;
			}
			if($('#qna_content').val().trim() == ''){
				alert('내용을 입력하세요.');
				$('#qna_content').val('').focus();
				return false;
			}
			if(isNaN($('#order_num').val())){
				alert('구매내역을 선택해야 합니다. 구매내역이 없을 상품문의글을 남길 수 없습니다.');
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
		<h2>상품문의 게시판 글 수정</h2>
		<form id="modify_form" action="modifyQna.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="qna_num" value="${qna.qna_num}">
			<ul>
				<li>
					<label for="qna_title">제목</label>
					<input type="text" name="qna_title" id="qna_title" value="${qna.qna_title}" maxlength="50">
				</li>
				<li>
					<label for="qna_content">내용</label>
					<textarea rows="5" cols="30" name="qna_content" id="qna_content">${qna.qna_content}</textarea>
				</li>
				<li>
					<label for="qna_filename">파일</label>
					<input type="file" name="qna_filename" id="qna_filename" accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty qna.qna_filename}">
					<div id="file_detail">
						(${qna.qna_filename})파일이 등록되어 있습니다.
						<input type="button" value="파일삭제" id="file_del">
					</div>
					<script type="text/javascript">
						$(function(){
							$('#file_del').click(function(){
								let choice = confirm('파일을 삭제하시겠습니까?');
								
								if(choice){
									$.ajax({
										url:'deleteFile.do',
										type:'post',
										data:{qna_num:${qna.qna_num}},
										dataType:'json',
										success:function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용하세요.');
											} else if(param.result == 'wrongAccess'){
												alert('잘못된 접근입니다.');
											} else if(param.result == 'success'){
												$('#file_detail').hide();
											} else{
												alert('파일 삭제 오류!');
											}
										},
										error:function(){
											alert('네트워크 오류 발생');
										}
										
									});
								}
							});
						});
					</script>
					</c:if>
				</li>
				<li>
					<label for="order_num">구매내역</label>
					<select name="order_num" id="order_num">
						<option>===선택===</option>
						<c:forEach var="order" items="${list}">
						<option value="${order.detail_num}"<c:if test="${order.order_num}">selected</c:if>>[${order.order_num}] ${order.item_name}</option>
						</c:forEach>
					</select>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="글 상세" onclick="location.href='detailQna.do?qna_num=${qna.qna_num}'">
				<input type="button" value="목록" onclick="location.href='qnaList.do'">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>