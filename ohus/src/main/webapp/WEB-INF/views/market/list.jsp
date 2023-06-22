<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상추 메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/market_list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim() == ''){
				alert('검색어를 입력하세요');
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
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>상추 메인</h2>
		<!-- 검색창 시작 -->
		<form id="search_form" action="list.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>제목+내용</option>
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
		<!-- 검색창 끝 -->
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