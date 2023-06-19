<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<script type="text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용 시작 --%>
		<div class="content-main">
			<c:if test="${item.item_status == 1}">
				<div class="result-display">
					<div class="align-center">
						판매 중지 상품
						<p>
						<input type="button" value="판매상품 보기" onclick="location.href='itemList.do'">
					</div>
				</div>
			</c:if>
			<c:if test="${item.item_status == 2}">
				<h3>${item.item_name}</h3>
				<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" width="400"><br>
				${item.item_name}<br>
				가격 : <b><fmt:formatNumber value="${item.item_price}"/></b>원<br>
				남은 수량 : <b><fmt:formatNumber value="${item.item_stock}"/></b>개<br>
				원산지 : ${item.item_origin}<br>
				<input type="button" value="장바구니" onclick="location.href=''">
				<input type="button" value="구매" onclick="location.href=''">
				<hr size="1" noshade="noshade" width="100%">
				<img src="${pageContext.request.contextPath}/upload/${item.item_photo2}" width="400"><br>
				<img src="${pageContext.request.contextPath}/upload/${item.item_photo3}" width="400"><br>
				<p>
					${item.item_content}
				</p>
			</c:if>
		</div>
		<%-- 내용 끝 --%>
	</div>
</body>
</html>