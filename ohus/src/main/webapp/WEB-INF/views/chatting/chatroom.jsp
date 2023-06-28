<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅방 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ssk/chatroom.css">
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
			<c:forEach var="chatroom" items="${list}" varStatus="status">
				<tr class="chatroom-item">
					<td>
						
						<a href="chatting.do?chatroom_num=${chatroom.chatroom_num}&buyer_num=${chatroom.buyer_num}&seller_num=${chatroom.seller_num}">
							<div>
							<c:if test="${readCheckList.get(status.index)==1}">
							 	<img src="${pageContext.request.contextPath}/images/readcheck.png" width="15px">
							</c:if>
								${chatroom.chatroom_num}
							</div>
						</a>
					</td>
					<td>
						<a href="chatting.do?chatroom_num=${chatroom.chatroom_num}&buyer_num=${chatroom.buyer_num}&seller_num=${chatroom.seller_num}">
							<div>${chatroom.market_title}</div>
						</a>
					</td>
					<td>
						<a href="chatting.do?chatroom_num=${chatroom.chatroom_num}&buyer_num=${chatroom.buyer_num}&seller_num=${chatroom.seller_num}">
							<div>${chatroom.id}</div>
						</a>
					</td>
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