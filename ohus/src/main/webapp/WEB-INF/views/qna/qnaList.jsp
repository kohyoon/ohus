<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품문의 게시판 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
		<h2>상품문의 게시판 목록</h2>
		<!-- 검색창 시작 -->
		<form id="search_form" action="qnaList.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
		<!-- 검색창 끝 -->
		<div class="list-space align-right">
			<input type="button" value="문의하기" onclick="location.href='writeQnaForm.do'"
				<c:if test="${empty user_num}">disabled="disabled"</c:if>>
			<input type="button" value="전체목록" onclick="location.href='qnaList.do'">
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 상품문의가 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			<c:forEach var="qna" items="${list}">
			<tr>
				<td>${qna.qna_num}</td>
				<td><a href="detailQna.do?qna_num=${qna.qna_num}">${qna.qna_title}</a></td>
				<td>${qna.id}</td>
				<td>${qna.qna_regdate}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>