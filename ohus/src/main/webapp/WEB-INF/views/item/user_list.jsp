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
</head>
<body>
	<div class = "page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용시작 --%>
		<div class = "content-main">
			<%-- 상품 목록 --%>
			<h4>최신 상품</h4>
			<div class="image-space">
				<c:forEach var="item" items="${itemList}">
					<div class="horizontal-area">
						<a href="${pageContext.request.contextPath}/item/detail.do?item_num=${item.item_num}">
							<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}">
							<span>${item.item_name}</span>
							<br>
							<b><fmt:formatNumber value="${item.item_price}"/>원</b>
						</a>
					</div>
				</c:forEach>
				<div class="float-clear">
					<hr width="100%" size="1" noshade="noshade">
				</div>
			</div>
			<%-- 상품 목록 --%>
		</div>
		<%-- 내용끝 --%>
	</div>
</body>
</html>