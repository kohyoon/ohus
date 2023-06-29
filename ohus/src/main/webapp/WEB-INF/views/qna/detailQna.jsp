<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품문의 글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="/ohus/css/koy/faq.css">
<link rel="stylesheet" href="/ohus/css/koy/detail.css">
<link rel="stylesheet" href="/ohus/css/koy/notice.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/item.answer.js"></script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
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
		<ul class="detail-sub">
			<li style="width:200px;">
				<%-- 로그인 한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>
				<c:if test="${user_num == qna.mem_num && qna.qna_status < 2}">
				<input type="button" value="수정" onclick="location.href='modifyQnaForm.do?qna_num=${qna.qna_num}'">
				<input type="button" value="삭제" id="delete_btn">
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
		<hr size="1" noshade="noshade" width="100%">
		
		<!-- 답변 시작 -->
		<div id="reply_div">
			<!-- 답변 목록 출력 시작 -->
			<div id="output"></div>
				<div class="paging-button" style="display:none;">
					<input type="button" value="다음글 보기">
				</div>
			<div class="loading" style="display:none;">
				<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
			</div>
			<!-- 답변 목록 출력 끝 -->
			<div id="reply_div">
				<form id="a_form">
					<input type="hidden" name="qna_num" value="${qna.qna_num}" id="qna_num">
					<textarea rows="3" cols="50" name="a_content" id="a_content" class="rep-content" placeholder="답변 입력"
							<c:if test="${user_auth < 9}">disabled="disabled"</c:if>><c:if test="${user_auth < 9}">관리자만 작성할 수 있습니다.</c:if></textarea>
					<c:if test="${!empty user_num}">
					<div id="re_first">
						<span class="letter-count">300/300</span>
					</div>
					<div id="re_second" class="align-right" style="margin:0;">
						<input type="submit" value="전송">
					</div>
					</c:if>
				</form>
			</div>
		</div>		
		
		<!-- 상품 문의 답변 끝 -->
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>