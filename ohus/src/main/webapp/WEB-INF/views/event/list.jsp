<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
 
<script type="text/javascript">

	$(function(){
		
		$('#search_form').submit(function(){
			
			if($('#keyword').val().trim()==''){ //검색어를 입력하지 않은 경우
				
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
			
		});
		
	}); 

</script>

</head>

<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<!--  내용 시작 -->
		<div class="content-main">
			<h2>이벤트 목록</h2>
			<!--  검색창 시작 --> 
			<form id="search_form" action="list.do" method="get"> <%-- get방식으로. 주소창에 명시됨 --%>
			<ul class="search">
				<li>
					<select name="keyfield"> <%-- option 태그 안에 if를 넣어 조건체크를 해주고 selected는 선택된 것 표시 --%>
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
						<option>
						<%--만약에 제목 검색에 a라고 적고 작성자로 옵션을 바꿔도 내용이 남아있도록 처리한 것! --%>
					</select>
				</li>
				
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>

				<li>
					<input type="submit" value="검색">
				</li>
								
			</ul>
			
			</form>
			<!--  검색창 끝 -->
			
			
			<!-- =========오른쪽 버튼처리 시작 ==========-->
			
			<!-- 관리자로 로그인한 경우 (글쓰기) (목록)-->
			<!-- 관리자 버튼 시작 -->
			<c:if test="${!empty user_num && user_auth==9}">
				<div class="list-space align-right">
				
					<!-- 로그인 + 관리자의 경우만 버튼이 보이도록 처리 -->
					<!-- 로그인이 안 되어져있어서 user_num이 비어있을 때 처리, input 태그 안에서 c:if를 넣을 수 있다!! -->
					<input type="button" value="등록" onclick="location.href='writeForm.do'">
					<input type="button" value="목록" onclick="location.href='list.do'"> 
				</div>
			</c:if>
			<!-- 관리자 버튼 끝 -->
			
			<!-- 관리자가 아닌 경우 (당첨확인) (목록) -->
			<c:if test="${user_auth !=9}"> <!-- 비회원인 경우 당첨확인 버튼 disabled처리 -->
				<input type="button" value="당첨확인" onclick="location.href='result.do'" 
						  id="event_result" name="event_result" <c:if test="${empty user_num}">disabled="disabled"</c:if>>
				<input type="button" value="목록" onclick="location.href='list.do'"> 
			</c:if>
			
			<!-- =========오른쪽 버튼처리 끝 =========-->
			
			<!-- ======이벤트 글 리스트 처리 시작=========== -->
			<!-- ListAction 다 처리한 후에 적기! -->
			<!-- 표시할 게시물이 없음 -->
			<c:if test="${count ==0 }"> 
				<div class="result-display">
					아직 이벤트가 없네요!
				</div>
			</c:if>
			
			<!-- 목록이 있는 경우 -->
			<c:if test="${count>0 }"> 
				<div class="event_main">
					<ul class="event_ul">
						<c:forEach var="event" items="${list}"> <!-- 넣어야되나..??? -->
							<li class="event_list">
								<!-- 이벤트 사진을 누르면 상세글로 이동함 -->
								<div class="event_photo">
								
									<a href="detail.do?event_num=${event.event_num}">
										<img src="${pageContext.request.contextPath}/upload/${event.event_photo}">
									</a> 
								</div>
								<!-- 이벤트 사진 아래 (진행중) (기간) -->
								<div class="event_footer">
									<div class="evnet-status">
										<c:if test="${event.event_status==1}">종료</c:if>
										<c:if test="${event.event_status==2}">진행중</c:if>
									</div>
									
									<div class="event-date">
										${event.event_start} ~ ${event.event_end }
									</div>
								
								</div>
							</li>
						</c:forEach>

					</ul>
				</div>	
					
				<!-- 페이지 보여주기 -->
				<div class="align-center">${page}</div>
			</c:if>	
			<!-- ======이벤트 글 리스트 처리 끝=========== -->		
		</div>
		<!-- 내용 끝 -->
	</div>
</body>

</html>