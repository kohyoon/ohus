<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터 메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
	<h2>FAQ</h2>
	
	<h2><a href="${pageContext.request.contextPath}/notice/listNotice.do">공지사항</a></h2>
	<table>
		<tr>
			<th>제목</th>
			<th>작성일</th>
		</tr>
		<c:forEach var="notice" items="${noticeList}">
		<tr>
			<td><a href="${pageContext.request.contextPath}/notice/detailNotice.do?notice_num=${notice.notice_num}">${notice.notice_title}</a></td>
			<td>${notice.notice_regdate}</td>
		</tr>
		</c:forEach>
	</table>
	
	<h2><a href="${pageContext.request.contextPath}/inquiry/listInquiry.do">문의게시판</a></h2>
	
	<h2><a href="${pageContext.request.contextPath}/qna/qnaList.do">상품문의</a></h2>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>