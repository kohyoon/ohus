<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 목록</title>
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
		<%-- 내용 시작 --%>
		<p>
		<div class="admin-item-submit">
			<%-- 검색창 시작 --%>
			<form id="search_form" action="list.do" method="get">
				<h1>상품 목록</h1>
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>상품명</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
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
			<div class="align-center">
				<button class="list-btn ad-regi" type="button"  onclick="location.href='writeForm.do'">
					상품 등록
				</button>
				<button class="list-btn ad-list" type="button"  onclick="location.href='list.do'">
					상품 목록
				</button>
				<button class="list-btn ad-del" type="button" onclick = "location.href = '${pageContext.request.contextPath}/main/main.do'">
					홈으로
				</button>
			</div>
			<p>
			<%-- 상품 목록 --%>
			<c:if test="${count == 0}">
				<div class = "result-display">
					표시할 상품 없음
				</div>
			</c:if>
			<c:if test="${count > 0}">
				<table id="ad_table">
					<tr>
						<th>번호</th>
						<th>상품명</th>
						<th>가격</th>
						<th>재고</th>
						<th>등록일</th>
						<th>상태</th>
					</tr>
					<c:forEach var = "item" items = "${list}">
						<tr>
							<td>${item.item_num}</td>
							<td><a href = "modifyForm.do?item_num=${item.item_num}">${item.item_name}</a></td>
							<td><fmt:formatNumber value="${item.item_price}"/>원</td>
							<td><fmt:formatNumber value="${item.item_stock}"/></td>
							<td>${item.item_regdate}</td>
							<td>
								<c:if test="${item.item_status == 1}">미표시</c:if>
								<c:if test="${item.item_status == 2}">표시</c:if>
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
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>