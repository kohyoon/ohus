<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<style type="text/css">
		.item-container {
    		display: flex;
   			flex-wrap: wrap;
    		align-items: flex-start;
    		height: 100%;
    		margin: 0;
    		display: flex;
    		justify-content: center;
    		align-items: center;
		}

		.item {
   			width: 250px; /* Adjust the width as needed */
    		margin: 10px;
		}
		.align-center {
    		display: flex;
    		justify-content: center;
		}
	</style>
</head>
<body>
	<div class = "page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<jsp:include page="/WEB-INF/views/item/item_header.jsp"/>
		<%-- 내용시작 --%>
		<div class = "content-main">
			<%-- 상품 목록 --%>
			<div class="item-container">
				<c:if test="${count == 0}">
					<div>
					표시할 상품 없음
				</div>
				</c:if>
				<c:forEach var="item" items="${itemList}">
					<div class="item">
						<a href="${pageContext.request.contextPath}/item/detail.do?item_num=${item.item_num}">
							<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" width="250"><br>
							${item.item_name}
							<br>
							<b><fmt:formatNumber value="${item.item_price}"/>원</b>
						</a>
					</div>
				</c:forEach>
			</div>
			<c:if test="${count != 0}">
			<div class = "align-center">${page}</div>
			</c:if>
			<%-- 상품 목록 --%>
		</div>
		<%-- 내용끝 --%>
	</div>
</body>
</html>