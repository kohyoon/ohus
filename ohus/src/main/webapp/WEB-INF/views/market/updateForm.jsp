<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ssk/market_writeForm.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		// 이벤트 연결
		$('#write_form').submit(function(){
			if($('#title').val().trim()==''){
				alert('제목을 입력하세요!');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}
		});
		
		// 이미지 프리뷰
		$('#market_photo1').change(function(){
			let file = this.files[0];
			$('.photo1-name').html($(this).val());
			let reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onload = function(e){
				$('#photo1_preview').attr('src',e.target.result).attr('width',500).attr('hieght',300);
			};
		});
		
		$('#market_photo2').change(function(){
			let file = this.files[0];
			$('.photo2-name').html($(this).val());
			let reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onload = function(e){
				$('#photo2_preview').attr('src',e.target.result).attr('width',500).attr('hieght',300);
			};
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>글 수정하기</h2>
		<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="market_num" value="${market.market_num}">
			<div class="half left">
				<input type="text" name="market_title" id="title" maxlength="50" value="${market.market_title}">
				<br>
				<br>
				<textarea rows="5" cols="30" name="market_content" id="content" maxlength="300">${market.market_content}</textarea>
				<div class="buttons">
					<input type="submit" value="수정하기">
					<input type="button" value="목록보기" onclick="location.href='list.do'">
				</div>  
			</div>
			<div class="half right">
				<div class="status">
					<label for="market_status">거래상태</label>
					<select name="market_status" id="status">
						<option value="0" selected>판매중</option>
						<option value="1">거래완료</option>
					</select>
				</div>
				<div class="photos">
					<label class="photos-main-title">첨부파일</label>
					<label class="photos-select" for="market_photo1">첫번째 사진</label>
					<input type="file" name="market_photo1" id="market_photo1" accept="image/gif,image/png,image/jpeg">
					<span class="photo1-name">${market.market_photo1}</span>
					<img id="photo1_preview" src="${pageContext.request.contextPath}/upload/${market.market_photo1}" width="500px" height="300px">
					<br><br>
					
					<label class="photos-select" for="market_photo2">두번째 사진</label>
					<input type="file" name="market_photo2" id="market_photo2" accept="image/gif,image/png,image/jpeg">
					<span class="photo2-name">${market.market_photo2}</span>
					<img id="photo2_preview" src="${pageContext.request.contextPath}/upload/${market.market_photo2}" width="500px" height="300px">
				</div>
			</div>        
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>



