<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상추마켓 글목록</title>
	<link rel = "stylesheet" href = "${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css">
	<script type="text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
		$(function(){
			
			$('#search_form').submit(function(){
				if($('#keyword').val().trim() == ''){
					alert('검색어를 입력하세요.');
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
		<jsp:include page="/WEB-INF/views/member/adminPageheader.jsp"/>
		<%-- 내용 시작 --%>
		<p>
		<div class="admin-market-submit">
			<%-- 검색창 시작 --%>
			<form id="search_form" action="adminPageMarket.do" method="get">
				<h1>상추마켓 글목록</h1>
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
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
			<%-- 검색창 끝 --%>
			<!-- 글번호, 제목, 내용, 조회수, 수정일, 상태, photo1,2,mem_num, id  == list-->
			<div class="align-center">
				
				<button class="list-btn ad-list" type="button"  onclick="location.href='adminPageMarket.do'">
					글목록
				</button>
				<button class="list-btn ad-del" type="button" onclick = "location.href = '${pageContext.request.contextPath}/main/main.do'">
					홈으로
				</button>
			</div>
			<p>
			
			<%-- 상품 목록 --%>
			<c:if test="${count == 0}">
				<div class = "result-display">
					상추글이 없네요!
				</div>
			</c:if>
			
			<c:if test="${count > 0}">
				<table id="ad_table">
					<tr>
						<th>글번호</th>
						<th>글제목</th>
						<th>등록일</th>
						<th>상태</th>
						<th>삭제</th>
					</tr> 
					<!-- 0 이 판매중, 1이 거래완료 야 -->
					<c:forEach var = "market" items = "${list}">
						<tr>
							<td>${market.market_num}</td>
							<td><a href = "${pageContext.request.contextPath}/market/detail.do?market_num=${market.market_num}">${market.market_title}</a></td>
							<td>${market.market_regdate}</td>
							<td>
								<c:if test="${market.market_status==0 }">판매중</c:if>
								<c:if test="${market.market_status!=0 }">거래완료</c:if>
							</td>
							<td>
								<input class="footer-button" type="button" value="삭제하기" id="delete_btn"
										   onclick = "location.href = '${pageContext.request.contextPath}/market/delete.do?market_num=${market.market_num}'">
							</td>
							
						</tr>
					</c:forEach>
				</table>
				<p>
				<div class = "align-center">${page}</div>
			</c:if>
			<%-- 상품 목록 --%>
		</div>
		<%-- 내용 끝 --%>
	</div>
</body>
</html>