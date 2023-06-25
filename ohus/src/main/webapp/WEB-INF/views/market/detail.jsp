<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ssk/market_detail.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="contents">
		<h2 class="header-title">${market.market_title}</h2>
		<img src="${pageContext.request.contextPath}/images/face.png" width="100" height="100" class="my-photo">
		<h4 class="header-user-id">${market.id}</h4>
		<c:if test="${!empty market.market_modifydate}">
			<h6 class="header-date">작성일 : ${market.market_regdate} 수정일 : ${market.market_modifydate} 조회수 : ${market.market_hit}</h6>
		</c:if>
		<c:if test="${empty market.market_modifydate}">
			<h6 class="header-date">작성일 : ${market.market_regdate} 조회수 : ${market.market_hit}</h6> 
		</c:if>
		<div class="content-img">
			<div class="inner-img">
				<img src="${pageContext.request.contextPath}/upload/${market.market_photo1}" width="300" height="300" class="content-photo">
			</div>
			<div class="inner-img">
				<img src="${pageContext.request.contextPath}/upload/${market.market_photo2}" width="300" height="300" class="content-photo">
			</div>	
		</div>
		
		<div class="content-main">
			<p>
				${market.market_content}
			</p>
		</div>
		
		<div class="content-footer">
			<ul >
				<li>
					<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>
					<c:if test="${user_num == market.mem_num}">
					<input class="footer-button" type="button" value="수정하기" onclick="location.href='updateForm.do?market_num=${market.market_num}'">
					<input class="footer-button" type="button" value="삭제하기" id="delete_btn">
					<script type="text/javascript">
						let delete_btn = document.getElementById('delete_btn');
						// 이벤트 연결
						delete_btn.onclick=function(){
							let choice = confirm('삭제하겠습니까?');
							if(choice){
								location.replace('delete.do?market_num=${market.market_num}');
							}
						};
						
					</script>
					</c:if>
					<input class="footer-button" type="button" value="목록보기" onclick="location.href='list.do'">
					<c:if test="${market.market_status == 0}">
						<c:if test="${user_num == market.mem_num}">
							<input class="footer-button" type="button" value="채팅하기" onclick="location.href='${pageContext.request.contextPath}/chatting/chatroom.do?market_num=${market.market_num}'">
						</c:if>
						<c:if test="${user_num != market.mem_num}">
							<input class="footer-button" type="button" value="채팅하기" onclick="location.href='${pageContext.request.contextPath}/chat/chat.do?market_num=${market.market_num}&buyer_num=${user_num}&seller_num=${market.mem_num}'">
						</c:if>
					</c:if>
					<c:if test="${market.market_status == 1}">
						<input class="close-chat" type="button" value="채팅하기" disabled="disabled">
					</c:if>
				</li>
			</ul>
		</div>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>



