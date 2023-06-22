<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>종료된 이벤트 목록</title>
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
			<h2>종료된 이벤트 목록</h2>
			<!--  검색창 시작 --> 
			<form id="search_form" action="endEventList.do" method="get"> <%-- get방식으로. 주소창에 명시됨 --%>
			<ul class="search">
				<li>
					<select name="keyfield" id="keyfield"> <%-- option 태그 안에 if를 넣어 조건체크를 해주고 selected는 선택된 것 표시 --%>
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
			
			<!-- 우측 버튼 시작 -->
			<div class="list-space align-right">
				<input type="button" value="목록" onclick="location.href='list.do'"> 
			</div>	
			<!-- 우측 버튼 끝 -->
			
			<!-- =========오른쪽 버튼처리 끝 =========-->
			
			<!-- ======이벤트 글 리스트 처리 시작=========== -->
		<c:if test="${count==0}">
					<!-- 종료된 이벤트가 없음 -->
					<div class="result-display">
						<span>종료된 이벤트가 없네요!</span>
					</div>
			</c:if> 

			<!-- 종료된 이벤트가 있음 -->
				<c:forEach var="event" items="${list}">
					<div class="event_main">
						<ul class="event_ul">
							
								<li class="event_list">
									<!-- 이벤트 사진을 누르면 상세글로 이동함 -->
									<div class="event_photo">
										<a href="detail.do?event_num=${event.event_num}">
											<img src="${pageContext.request.contextPath}/upload/${event.event_photo}">
										</a> 
									</div>
									
									<div class="event_title">
										<a href="detail.do?event_num=${event.event_num}">
											${event.event_title}
										</a>
									</div>
										
									<div class="event-date">
										${event.event_start} ~ ${event.event_end }
									</div>
									
									<div class="event-go">
										<input type="button" value="추첨시작" onclick="location.href='result.do'"">
									</div>
	
								</li>
							
	
						</ul>
					</div>	
					
					<!-- 페이지 보여주기 -->
					<div class="align-center">${page}</div>
			</c:forEach>	
			<!-- 종료된 이벤트가 있는 경우 끝 -->
			<!-- ======이벤트 글 리스트 처리 끝=========== -->		
		</div>
		<!-- 내용 끝 -->
	</div>
</body>

</html>