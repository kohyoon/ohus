<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의참여</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/table.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/myPage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<style>
.tables-container {
  display: flex;
}

.table-container {
  width: 100%;
  box-sizing: border-box;
  margin-right: 170px;
  margin-left: 20px;
  flex-grow: 10;
}

.table-container h3 {
  text-align: left;
  margin-left: 40px;
}

.table-container table {
  width: 130%;
}

.table-container th,
.table-container td {
  padding: 5px;
  border: 1px solid #ddd;
}


</style>

<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/member/myPageheader.jsp"/>
		      <!-- 리뷰쓰기, 내가 작성한 리뷰, 응모내역 -->

<!-- 내가 작성한 리뷰 -->		 
<div class="page-main">
<div class="content-main">
<div class="tables-container">
	<div class="table-container">
	 		<h3>내가 작성한 리뷰</h3>
	 		<c:if test="${!empty itemList }">
			<table>
			<thead>
				<tr>
					<th>상품번호</th>
					<th>리뷰내용</th>
					<th>리뷰등록일</th>
				</tr>
				</thead>
				<c:forEach var="review" items="${itemList}">
					<tr> 
						<td><a href="${pageContext.request.contextPath}/item/detail.do?item_num=${review.item_num}" >${review.item_num}</a></td>
						<td>${review.review_content}</td>
						<td>${review.review_regdate}</td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
			
			<c:if test="${empty itemList }">
	    		<span class="no-list">작성한 리뷰가 없습니다!</span>
	    	</c:if>
			
			
	</div>
	<!-- 응모내역 -->	
	<div class="table-container">	      
	 		<h3>이벤트 응모내역</h3>
	 		<c:if test="${!empty mylist }">
			<table>
				<thead>
					<tr>
						<th>이벤트 번호</th>
						<th>댓글 번호</th>
						<th>댓글 등록일</th>
						<th>댓글내용</th>
					</tr>
				</thead>
					<c:forEach var="reply" items="${mylist}">
						<tr> 
							<td><a href="${pageContext.request.contextPath}/event/detail.do?event_num=${reply.event_num}">${reply.event_num}</a></td>
							<td>${reply.re_num}</td>
							<td>${reply.re_date}</td>
							<td>${reply.re_content}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			
			<c:if test="${empty mylist }">
	    		<span class="no-list">이벤트 응모내역이 없습니다!</span>
	    	</c:if>
			
	</div>	
</div>
</div>	
</div>	
</body>
</html>