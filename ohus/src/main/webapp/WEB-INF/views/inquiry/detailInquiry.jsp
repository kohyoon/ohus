<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>${inquiry.inq_title}</h2>
		<ul class="detail-info">
			<li>
				<label>카테고리</label>
				<c:if test="${inquiry.inq_category == 1}">사이트 문의</c:if>
				<c:if test="${inquiry.inq_category == 2}">신고 문의</c:if>
			</li>
			<li>
				<label>작성자</label>
				${inquiry.id}
			</li>
			<li>
				<label>작성일</label>
				${inquiry.inq_regdate}
			</li>
			<li>
				<label>처리상태</label>
				: *체크박스추가해야됨
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<p>${inquiry.inq_content}</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			<li>
				작성일 : ${inquiry.inq_regdate}
				<c:if test="${user_num == inquiry.mem_num}">
				<input type="button" value="수정" onclick="location.href='modifyInquiryForm.do?inq_num=${inquiry.inq_num}'">
				<input type="button" value="목록" onclick="location.href='listInquiry.do'">
				</c:if>
			</li>
		</ul>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>