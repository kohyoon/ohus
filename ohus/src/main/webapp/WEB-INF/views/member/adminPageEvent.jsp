<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/table.css">
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


.event-list ul:hover img {
    opacity: 0.7;
  }

  .event-list ul img {
    transition: opacity 0.3s ease;
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
<jsp:include page="/WEB-INF/views/member/adminPageheader.jsp"/>
	<%-- 내용시작 --%>
	<div class = "event-main">
		<%-- 검색창 시작 --%>
		<form id="search_form" action="list.do" method="get">
			<ul ul class="align-center">
				<li>
				<select name="keyfield"> <
					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
					<option>
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
		
		<%-- 상품 목록 --%>
			<div class="event-list">
				<c:if test="${count == 0}">
					<div>
						아직 이벤트가 없네요!
					</div>
				</c:if>
				<c:if test="${count>0 }"> 
					<table>
						<thead>
						<tr>
							<th>이벤트번호</th>
							<th>이벤트제목</th>
							<th>이벤트기간</th>
							<th>당첨자수</th>
							<th>이벤트상태</th>
						</tr>
						</thead>
					
					<c:forEach var="event" items="${list}"> 
						<tr> 
							<td>${event.event_num}</a></td>
							<td><a href="${pageContext.request.contextPath}/event/detail.do?event_num=${event.event_num}" target="_blank">${event.event_title}</td>
							<td>${event.event_start} ~ ${event.event_end }</td>
							<td>${event.winner_count}명</td>
							<td> <!-- 종료+추첨완료 = 추첨결과페이지, 종료+추첨미완료 = 추첨시작 -->
								<c:if test="${event.event_status==1 }">종료
									<c:if test="${event.event_check==0 }">
										<input type="button" value="추첨시작" onclick="location.href='${pageContext.request.contextPath}/event/playEvent.do?event_num=${event.event_num}'">
									</c:if>
									<c:if test="${event.event_check==1 }">
										<input type="button" value="추첨결과" onclick="location.href='${pageContext.request.contextPath}/event/playEventResult.do?event_num=${event.event_num}'">
									</c:if>
								</c:if>
								<c:if test="${event.event_status!=1 }">진행중</c:if>
							</td>
						</tr>
					</c:forEach>	
				</table>	
			</c:if>
		</div>
	</div>
	<%-- 내용끝 --%>
</div>	
</body>
</html>



