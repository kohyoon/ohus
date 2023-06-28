<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/faq.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/detail.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/notice.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div id="cs_center">
		<button onclick="location.href='${pageContext.request.contextPath}/faq/faqPay.do'">FAQ</button>
		<button id="this_cs" onclick="location.href='${pageContext.request.contextPath}/notice/listNotice.do'">공지사항</button>
		<button onclick="location.href='${pageContext.request.contextPath}/inquiry/listInquiry.do'">문의게시판</button>
	</div>
	<!-- 내용 시작 -->
	<div class="container">
		<ul class="detail-info">
			<li>
				<h2><span>TITLE | </span> ${notice.notice_title}</h2>
			</li>
			<li>
				<span>WRITER : </span>${notice.id}
				<span> | HIT : </span>${notice.notice_hit}
				<span> | 작성일 : </span>${notice.notice_regdate}
				<c:if test="${!empty notice.notice_mdate}">
				<span> | 최근 수정일 : </span>${notice.notice_mdate}
				</c:if>
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty notice.notice_filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${notice.notice_filename}" class="detail-img">
		</div>
		</c:if>
		<p class="content">
			${notice.notice_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<%-- 로그인 한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>
		<c:if test="${user_num == notice.mem_num}">
		<div class="detail-sub">
			<input type="button" value="수정" id="modify_btn" onclick="location.href='modifyNoticeForm.do?notice_num=${notice.notice_num}'">
			<input type="button" value="삭제" id="delete_btn">
			<input type="button" value="목록" onclick="location.href='listNotice.do'">
		</div>
		<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			//이벤트 연결
			delete_btn.onclick = function(){
				let choice = confirm('삭제하겠습니까?');
				if(choice){
					location.replace('deleteNotice.do?notice_num=${notice.notice_num}')
				}
			};
		</script>
		</c:if>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>