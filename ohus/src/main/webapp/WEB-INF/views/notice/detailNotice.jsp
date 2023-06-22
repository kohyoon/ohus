<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
		<h2>${notice.notice_title}</h2>
		<ul class="detail-info">
			<li>
				${notice.id}<br>
				조회 : ${notice.notice_hit}
			</li>
			<li>
				<c:if test="${!empty notice.notice_mdate}">
				최근 수정일 : ${notice.notice_mdate}
				</c:if>
				작성일 : ${notice.notice_regdate}
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty notice.notice_filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${notice.notice_filename}" class="detail-img">
		</div>
		</c:if>
		<p>
			${notice.notice_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<input type="button" value="목록" onclick="location.href='listNotice.do'">
		<%-- 로그인 한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>
		<c:if test="${user_num == notice.mem_num}">
		<input type="button" value="수정" onclick="location.href='modifyNoticeForm.do?notice_num=${notice.notice_num}'">
		<input type="button" value="삭제" id="delete_btn">
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
</div>
</body>
</html>