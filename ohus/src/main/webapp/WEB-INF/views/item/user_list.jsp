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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$('input[type="search"]').attr('placeholder','상품 검색');
			
			function searchData(){
				let data = $('input[type="search"]').val();
				location.href="userList.do?keyword="+data;
			};
			
			$('input[type="search"]').keypress(function(){
				if(event.keyCode==13){
					searchData();	
				}
				
			});
		});
	</script>
</head>
<body>
	<div class = "page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<jsp:include page="/WEB-INF/views/item/item_header.jsp"/>
		<%-- 내용시작 --%>
		<div class = "item-main">
			<%-- 상품 목록 --%>
			<div class="item-list">
				<c:if test="${count == 0}">
					<div>
					표시할 상품 없음
				</div>
				</c:if>
				<c:forEach var="item" items="${itemList}">
					<ul class="horizontal-list">
						<li class="hover">
							<figure>
								<a href="${pageContext.request.contextPath}/item/detail.do?item_num=${item.item_num}">
									<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" width="250" height="250"><br>
									${item.item_name}
									<br>
									<b><fmt:formatNumber value="${item.item_price}"/>원</b>
								</a>
							</figure>
						</li>
					</ul>
				</c:forEach>
			</div>
			<c:if test="${count != 0}">
			<div class="align-center">${page}</div>
			</c:if>
			<%-- 상품 목록 --%>
		</div>
		<%-- 내용끝 --%>
	</div>
</body>
</html>