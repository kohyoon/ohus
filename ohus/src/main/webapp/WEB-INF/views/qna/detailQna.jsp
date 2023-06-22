<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품문의 글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qna.answer.js"></script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
		<h2>${qna.qna_title}</h2>
		<ul>
			<li>
				작성자 : ${qna.id}
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty qna.qna_filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${qna.qna_filename}" class="detail-img">
		</div>
		</c:if>
		<p>
			${qna.qna_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			<li>
				<c:if test="${!empty qna.qna_mdate}">
				최근 수정일 : ${qna.qna_mdate}
				</c:if>
				작성일 : ${qna.qna_regdate}
				<%-- 로그인 한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>
				<c:if test="${user_num == qna.mem_num}">
				<input type="button" value="수정" onclick="location.href='modifyQnaForm.do?qna_num=${qna.qna_num}'">
				<input type="button" value="삭제" id="delete-btn">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					delete_btn.onclick = function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('deleteQna.do?qna_num=${qna.qna_num}');
						}
					};
					
				</script>
				</c:if>
			</li>
		</ul>
		<!-- 댓글 시작 -->
		
		
		
		<!-- 댓글 끝 -->
	</div>
	<!-- 내용 끝 -->
	
</div>
</body>
</html>