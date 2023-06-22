<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상추 마켓</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/market_list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<div class="page-title">
			<img src="${pageContext.request.contextPath}/upload/lettuce.png" width="100px" height="100px">
			<h2> 상추 마켓</h2>
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			등록된 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<div class="content-wrap">
			<c:forEach var="market" items="${list}">
			<div class="box-item">
				<a href="detail.do?market_num=${market.market_num}">
					<div class="img-box">
						<img src="${pageContext.request.contextPath}/upload/${market.market_photo1}" width="250" height="250" class="my-photo"><br>
					</div>
						<h3 class="box-title">${market.market_title}</h3>
						<div class="box-writer">
							${market.id}
						</div>
						<div class="box-date-hit">
							${market.market_regdate} ${market.market_hit}
						</div>
				</a>
			</div>
			</c:forEach>
		</div>
		<div class="page">${page}</div>
		</c:if>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>