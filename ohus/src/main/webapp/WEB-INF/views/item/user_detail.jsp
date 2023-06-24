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
	<style type="text/css">
		.content-main{
			padding:20px 60px;
			margin: 0 auto;
		}
		.imagedetail{
			width: 1200px;
			margin: 0 auto;
		}
		.imagedetail div{
			display: inline-block; /* 가로로 나열하기 위해 block레벨 요소를 inline-block레벨로 전환 */
			width: 550px; /* 영역 크기 지정 안해주면 inline 레벨처럼 내용물 만큼만 차지하기 때문에 크기 지정 */
            height: 550px; /* 영역 크기 지정 안해주면 inline 레벨처럼 내용물 만큼만 차지하기 때문에 크기 지정 */
		}
		.item-detail{
			float:right;
		}
		#price{
			font-size: 35px;
			font-weight:bold;
		}
		#item_total_txt{
			color:#000;
			font-weight:bold;
			text-align:right;
			font-size: 30px;
		}
		.item-detail li{
			padding:0 0 5px 0;
			font-size: 20px;
		}
		#reviewCount{
			color: #35c5f0;
		}
		.item-detail form{
			border:none;
			margin:0;
		}
		
		/* 버튼
		---------------------*/
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
		.btn-cart{
			background-color: white;
			color: #35c5f0;
			border: solid #35c5f0;
		}
		
		.btn-buy {
		  background-color: #35c5f0;
		  border:none;
		  color: #fff;
		}
		
	/* 네비게이션 하단 영역 ---------------------------------------------------*/
	.inner {
	  display: flex;
	  height: 40px;
	}
	
	.inner nav {
	  display: flex;
	  flex-flow: row nowrap;
	  justify-content: flex-start;
	  color: #424242;
	}
	
	.lower__item {
	  font-size: 1.2rem;
	  font-weight: 700;
	  color: #424242;
	  cursor: pointer;
	  /* margin: 8px 0; */
	  padding: 11px 10px;
	  /* position: relative; */
	  flex-shrink: 0;
	}
	
	.lower__item:hover {
	  color: #35c5f0;
	}
	
	.lower__item.active {
	  border-bottom: 2px solid #35c5f0;
	}
	
	.lower .inner div {
	  display: none;
	}
	
	/* 상세정보 ---------------------------------------------------*/
	#item_detail{
		padding:20px 60px;
		margin: 0 auto;
		float: center;
		width:1000px;
	}
	#detail_content{
		
	}
	</style>
</head>
<body>
	<div>
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
				<div class="imagedetail">
					<div class="item-image">
						<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" width="550" height="550">
					</div>
					<div class="item-detail"><br><br>
						
						<form id="item_cart">
							<input type="hidden" name="item_num" value="${item.item_num}" id="item_num">
							<input type="hidden" name="item_price" value="${item.item_price}" id="item_price">
							<input type="hidden" name="item_stock" value="${item.item_stock}" id="item_stock">
							<ul>
								<li><h1>${item.item_name}</h1></li>
								<li id="price"><b><fmt:formatNumber value="${item.item_price}"/>원</b></li>
								<li>남은 수량 : <span><fmt:formatNumber value="${item.item_stock}"/></span></li>
								<li>조회수 : <fmt:formatNumber value="${item.item_hit}"/>회</li>
								<li>&#11088; :  <span id="reviewCount">${reviewCount}개 리뷰</span></li>
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
									<li id="item_total_txt">총 주문 금액 : 0원</li>
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
				</div>
				<p>
			</c:if>
				<hr size="1" noshade="noshade" color="#ededed" width="100%">
					<div class="lower">
						<div class="low-inner">
							<nav>
								<a class="lower__item" href="#item_detail">상품정보</a> 
								<a class="lower__item" href="#item_review">리뷰 <small>${reviewCount}개</small></a> 
								<a class="lower__item" href="#item_inquiry">문의</a>
							</nav>
						</div>
					</div>
				<hr size="1" noshade="noshade" color="#ededed" width="100%">
				<p>
				<div id="item_detail">
					<img src="${pageContext.request.contextPath}/upload/${item.item_photo2}" width="1000" height="1000"><br>
					<img src="${pageContext.request.contextPath}/upload/${item.item_photo3}" width="1000" height="1000"><br>
					<p id="detail-content">
						${item.item_content}
					</p>	
				</div>
			<hr size="1" noshade="noshade" width="100%">
			<div id="item_review">
				<h2>상품 리뷰</h2>
				리뷰는 여기 있습니다.
			</div>
			<hr size="1" noshade="noshade" width="100%">	
			<div id="item_inquiry">
				<h2>상품 문의</h2>
				문의는 여기 있습니다.
			</div>
		</div>
		<%-- 내용 끝 --%>
	</div>
</body>
</html>