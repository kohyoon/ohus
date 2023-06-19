<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래글 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
			if($('#photo1').val() == ''){
				alert('첫번째 사진을 첨부해주세요!');
				return false;
			}
			if($('#photo2').val() == ''){
				alert('두번째 사진을 첨부해주세요!');
				return false;
			}
		});
		
		// 이미지 프리뷰
		$('#photo1').change(function(){
			let file = this.files[0];
			let reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onload = function(e){
				$('#photo1_preview').attr('src',e.target.result).attr('width',500).attr('hieght',300);
			};
		});
		
		$('#photo2').change(function(){
			let file = this.files[0];
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
		<h2>거래글 글쓰기</h2>
		<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<label for="market_title">제목</label>
					<input type="text" name="market_title" id="title" maxlength="50">
				</li>
				<li>
					<label for="market_content">내용</label>
					<textarea rows="5" cols="30" name="market_content" id="content" maxlength="50"></textarea>
				</li>
				<li>
					<label for="market_status">거래상태</label>
					<select name="market_status" id="status">
						<option value="0" selected>판매중</option>
						<option value="1">거래완료</option>
					</select>
				</li>
				<li>
					<label for="market_photo1">첫번째 사진</label>
					<input type="file" name="market_photo1" id="photo1" accept="image/gif,image/png,image/jpeg">
					<img id="photo1_preview">
				</li>
				<li>
					<label for="market_photo2">두번째 사진</label>
					<input type="file" name="market_photo2" id="photo2" accept="image/gif,image/png,image/jpeg">
					<img id="photo2_preview">
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="등록하기">
				<input type="button" value="목록보기"
				   onclick="location.href='list.do'">
			</div>      
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>



