<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
	<link rel = "stylesheet" href = "${pageContext.request.contextPath}/css/style.css">
	<script type="text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용 시작 --%>
		<div class="content-main">
			<h2>상품 등록</h2>
			<form action="write.do" method="post" enctype="multipart/form-data" id="write_form">
				<ul>
					<li>
						<label>상품 판매 가능 여부</label>
						<input type="radio" name="item_status" value="1" id="status1">미표시
						<input type="radio" name="item_status" value="2" id="status2">표시
					</li>
					<li>
						<label for="item_name">상품명</label>
						<input type="text" name="item_name" id="item_name" maxlength="200">
					</li>
					<li>
						<label>카테고리</label>
						<input type="radio" name="item_category" value="0" id="category0">침대
						<input type="radio" name="item_category" value="1" id="category1">소파
						<input type="radio" name="item_category" value="2" id="category2">수납
						<input type="radio" name="item_category" value="1" id="category3">테이블
						<input type="radio" name="item_category" value="0" id="category4">의자
					</li>
					<li>
						<label for="item_price">가격</label>
						<input type="number" name="item_price" id="item_price" min="1" max="999999999">
					</li>
					<li>
						<label for="item_content">상품상세정보</label>
						<textarea rows="5" cols="30" name="item_content" id="item_content"></textarea>
					</li>
					<li>
						<label for="item_stock">재고</label>
						<input type="number" name="item_stock" id="item_stock" min="0" max="999">
					</li>
					<li>
						<label for="item_origin">원산지</label>
						<input type="text" name="item_origin" id="item_origin" maxlength="45">
					</li>
					<li>
						<label for="item_photo1">상품대표사진</label>
						<input type="file" name="item_photo1" id="item_photo1" accept="image/gif, image/png, image/jpeg">
					</li>
					<li>
						<label for="item_photo2">상품사진1</label>
						<input type="file" name="item_photo2" id="item_photo2" accept="image/gif, image/png, image/jpeg">
					</li>
					<li>
						<label for="item_photo3">상품사진2</label>
						<input type="file" name="item_photo3" id="item_photo3" accept="image/gif, image/png, image/jpeg">
					</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="등록">
					<input type="button" value="목록" onclick="location.href='list.do'">
				</div>
			</form>
		</div>
		<%-- 내용 시작 --%>
	</div>
</body>
</html>