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
	<script type="text/javascript" src = "${pageContext.request.contextPath}/js/item.fav.js"></script>
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
				if(Number($('#item_stock').val()) < $('#order_quantity').val()){
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
	<style type="text/css">
		.content-main{
			width: 680px;
			margin 0 auto;
		}
		.btn {
		  margin: 1rem;
		  padding: 0.5rem 1rem;
		  font-size: 1.3rem;
		  font-weight: 500;
		  border-radius: 4px;
		  text-align: center;
		  text-decoration: none;
		  cursor: pointer;
		  transition: background-color 0.3s ease-in-out;
		  width: 200px;
		  height: 60px;
		  
		  &:hover {
		    filter: brightness(90%);
		  }
		}

		.btn-cart {
		  background-color: white;
		  color: #35c5f0;
		  border-color: #35c5f0;
		  box-shadow: 0 0px 0px rgba(53, 196, 240);
		}
		
		.btn-buy {
		  background-color: #35c5f0;
		  border:none;
		  color: #fff;
		}
	</style>
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
									<img id="output_fav" data-num="${item.item_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
									<span id="output_fcount"></span>회
								</li>
								<li>
									<span id="item_total_txt">총 주문 금액 : 0원</span>
								</li>
								<li>
									<button class="btn btn-cart" type="submit">
									    장바구니
									</button>
									<button class="btn btn-buy" type="submit">
										바로 구매
									</button>
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
				<a href="#item_detail">상품정보</a> 
				<a href="#item_review">리뷰 <small>${reviewCount}개</small></a> 
				<a href="#item_inquiry">문의</a>
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
		<%-- 내용 끝 --%>
	</div>
</body>
</html>