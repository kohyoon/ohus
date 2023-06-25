<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>


<body>
<li>
	<a>주문내역조회</a>
	<div class="page-main">
	<h3>상품구매목록 <input type = "button" value = "구매목록보기" onclick = "location.href='${pageContext.request.contextPath}/order/orderList.do'"> </h3>
			<ul>
				<li>
					<c:forEach var = "order" items = "${orderList}">
						<div>
							<a href = "${pageContext.request.contextPath}/order/orderModifyForm.do?order_num=${order.order_num}">
								${order.order_num} ${order.item_name}(${order.order_regdate})	
								<c:if test = "${order.order_status == 1}">배송대기</c:if>
								<c:if test = "${order.order_status == 2}">배송준비중</c:if>
								<c:if test = "${order.order_status == 3}">배송중</c:if>
								<c:if test = "${order.order_status == 4}">배송완료</c:if>
								<c:if test = "${order.order_status == 5}">주문취소</c:if>
							</a>
						</div>
					</c:forEach>
				</li>
			</ul>

</div>
</li>




	      <li><a>찜한상품</a></li>
	      <li><a>공지사항</a></li>
	      <li><a>고객센터</a></li>
</body>
</html>