<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CART</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sys/list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		// 장바구니 상품 삭제 이벤트 연결
		$('.cart-del').on('click', function(){
			$.ajax({
				url : 'deleteCart.do',
				type : 'post',
				data : {cart_num:$(this).attr('data-cartnum')},
				dataType : 'json',
				success : function(param){
					if(param.result == 'logout') {
						alert('로그인 후 사용하세요!');
					} else if(param.result == 'success') {
						alert('선택하신 상품을 삭제했습니다.');
						location.href='list.do';
					} else {
						alert('장바구니 상품 삭제 오류 list.jsp로 오세요.');
					}
				},
				error : function(){
					alert('네트워크 오류 발생');
				}
			})
		})
		// 장바구니 상품 수량 변경 이벤트 연결
		$('.cart-modify').on('click', function(){
			let input_quantity = $(this).parent().find('input[name="order_quantity"]');
			if(input_quantity.val()==''){
				alert('수량을 입력하세요.');
				input_quantity.focus();
				return;
			}
			if(input_quantity.val() < 1){
				alert('상품의 최소 수량은 1입니다.'); // 태그에 명시한 변경전 value값을 읽어옴
				input_quantity.val(input_quantity.attr('value'));
				return;
			}
			
			// 서버와 통신
			$.ajax({
				url : 'modifyCart.do',
				type : 'post',
				data : {cart_num:$(this).attr('data-cartnum'),item_num:$(this).attr('data-itemnum'),order_quantity:input_quantity.val()},
				dataType : 'json',
				success : function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요.');
					} else if(param.result == 'noSale') {
						alert('판매가 중지되었습니다.');
						location.href='list.do';
					} else if(param.result == 'noQuantity') {
						alert('상품의 수량이 부족합니다.');
						location.href='list.do';
					} else if(param.result == 'success') {
						alert('상품의 개수가 수정되었습니다.');
						location.href='list.do';
					} else {
						alert('장바구니 상품 개수 수정 오류 list.jsp로 오세요.');
					}
				},
				error : function(){
					alert('네트워크 오류 발생');
				}
			})
		})
	})
</script>
</head>
<body>
<div class = "page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class = "content-main">
		<h2>장바구니</h2>
		<c:if test = "${empty list}">
		<div class = "result-display">
			장바구니에 담은 상품이 없습니다.
		</div>
		</c:if>
		<c:if test = "${!empty list}">
		<!-- 장바구니 화면 -->
		<form id = "cart_order" action = "${pageContext.request.contextPath}/order/orderForm.do" method = "post">
			<table>
				<tr>
					<th>상품명</th>
					<th>수량</th>
					<th>상품가격</th>
					<th>합계</th>
				</tr>
				<!-- ${list}를 어디서 받아오는 것입니까 ? -->
				<c:forEach var = "cart" items = "${list}">
				<tr>
					<td>
						<a href = "${pageContext.request.contextPath}/item/detail.do?item_num=${cart.item_num}">
							<img src = "${pageContext.request.contextPath}/upload/${cart.itemVO.item_photo1}" width = "80"> <!-- 사진 크기 -->
							${cart.itemVO.item_name}
						</a>
					</td>
					<td class = "align-center">
						<%-- 상품 판매 가능 여부 : 미표시 or 상품 재고 < 주문할 상품의 수량 --%>
						<c:if test="${cart.itemVO.item_status==1 or cart.itemVO.item_stock < cart.order_quantity}">[판매중지]</c:if>
						<%-- 상품 판매 가능 여부 : 표시 or 상품 재고 >= 주문할 상품의 수량 --%>
						<c:if test="${cart.itemVO.item_status==2 and cart.itemVO.item_stock >= cart.order_quantity}">
							<input type = "number" name = "order_quantity" min = "1" max = "${cart.itemVO.item_stock}" autocomplete = "off" value = "${cart.order_quantity}">
							<br>
							<input type = "button" value = "변경" class = "cart-modify" data-cartnum = "${cart.cart_num}" data-itemnum = "${cart.item_num}">
						</c:if>
					</td>
					<td class = "align-center">
						<fmt:formatNumber value = "${cart.itemVO.item_price}"/>원
					</td>
					<td class = "align-center">
						<fmt:formatNumber value = "${cart.sub_total}"/>원
						<br>
						<input type = "button" value = "삭제" class = "cart-del" data-cartnum = "${cart.cart_num}">
					</td>
				</tr>
				</c:forEach>
				<tr>
					<td colspan = "3" class = "align-center"><b>총구매금액</b></td>
					<td><fmt:formatNumber value = "${all_total}"/>원</td>
				</tr>
			</table>
			<div class = "align-center cart-submit">
				<input type = "submit" value = "구매하기">
			</div>
		</form>
		</c:if>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>







