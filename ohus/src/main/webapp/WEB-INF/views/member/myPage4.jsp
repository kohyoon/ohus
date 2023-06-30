<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 채팅 내역</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/lyj/myPage.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/lyj/table.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<style>
.tables-container {
	display: flex;
	justify-content: space-between;
}

.table-container {
	width: 55%;
	box-sizing: border-box;
	margin-right: 40px;
}

.table-container:first-child {
	margin-right: 5%;
}

.table-container:last-child {
	margin-left: 5%;
}

.table-container h3 {
	text-align: left;
	margin-left: 40px;
}

.table-container table {
	width: 100%;
}

.table-container th, .table-container td {
	padding: 5px;
	border: 1px solid #ddd;
}
</style>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/member/myPageheader.jsp" />
	<div class="page-main">
		<div class="content-main">
			<!-- 내용 시작 -->
			<div class="tables-container">
				<div class="table-container">
					<h3>내가 보낸 채팅</h3>

					<c:if test="${!empty sendList}">
						<table>
							<tr>
								<th>채팅방번호</th>
								<th>게시글 제목</th>
								<th>받는사람</th>
							</tr>

							<c:forEach var="list" items="${sendList}">
								<tr>
									<td><a
										href="${pageContext.request.contextPath}/chatting/chatting.do?chatroom_num=${list.chatroom_num}&buyer_num=${list.buyer_num}&seller_num=${list.seller_num}">
											${list.chatroom_num} </a></td>
									<td><a
										href="${pageContext.request.contextPath}/chatting/chatting.do?chatroom_num=${list.chatroom_num}&buyer_num=${list.buyer_num}&seller_num=${list.seller_num}">
											${list.market_title} </a></td>
									<td><a
										href="${pageContext.request.contextPath}/chatting/chatting.do?chatroom_num=${list.chatroom_num}&buyer_num=${list.buyer_num}&seller_num=${list.seller_num}">
											${list.id} </a></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>

					<c:if test="${empty sendList}">
						<span class="no-list">보낸 채팅이 없습니다.</span>
					</c:if>


				</div>

				<div class="table-container">

					<h3>내가 받은 채팅</h3>
					<c:if test="${!empty receiveList}">
						<table>
							<tr>
								<th>채팅방번호</th>
								<th>게시글 제목</th>
								<th>보낸사람</th>
							</tr>

							<c:forEach var="list" items="${receiveList}">
								<tr>
									<td><a
										href="${pageContext.request.contextPath}/chatting/chatting.do?chatroom_num=${list.chatroom_num}&buyer_num=${list.buyer_num}&seller_num=${list.seller_num}">
											${list.chatroom_num} </a></td>
									<td><a
										href="${pageContext.request.contextPath}/chatting/chatting.do?chatroom_num=${list.chatroom_num}&buyer_num=${list.buyer_num}&seller_num=${list.seller_num}">
											${list.market_title} </a></td>
									<td><a
										href="${pageContext.request.contextPath}/chatting/chatting.do?chatroom_num=${list.chatroom_num}&buyer_num=${list.buyer_num}&seller_num=${list.seller_num}">
											${list.id} </a></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					<c:if test="${empty receiveList}">
						<span class="no-list">받은 채팅이 없습니다</span>
					</c:if>


				</div>
			</div>
		</div>
	</div>
</body>
</html>