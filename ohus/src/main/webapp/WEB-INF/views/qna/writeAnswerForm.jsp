<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품문의 답변 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/form.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
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
			if(isNaN($('select[name="qna_category"]').val())){
				alert('카테고리를 선택해야 합니다.');
				return false;
			}
			if(isNaN($('select[name="item_num"]').val())){
				alert('상품을 선택해야 합니다.');
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
		<h2>상품문의 게시판 글쓰기</h2>
		<%-- 
		<!-- 상품 문의 답변 시작 -->
		<div class="detail-info">
		<ul>
			<li>
				<h2><span>TITLE </span> ${qna.qna_title}</h2>
			</li>
			<li>
				<span>WRITER : </span>${qna.id}
				<span> / ITEM : </span>${qna.item_name}
				<span>/ CATEGORY : </span>
				<c:if test="${qna.qna_category == 1}">상품</c:if>
				<c:if test="${qna.qna_category == 2}">배송</c:if>
				<c:if test="${qna.qna_category == 3}">반품</c:if>
				<c:if test="${qna.qna_category == 4}">교환</c:if>
				<c:if test="${qna.qna_category == 5}">환불</c:if>
				<c:if test="${qna.qna_category == 6}">기타</c:if>
				<span><br>작성일 : </span>${qna.qna_regdate}
				<c:if test="${!empty qna.qna_mdate}">
				<span> / 최근 수정일 : </span>${qna.qna_mdate}
				</c:if>
				<span> / </span>
				<c:if test="${qna.qna_status == 2}">
				<input type="checkbox" checked disabled>처리완료
				</c:if>
				<c:if test="${qna.qna_status == 1}">
				<input type="checkbox" disabled>처리전
				</c:if>
			</li>
		</ul>
		</div>
		<hr size="1" noshade="noshade" width="100%">
		<p class="content">
			${qna.qna_content}
		</p>
		
		--%>
		
		
		<form id="write_form" action="writeAnswer.do" method="post">
			<ul>
				<li>
					<label for="qna_title">제목</label>
					<input type="text" name="qna_title" id="qna_title" maxlength="50">
				</li>
				<li>
					<label for="qna_content">내용</label>
					<textarea rows="5" cols="30" name="qna_content" id="qna_content"></textarea>
				</li>
				<li>
					상품문의 글번호 : ${qna.qna_num}
				</li>	
			</ul>
			<div class="align-center">
				<input type="submit" value="등록">
				<input type="button" value="목록" onclick="location.href='qnaList.do'">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>