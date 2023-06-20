<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅방</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/chatMessage.js"></script>
<script type="text/javascript">
	let chatroom_num = ${chatroom_num};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 채팅방 헤더 시작 -->
	<!-- 보내는 사람 정보, 게시글 제목 필요-->
	<!-- 채팅방 헤더 끝 -->
	
	<!-- 채팅 내용 시작 -->
	<div class="content-main">		
		<!-- 채팅 내용 출력 시작 -->
		<div id="output"></div>
		<!-- 채팅 내용 출력 끝 -->
		<div id="chatting_div">
			<form id="chat_form">
				<input type="hidden" name="chatroom_num" value="${chatroom_num}" id="chatroom_num">
				<textarea rows="3" cols="20" name="message" id="message" class="rep-content"></textarea>
				<div id="re_second" class="align-right">
					<input type="submit" value="전송">
				</div>
			</form>
		</div>
	</div>
	<!-- 채팅 내용 끝 -->
</div>
</body>
</html>



