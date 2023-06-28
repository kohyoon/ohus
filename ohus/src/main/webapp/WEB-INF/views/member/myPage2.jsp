<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의참여</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/myPage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<style>
.page-main{
   max-width: 1700px;
      margin: 0 auto;
      padding: 20px;
}
table {
  border: 1px #a39485 solid;
  font-size: .9em;
  box-shadow: 0 2px 5px rgba(0,0,0,.25);
  width: 70%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  margin : 0 auto;
}

th {
  text-align: center;
  background-color: #35c5f0;
  color: white;
  padding: 10px 0;
  border-bottom: 1px solid #fff; /* 상단에 흰색 선 추가 */
}

td,
th {
  padding: 1em .5em;
  vertical-align: middle;
  text-align: center;
  border-right: 1px solid white; /* 각 열에 세로 선 추가 */
}

td:last-child,
th:last-child {
  border-right: none; /* 마지막 열의 오른쪽 선 제거 */
}

td {
  border-bottom: 1px solid #a39485; /* 각 행에 가로 선 추가 */
  background: #fff;
}

</style>

<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/member/myPageheader.jsp"/>
		      <!-- 리뷰쓰기, 내가 작성한 리뷰, 응모내역 -->
		      
<!-- 응모내역 -->		      
<div class="page-main">
	 <c:if test="${!empty mylist}">
	 		<h4>이벤트 응모내역</h4>
			<table>
			<thead>
				<tr>
					<th>이벤트 번호</th>
					<th>댓글 번호</th>
					<th>댓글 등록일</th>
					<th>댓글내용</th>
					<th>당첨여부</th>
				</tr>
				</thead>
				<c:forEach var="reply" items="${mylist}">
					<tr> 
						<td><a href="${pageContext.request.contextPath}/event/detail.do?event_num=${reply.event_num}" target="_blank">${reply.event_num}</a></td>
						<td>${reply.re_num}</td>
						<td>${reply.re_date}</td>
						<td>${reply.re_content}</td>
						<td>
							<c:if test="${reply.event_winner==1 }">당첨!</c:if>
							<c:if test="${reply.event_winner==0 }">꽝!</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
			
		<c:if test="${empty mylist }">
			<p>댓글을 단 이벤트가 없습니다</p>
		</c:if>
</div>	
</body>
</html>