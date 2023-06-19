<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅방 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>채팅방 목록</h2>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>채팅방 번호</th>
				<th>게시글 제목</th>
				<th>보내는 사람</th>
				
			</tr>
			<c:forEach var="chatroom" items="${list}">
			<tr>
				<td><a href="detail.do?market_num=${chatroom.chatroom_num}">${chatroom.chatroom_num}</a></td>
				<td>${chatroom.market_title}</td>
				<td>${chatroom.buyer_num}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>