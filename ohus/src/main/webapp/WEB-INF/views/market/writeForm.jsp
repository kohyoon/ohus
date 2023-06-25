<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래글 글쓰기</title>
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
			if($('#market_photo1').val() == ''){
				alert('첫번째 사진을 첨부해주세요!');
				return false;
			}
			if($('#market_photo2').val() == ''){
				alert('두번째 사진을 첨부해주세요!');
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
				$('#photo_preview').attr('src',e.target.result).attr('width',400).attr('height',400);
			};
		});
		
		$('#market_photo2').change(function(){
			let file = this.files[0];
			$('.photo2-name').html($(this).val());
			let reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onload = function(e){
				$('#photo_preview').attr('src',e.target.result).attr('width',400).attr('height',400);
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
		<h2>글쓰기</h2>
		<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
			<div class="half left">
				<input type="text" name="market_title" id="title" maxlength="50" placeholder="제목">
				<br>
				<br>
				<textarea rows="5" cols="30" name="market_content" id="content" maxlength="300" placeholder="내용"></textarea>
				<div class="buttons">
					<input type="submit" value="등록하기">
					<input type="button" value="목록보기" onclick="location.href='list.do'">
				</div>  
			</div>
			<div class="half right">
				<div class="status">
					<label for="market_status">거래상태</label>
					<select name="market_status" id="status">
						<option value="0" selected>판매중</option>
					</select>
				</div>
				<div class="photos">
					<label class="photos-main-title">첨부파일</label>
					<label class="photos-select" for="market_photo1">첫번째 사진</label>
					<input type="file" name="market_photo1" id="market_photo1" accept="image/gif,image/png,image/jpeg">
					<span class="photo1-name"></span>
					
					<br><br>
					
					<label class="photos-select" for="market_photo2">두번째 사진</label>
					<input type="file" name="market_photo2" id="market_photo2" accept="image/gif,image/png,image/jpeg">
					<span class="photo2-name"></span>
				</div>
				<div class="preview">
					<img id="photo_preview">
				</div>
			</div>    
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>



