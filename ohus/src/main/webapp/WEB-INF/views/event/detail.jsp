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
<style type="text/css">
.btn{
	text-align: right;
}
.board-main {
  padding-left: 400px;
  padding-right: 400px;
} 

.align-left {
  text-align: left;
}

.board-sub {
  margin-top: 10px;
  font-size: 14px;
  color: #888;
}

.board-sub ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.board-sub li {
  display: inline-block;
  margin-right: 10px;
}

.btn {
  padding: 5px 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn:hover {
  background-color: #0056b3;
}

#reply_div {
  margin-top: 20px;
}

.re-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
  display: block;
}

#re_form {
  display: inline-block;
}

.re-content {
  width: 40%;
  height: 30px;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: none;
}

#re_first {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.letter-count {
  font-size: 14px;
  color: #888;
}

#re_second {
  text-align: right;
}

#reply_go {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}





</style>

</head>
<body>       
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<!-- 내용 시작 -->
<div class="board-main"> 
	<!-- 상단 -->
	<div class="align-left">
		<h3>제목 : ${event.event_title}</h3>
			<%-- 수정일이 있으면 수정일을 보여주고 작성일은 항상 보여주기 --%>
	</div>		
	
	<div class="board-sub">
		<ul>
			<li> 
				조회수 : ${event.event_hit}
				<br>
				<%-- 수정한 날짜가 있으면, 내용을 표시 --%>
				<c:if test="${!empty event.event_modifydate}">
				  <%-- 자바스크립트로 날짜 포맷팅 --%>
				  <script type="text/javascript">
				    let modifyDate = new Date("${event.event_modifydate}");
				    let formattedModifyDate = modifyDate.toLocaleDateString('ko-KR');
				    document.write("최근 수정일 : " + formattedModifyDate);
				  </script>
				</c:if>
				
				<%-- 작성일 표시 --%>
				<%-- 자바스크립트로 날짜 포맷팅 --%>
				<script type="text/javascript">
				  let regDate = new Date("${event.event_regdate}");
				  let formattedRegDate = regDate.toLocaleDateString('ko-KR');
				  document.write("작성일 : " + formattedRegDate);
				</script>
				
				<%-- 아래 화면은 관리자로 로그인을 해야 보이므로 스크립트 처리--%>
				<c:if test="${!empty user_num && user_auth == 9}">
				 <div class="align-right">
						<input type="button" value="수정" class="btn"
						onclick="location.href='updateForm.do?event_num=${event.event_num}'"> 
						<input type="button" value="삭제"  id="delete_btn" class="btn"> <%-- 자바 스크립트로 처리하기 위해 아이디 부여, 정말 삭제하시겠습니까? 표시 --%>
						
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
					</div>
				</c:if>
			</li>
		</ul>
	</div>

	<!-- 상단 끝 -->
			
	<hr size="1" noshade="noshade" width="100%">

	<!-- 내용 시작 --> 
	<div class="align-center">
		이벤트 기간 : ${event.event_start} ~ ${event.event_end}
		<br>
		당첨자 수 : ${event.winner_count} 
		<p>
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
		<!-- 회원인 경우에만 달 수 있고, 댓글은 이벤트 당 하나만 달 수 있다 -->
		<textarea rows="3" cols="140" name="re_content" id="re_content" class="re-content"<c:if test="${empty user_num || event.event_status == 1}">disabled="disabled"</c:if>><c:if test="${empty user_num}">로그인 후 작성할 수 있습니다</c:if><c:if test="${event.event_status == 1}">종료된 이벤트입니다</c:if></textarea>
		
		<c:if test="${!empty user_num && event.event_status!=1}"> <!-- 로그인 되어 있고 status가 1이 아니면 댓글 달 수 있음 -->
			<div id="re_first">   
			 	<span class="letter-count">300/300</span> <!-- 글자수 제한 -->
			</div>
			<div id="re_second" class="align-right">
				<input type="submit" value="전송" id="reply_go" name="reply_go" class="btn"> <!-- 전송 버튼에 btn 클래스 추가 -->
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