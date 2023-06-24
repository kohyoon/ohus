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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
	
	<style type="text/css">
		.item-main {
  			padding: 0;
 			margin: 0;
  			box-sizing: border-box;
		}

		.item-list {
  			display: flex;
  			flex-wrap: wrap;
  			justify-content: flex-start;
		}

		.item-list > ul {
		  list-style: none;
		  width: 280px; /* 원하는 너비로 조정하세요 */
		  margin-left: 20px;
		  margin-top: 20px;
		  padding: 15px;
		  border: 1px solid #CCC;
		  box-sizing: border-box;
		  display: flex; /* Flexbox를 사용하여 정렬 */
  		  justify-content: center; /* 수평 가운데 정렬 */
          align-items: center; /* 수직 가운데 정렬 */
		}
		
		.item-list > ul:hover {
		  border-color: #35c5f0;
		}
		
		.horizontal-list {
		  list-style-type: none;
		  display: flex;
		  flex-wrap: wrap;
		}
		.align-center{
			text-align:center;
		}
		
	</style>
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