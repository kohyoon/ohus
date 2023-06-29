<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${item.item_name} | 후기 수정</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용시작 --%>
		<p>
		<div class="item-review">
			<form action="updateReview.do" method="post" enctype="multipart/form-data" id="review_form" class="review-form">
				<input type="hidden" value="${item.item_num}" name="item_num">
				<div>
					<a href="javascript:window.history.back();">X</a>
				</div>
				<div class="item-info">
					<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" width="300" height="300">
				</div>
				<div class="item-name">
					<span>${item.item_name}</span> | 후기 작성
				</div>
				<ul>
					<li>
						<label>별점을 매겨주세요.</label>
						<select name="item_score">
							<option value="5" <c:if test="${db_review.item_score == 5}">selected</c:if>>⭐⭐⭐⭐⭐</option>
							<option value="4" <c:if test="${db_review.item_score == 4}">selected</c:if>>⭐⭐⭐⭐</option>
							<option value="3" <c:if test="${db_review.item_score == 3}">selected</c:if>>⭐⭐⭐</option>
							<option value="2" <c:if test="${db_review.item_score == 2}">selected</c:if>>⭐⭐</option>
							<option value="1" <c:if test="${db_review.item_score == 1}">selected</c:if>>⭐</option>
						</select>
					</li>
					<li>
						<label>후기 사진</label><br>
						<input type="file" name="review_photo" id="review_photo" accept="image/gif, image/png, image/jpeg" value="${db_review.review_photo}">
					</li>
					<li>
						<label>상품에 대한 평가를 적어주세요.</label><br>
						<textarea rows="5" cols="60" name="review_content" id="review_content">${db_review.review_content}</textarea>
					</li>
				</ul>
				<div class="align-center">
					<button class="rev-btn" type="submit">수정</button>
				</div>
			</form>
		</div>
		<%-- 내용 끝 --%>
	</div>
</body>
</html>