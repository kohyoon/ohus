<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${item_name} | 후기 작성</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용시작 --%>
		<p>
		<div class="item-review">
			<form action="review.do" method="post" enctype="multipart/form-data" id="review_form" class="review-form">
				<div>
					<a href="javascript:window.history.back();">X</a>
				</div>
				<div class="item-info">
					<img src="${pageContext.request.contextPath}/upload/${item_photo1}" width="300" height="300">
				</div>
				<div class="item-name">
					<span>${item_name}</span>|후기 작성
				</div>
				<ul>
					<li>
						<label>별점을 매겨주세요.</label>
						<select name="item_score">
							<option value="5">⭐⭐⭐⭐⭐</option>
							<option value="4">⭐⭐⭐⭐</option>
							<option value="3">⭐⭐⭐</option>
							<option value="2">⭐⭐</option>
							<option value="1">⭐</option>
						</select>
					</li>
					<li>
						<label>후기 사진</label><br>
						<input type="file" name="review_photo" id="review_photo" accept="image/gif, image/png, image/jpeg">
					</li>
					<li>
						<label>상품에 대한 평가를 적어주세요.</label><br>
						<textarea rows="5" cols="60" name="review_content" id="review_content" placeholder="너무 좋아요!"></textarea>
					</li>
				</ul>
				<div class="align-center">
					<button class="rev-btn" type="submit">등록</button>
				</div>
			</form>
		</div>
		<%-- 내용 끝 --%>
	</div>
</body>
</html>