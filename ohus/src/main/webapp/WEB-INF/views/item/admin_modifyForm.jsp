<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 수정</title>
	<link rel = "stylesheet" href = "${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css">
	<script type="text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$('#write_form').submit(function(){
				if($('#name').val().trim() == ''){
					alert('상품명 미입력');
					$('#name').val('').focus();
					return false;
				}
				
				if($('#price').val() == ''){
					alert('가격 미입력');
					$('#price').focus();
					return false;
				}
				
				if($('#quantity').val() == ''){
					alert('재고 수량 미입력');
					$('#quantity').focus();
					return false;
				}
				
				if($('#detail').val().trim() == ''){
					alert('상품 설명 미입력');
					$('#detail').val().focus();
					return false;
				}
			});
		});
	</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용 시작 --%>
		<p>
		<div class="admin-item-submit">
			<form action="modify.do" method="post" enctype="multipart/form-data" id="write_form" class="write-form">
				<input type="hidden" name="item_num" value="${item.item_num}">
				<ul>
					<li><h1>상품 수정</h1></li>
					<li>
						<label>상품 판매 가능 여부</label>
						<input type="radio" name="item_status" value="1" id="status1" <c:if test="${item.item_status == 1}">checked</c:if>>미표시
						<input type="radio" name="item_status" value="2" id="status2" <c:if test="${item.item_status == 2}">checked</c:if>>표시
					</li>
					<li>
						<label for="item_name">상품명</label>
						<input type="text" name="item_name" id="item_name" maxlength="10" value="${item.item_name}">
					</li>
					<li>
						<label>카테고리</label>
						<input type="radio" name="item_category" value="1" id="category1" <c:if test="${item.item_category == 1}">checked</c:if>>침대
						<input type="radio" name="item_category" value="2" id="category2" <c:if test="${item.item_category == 2}">checked</c:if>>소파
						<input type="radio" name="item_category" value="3" id="category3" <c:if test="${item.item_category == 3}">checked</c:if>>수납
						<input type="radio" name="item_category" value="4" id="category4" <c:if test="${item.item_category == 4}">checked</c:if>>테이블
						<input type="radio" name="item_category" value="5" id="category5" <c:if test="${item.item_category == 5}">checked</c:if>>의자
					</li>
					<li>
						<label for="item_price">가격</label>
						<input type="number" name="item_price" id="item_price" min="1" max="99999999" value="${item.item_price}">원
					</li>
					<li>
						<label for="item_content">상품상세정보</label>
						<textarea rows="5" cols="60" name="item_content" id="item_content">${item.item_content}</textarea>
					</li>
					<li>
						<label for="item_stock">재고</label>
						<input type="number" name="item_stock" id="item_stock" min="0" max="999" value="${item.item_stock}">
					</li>
					<li>
						<label for="item_origin">원산지</label>
						<input type="text" name="item_origin" id="item_origin" maxlength="45" value="${item.item_origin}">
					</li>
					<li>
						<label for="item_photo1">상품대표사진</label>
						<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" data-img="${item.item_photo1}" width="50" height="50" class="my-photo">
						<br>
						<input type="file" name="item_photo1" id="item_photo1" accept="image/gif, image/png, image/jpeg" class="form-notice">
					</li>
					<li>
						<label for="item_photo2">상품사진1</label>
						<img src="${pageContext.request.contextPath}/upload/${item.item_photo2}" data-img="${item.item_photo2}" width="50" height="50" class="my-photo">
						<br>
						<input type="file" name="item_photo2" id="item_photo2" accept="image/gif, image/png, image/jpeg" class="form-notice">
					</li>
					<li>
						<label for="item_photoe">상품사진2</label>
						<img src="${pageContext.request.contextPath}/upload/${item.item_photo3}" data-img="${item.item_photo3}" width="50" height="50" class="my-photo">
						<br>
						<input type="file" name="item_photo3" id="item_photo3" accept="image/gif, image/png, image/jpeg" class="form-notice">
					</li>
				</ul>
				<div class="align-center">
					<button class="ad-btn ad-regi" type="submit">
						상품 수정
					</button>
					<button class="ad-btn ad-list" type="button"  onclick="location.href='list.do'">
						상품 목록
					</button>
					<button class="ad-btn ad-del" type="button"  onclick="location.href='delete.do?item_num=${item.item_num}'">
						상품 삭제
					</button>
				</div>
			</form>
		</div>
		<%-- 내용 끝 --%>
	</div>
</body>
</html>