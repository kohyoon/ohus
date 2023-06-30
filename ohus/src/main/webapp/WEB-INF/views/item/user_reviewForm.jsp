<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${item_name} | 후기 작성</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$('#review_form').submit(function(){
			if($('#review_content').val().trim() == ''){
				alert('상품에 대한 평가를 작성해주세요!');
				$('#name').val('').focus();
				return false;
			}
			var fileCheck = document.getElementById("review_photo").value;
			if(!fileCheck){
			    alert("후기 사진을 첨부해 주세요");
			    return false;
			}
		});
	});
	</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용시작 --%>
		<p>
		<div class="item-review">
			<div class="left">
				<div class="item-name">
					<h2>${item_name} | 후기 작성</h2> 
				</div>
				<div class="item-info">
					<img src="${pageContext.request.contextPath}/upload/${item_photo1}" width="300" height="300">
				</div>
			</div>
			<div class="review-right">
				<form action="userReview.do" method="post" enctype="multipart/form-data" id="review_form" class="review-form">
				<input type="hidden" value="${item_num}" name="item_num">
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
						<button class="ad-btn ad-regi" type="submit">등록</button>
						<button class="ad-btn ad-list" type="button"  onclick="location.href='javascript:window.history.back();'">취소</button>
					</div>
				</form>
			</div>
		</div>
		<%-- 내용 끝 --%>
	</div>
</body>
</html>