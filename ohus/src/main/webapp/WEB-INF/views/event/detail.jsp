<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  

<!DOCTYPE html>
<html>
<head>  
<meta charset="UTF-8">
<title>이벤트 글 상세 보기 - 사진을 누르면 이벤트 상세글이 보인다</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/event.reply.js"></script>
</head>
<body>       
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<!-- 내용 시작 -->
<div class="content-main"> 
	<!-- 상단 -->
	<div class="align-center">
		<h3>제목 : ${event.event_title}</h3>
			<%-- 수정일이 있으면 수정일을 보여주고 작성일은 항상 보여주기 --%>
			<div class="align-right">
				<ul class="detail-sub">
					<li> 
						조회수 : ${event.event_hit}
						<br>
						 <%-- 수정한 날짜가 있으면, 내용을 표시 --%>
						<c:if test="${!empty event.event_modifydate}">
							최근 수정일 : ${event.event_modifydate }
						</c:if>
							작성일 : ${event.event_regdate}
						
						<%-- 아래 화면은 관리자로 로그인을 해야 보이므로 스크립트 처리--%>
						<c:if test="${!empty user_num && user_auth == 9 }">
							<input type="button" value="수정" 
							onclick="location.href='updateForm.do?event_num=${event.event_num}'"> 
							<input type="button" value="삭제"  id="delete_btn"> <%-- 자바 스크립트로 처리하기 위해 아이디 부여, 정말 삭제하시겠습니까? 표시 --%>
							
							<script type="text/javascript">
								let delete_btn = document.getElementById('delete_btn'); 
								//이벤트 연결
								delete_btn.onclick=function(){
									
									let choice = confirm('삭제하겠습니까?'); 
									if(choice){ //true가 되면
										location.replace('delete.do?event_num=${event.event_num}'); //replace() : href와 다르게 덮어 쓰우면서 뒤로 가기가 불가능해진다. href는 객체의 속성이며 replace는 메서드
									}
								}
							</script>
						</c:if>
					</li>
				</ul>
		</div>
	</div>
	<!-- 상단 끝 -->
			
	<hr size="1" noshade="noshade" width="100%">

	<!-- 내용 -->
	<div class="align-center">
		이벤트 기간 : ${event.event_start} ~ ${event.event_end}
		<br>
		당첨자 수 : ${event.winner_count} 
		<img src="${pageContext.request.contextPath}/upload/${event.event_photo}" class="detail.img">
		<p>
			${event.event_content}
		</p>
	</div>

	<hr size="1" noshade="noshade" width="100%">

	<!--======== 댓글 시작 ==========-->
	<div id="reply_div">
		<span class="re-title">댓글 달기</span>
		<form id="re_form">
			<input type="hidden" name="event_num" value="${event.event_num}" id="event_num">
			<%-- 회원인 경우에만 댓글 달 수 있도록 textarea 안에 조건 넣기 --%>
			<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content"
			<c:if test="${empty user_num}">disabled="disabled"</c:if>><c:if test="${empty user_num}">로그인 후 작성할 수 있습니다</c:if></textarea>
			<c:if test="${!empty user_num}"> <!-- 로그인 되어 있을 때 댓글 달 수 있음 -->
				<div id="re_first">
				 	<span class="letter-count">300/300</span> <!-- 글자수 제한 -->
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="전송">
				</div>
			</c:if>
		</form>
	</div>
	<!-- 댓글 끝 -->
	
	<!-- 댓글 목록 출력 시작-->
	<div id="output">
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음 댓글 보기">
		</div>
		<div id="loading" style="display: none;"> <%-- 로딩바 이미지 빙글빙글 도는 거 --%>
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
	</div>
	<!-- 댓글 목록 출력 끝-->
	
	<!-- =======댓글 끝 =========-->


</div>		
<!-- 내용 끝 -->

</body>
</html>