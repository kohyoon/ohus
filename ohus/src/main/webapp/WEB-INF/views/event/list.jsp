<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<style type="text/css">
		.event-main {
  			padding: 0;
 			margin: 0;
		}

		.event-list {
  			display: flex;
  			flex-wrap: wrap;
  			justify-content: flex-start;
		}

		.event-list > ul {
		  list-style: none;
		  margin-left: 20px;
		  margin-top: 20px;
		  padding: 15px;
  		  justify-content: center; /* 수평 가운데 정렬 */
          align-items: center; /* 수직 가운데 정렬 */
		}
		

		.horizontal-list {
		  list-style-type: none;
		  display: flex;
		  flex-wrap: wrap;
		}
		.align-center{
			text-align:center;
		}
		
		.event-status-1{
			color: gray;
		}
		
		.event-status-2{
			color: #35c5f0;
		}
		
		.event-status{
		float: left;
		margin-left: 7px;
		}
		
		.event-date{
			float:right;
			margin-right: 15px;
		}
		.btn
		{
		background-color: white;
		color : gray;
		}
		
.btn:hover
{
color: black;
background-color : skyblue;
}
		
.button {
	margin-left : 1300px;
}

		
</style>
	<script type="text/javascript">
		function submitForm(event) {
		    if (event.keyCode === 13) {
		      event.preventDefault(); // 엔터 키의 기본 동작인 폼 제출을 방지
		      document.getElementById('search_form').submit(); // 폼 제출
		    }
		  }
	</script>
</head>
<body>
	<div class = "page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용시작 --%>
		<div class = "event-main">
			<%-- 검색창 시작 --%>
			<form id="search_form" action="list.do" method="get">
				<ul ul class="align-center">
					<li>
					<select name="keyfield"> <%-- option 태그 안에 if를 넣어 조건체크를 해주고 selected는 선택된 것 표시 --%>
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
						<option>
						<%--만약에 제목 검색에 a라고 적고 작성자로 옵션을 바꿔도 내용이 남아있도록 처리한 것! --%>
					</select>
				</li>
					<li>
						<input type="search" size="30" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색" style="display: none;">
					</li>
				</ul>
			</form>
			<%-- 검색창 끝 --%>
			
			<!-- =========오른쪽 버튼처리 시작 ==========-->
			
			<!-- 관리자로 로그인한 경우 (추첨) (등록) (목록)-->
			<!-- 관리자 버튼 시작 -->
			<div class="button">
				<c:if test="${!empty user_num && user_auth==9}">
					
						<!-- 로그인 + 관리자의 경우만 버튼이 보이도록 처리 -->
						<input type="button" value="종료이벤트" onclick="location.href='endEventList.do'" class="btn"  >
						<input type="button" value="등록" onclick="location.href='writeForm.do'" class="btn"  >
						<input type="button" value="목록" onclick="location.href='list.do'" class="btn"  > 
				</c:if>
				<!-- 관리자 버튼 끝 -->
				
				<!-- 관리자가 아닌 경우 (당첨확인) (목록) -->
				<c:if test="${user_auth !=9}"> <!-- 비회원인 경우 당첨확인 버튼 disabled처리 -->
					<input type="button" value="당첨확인" onclick="location.href='eventresult.do'" 
							  class="btn"  <c:if test="${empty user_num}">disabled="disabled"</c:if>>
					<input type="button" value="목록" onclick="location.href='list.do'" class="btn"  > 
				</c:if>
			</div>
			<!-- =========오른쪽 버튼처리 끝 =========-->
			<%-- 상품 목록 --%>
			<div class="event-list">
				<c:if test="${count == 0}">
					<div>
					아직 이벤트가 없네요!
				</div>
				</c:if>
				
				<c:forEach var="event" items="${list}"> 
					<ul >
						<li class="hover">
							<figure>
									<!-- 이벤트 사진을 누르면 상세글로 이동함 -->
								
									<a href="detail.do?event_num=${event.event_num}">
										<img src="${pageContext.request.contextPath}/upload/${event.event_photo}">
									</a> 
									<br>
									
								</a>
								<b>
								<!-- 이벤트 사진 아래 (진행중) (기간) -->
								<div class="event_footer">
									<div class="event-status">
										<div class="event-status-1">
											<c:if test="${event.event_status==1}">종료</c:if>
										</div>
										<div class="event-status-2">	
											<c:if test="${event.event_status==2}">진행중</c:if>
										</div>
									</div>
									
									<div class="event-date">
										${event.event_start} ~ ${event.event_end }
									</div>
								</div>
								</b>	
							</figure>
						</li>
					</ul>
				</c:forEach>
			</div>
			<%-- 상품 목록 끝 --%>
			
			<%--페이지 --%>
			<c:if test="${count != 0}">
				<div class="align-center">${page}</div>
			</c:if>
			
		</div>
		<%-- 내용끝 --%>
	</div>
</body>
</html>

