<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${item.item_name} | 후기 수정</title>
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
					<h2>${item.item_name} | 후기 수정</h2>
				</div>
				<div class="item-info">
					<c:if test="${!empty item.item_photo1}">
					<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" width="300" height="300">
					</c:if>
					<c:if test="${empty item.item_photo1}">
						<img src="${pageContext.request.contextPath}/upload/${item.item_photo1}" width="300" height="300">
					</c:if>
				</div>
			</div>
			<div class="review-right">
			<form action="updateReview.do" method="post" enctype="multipart/form-data" id="review_form" class="review-form">
				<input type="hidden" value="${item.item_num}" name="item_num">
				<input type="hidden" value="${db_review.review_num}" name="review_num">
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
						<input type="file" name="review_photo" id="review_photo" accept="image/gif, image/png, image/jpeg">
						<c:if test="${!empty db_review.review_photo}">
							<div id="file_detail">
								(${db_review.review_photo})파일이 등록되어 있습니다.
								<input type="button" value="파일 삭제" id="file_del">
							</div>
							<script type="text/javascript">
								$(function(){
									$('#file_del').click(function(){
										let choice = confirm('삭제하시겠습니까?');
										if(choice){
											$.ajax({
												url:'deleteReviewFile.do',
												type:'post',
												data:{review_num:${db_review.review_num}},
												dataType:'json',
												success:function(param){
													if(param.result == 'logout'){
														alert('로그인 후 사용 가능!');
														
													}else if(parma.result == 'success'){
														$('#file_detail').hide();
													}else if(param.result == 'wrongAccess'){
														alert('잘못된 접속!');
													}else{
														alert('파일 삭제 오류 발생!');
													}
												},
												error:function(){
													alert('네트워크 오류 발생!');
												}
											})
										}
									});
								});
							</script>
						</c:if>
					</li>
					<li>
						<label>상품에 대한 평가를 적어주세요.</label><br>
						<textarea rows="5" cols="60" name="review_content" id="review_content">${db_review.review_content}</textarea>
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