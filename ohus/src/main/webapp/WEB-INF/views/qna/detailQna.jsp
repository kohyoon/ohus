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
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>