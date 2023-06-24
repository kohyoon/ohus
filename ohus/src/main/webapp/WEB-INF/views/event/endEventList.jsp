<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>종료 이벤트 목록</title>
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

		.event-count{
		float: left;
		margin-left: 7px;
		}
		
		.event-date{
			float:left;
			margin-left: 110px;
		}
		
		.event-go{
			float:right;
			margin-right: 10px;
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
			<form id="search_form" action="list.do" method="get" >
				<ul class="align-center">
					<li >
						<select name="keyfield"> 
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
							<option>
							<%--만약에 제목 검색에 a라고 적고 작성자로 옵션을 바꿔도 내용이 남아있도록 처리한 것! --%>
						</select>
					</li> 
					<li>
						<input type="search" size="30" name="keyword" id="keyword" value="${param.keyword}" >
					</li>
					<li>
						<input type="submit" value="검색" style="display: none;">
					</li>
				</ul>
			</form>
			<%-- 검색창 끝 --%>
			
			
			<%-- 상품 목록 --%>
			<div class="event-list">
				<c:if test="${count == 0}">
					<div>
						종료된 이벤트가 없네요!
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
								<!-- 이벤트 사진 하단 -->
								<div class="event_footer">
									<div class="event-count">
										당첨자 수 : ${event.winner_count}명
									</div>
									
									<div class="event-date">
										${event.event_start} ~ ${event.event_end }
									</div>
									
									
									<div class="event-go">
										<input type="button" value="추첨시작" onclick="location.href='playEvent.do?event_num=${event.event_num}'"">
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

