<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${item.item_name} | 내일의 집</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<script type="text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$('#order_quantity').on('keyup mouseup', function(){
				if($('#order_quantity').val() == ''){
					$('#item_total_txt').text('총 주문 금액 : 0원');
					return;
				}
				if($('#order_quantity').val() <= 0){
					$('#order_quantity').val('');
					return;
				}
				if(Number($('#item_quantity').val()) < $('#order_quantity').val()){
					alert('수량이 부족합니다.');
					$('#order_quantity').val('');
					$('#item_total_txt').text('총 주문 금액 : 0원');
					return;
				}
				
				//총 주문 금액 표시
				let total = $('#item_price').val() * $('#order_quantity').val();
				$('#item_total_txt').text('총 주문 금액 : ' + total.toLocaleString() + '원');//숫자 세자리마다 쉼표처리
			});
			
			//장바구니에 상품을 담기 위한 이벤트 처리
			$('#item_cart').submit(function(){
				//기본 이벤트 제거
				event.preventDefault();
				if($('#order_quantity').val() == ''){
					alert('수량을 입력하세요.');
					$('#order_quantity').focus();
					return false;
				}
				let form_data = $(this).serialize();
				$.ajax({
					url:'../cart/write.do',
					type:'post',
					data:form_data,
					dataType:'json',
					success:function(param){
						if(param.result == 'logout'){
							alert('로그인 필요');
						}else if(param.result == 'success'){
							alert('장바구니에 담았습니다.');
							location.href='../cart/list.do';
						}else if(param.result == 'over_quantity'){
							alert('수량 초과');
						}else{
							alert('장바구니 오류');
						}
					},
					error:function(){
						alert('네트워크 오류 발생');
					}
				})
			});
			
		});
	</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<jsp:include page="/WEB-INF/views/item/item_header.jsp"/>
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
				<div>
					<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" width="400">
				</div>
				<h1>${item.item_name}</h1>
				<div>
					<form id="item_cart">
						<input type="hidden" name="item_num" value="${item.item_num}" id="item_num">
						<input type="hidden" name="item_price" value="${item.item_price}" id="item_price">
						<input type="hidden" name="item_stock" value="${item.item_stock}" id="item_stock">
						<ul>
							<li>가격 : <b><fmt:formatNumber value="${item.item_price}"/>원</b></li>
							<li>남은 수량 : <span><fmt:formatNumber value="${item.item_stock}"/></span></li>
							<li>조회수 : <fmt:formatNumber value="${item.item_hit}"/>회</li>
							<c:if test="${item.item_stock > 0}">
								<li>
									<label for="order_quantity">구매 수량 :</label>
									<input type="number" name="order_quantity" 
									min="1" max="${item.item_stock}" 
									autocomplete="off" id="order_quantity">
								</li>
								<li>
									<span id="item_total_txt">총 주문 금액 : 0원</span>
								</li>
								<li>
									<input type="submit" value="장바구니에 담기">
									<input type="submit" value="바로 구매">
								</li>
							</c:if>
							<c:if test="${item.item_stock <= 0}">
								<li>
									<span>품절</span>
								</li>
							</c:if>
						</ul>
					</form>
				</div>
				<div>
				<hr size="1" noshade="noshade" width="95%">
				<a href="#item_detail">상품정보</a> <a href="#item_review">리뷰</a> <a href="#item_inquiry">문의</a>
				<hr size="1" noshade="noshade" width="95%">
				</div>
				<p>
				<div id="item_detail">
				<img src="${pageContext.request.contextPath}/upload/${item.item_photo2}" width="400">
				<img src="${pageContext.request.contextPath}/upload/${item.item_photo3}" width="400">
				<p>
					${item.item_content}
				</p>	
				</div>
			</c:if>
		</div>
		<hr size="1" noshade="noshade" width="95%">
		<div id="item_review">
			리뷰는 여기 있습니다.
		</div>
		<hr size="1" noshade="noshade" width="95%">	
		<div id="item_inquiry">
			문의는 여기 있습니다.
		</div>
		<!-- 하단 내용 시작 -->
		<footer class="footer">
			<div class="footer-upper">
				<div>
					<h4>
						<a href="${pageContext.request.contextPath}/inquiry/listInquiry.do">고객센터</a>
					</h4>
					<a class="footer-number" href="">1670-1234</a> 
					<span>평일 09:00 ~ 18:00 (주말 & 공휴일 제외)</span>
				</div>
			</div>
			<ul class="footer-lower">
				<li><a href="">브랜드 스토리</a></li>
				<li><a href="">회사소개</a></li>
				<li><a href="">채용정보</a></li>
				<li><a href="">이용약관</a></li>
				<li><a href="">개인정보처리방침</a></li>
				<li><a href="">공지사항</a></li>
				<li><a href="">고객센터</a></li>
				<li><a href="">고객의 소리</a></li>
				<li><a href="">전문가 등록</a></li>
			</ul>
		</footer>
		<!-- 하단 내용 끝 -->
		<%-- 내용 끝 --%>
	</div>
</body>
</html>