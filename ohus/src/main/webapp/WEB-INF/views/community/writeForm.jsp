<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//이벤트 연결
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
	});
</script>
<style>
	label {
		width: 100px; /* 레이블의 너비 조정 */
	}
	input[type="text"],
	textarea {
		width: 300px; /* 입력 칸의 너비 조정 */
	}
	.content-wrapper {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	}
	.horizontal-line {
	display: flex;
	align-items: center;
	}
	.horizontal-line hr {
	flex-grow: 1;
	border: none;
	border-top: 1px solid #000;
	margin: 0 10px;
	}
	.custom-hr {
  			height: 1px;
  			border: none;
  			background: #EAEDEF;
	}
	.button-container {
			text-align: center;
	}
	input[type=file]::file-selector-button {
  			width: 150px;
  			height: 30px;
  			background: #fff;
  			border: 1px solid rgb(77,77,77);
  			border-radius: 10px;
  			cursor: pointer;
  			&:hover {
    		background: rgb(77,77,77);
    		color: #fff;
  			}
	}		
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
<div class="content-wrapper">
	<div class="content-main">
		<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">		  
			<div class="horizontal-line">
			<hr>
			<h2 class="summary_title"  style="color: #828C94;"> 게시글 작성 </h2>
			<hr>
			</div>
			<br>
			<hr class="custom-hr" size="1" noshade="noshade" width="100%">
			<br>
			<div>
			<select name="cboard_category" id="category">
				<option value="0">일상</option>
				<option value="1">취미</option>
				<option value="2">자랑</option>
				<option value="3">추천</option>
			</select>
			</div><br>
			<div>
			
				<input type="file" name="cboard_photo1" id="filename" accept="image/gif,image/png,image/jpeg"><br><br>
				<input type="file" name="cboard_photo2" id="filename" accept="image/gif,image/png,image/jpeg">
			
			</div><br><br>	
			<hr class="custom-hr" size="1" noshade="noshade" width="100%">
			<br>
			<input type="text" name="cboard_title" id="title" maxlength="50" placeholder="제목을 입력해주세요." style="font-size: 24px; color: #C2C8CC; width: 600px; height: 25px; border: 1px solid transparent;"><br><br>
			<hr class="custom-hr" size="1" noshade="noshade" width="100%">
			<textarea rows="5" cols="30" name="cboard_content" id="content" maxlength="300" placeholder="내용을 입력해주세요." style="font-size: 16px; color: #C2C8CC; width: 600px; height: 200px; border: 1px solid transparent;"></textarea>

			<br><br>
			
			<hr class="custom-hr" size="1" noshade="noshade" width="100%">
			
			<br><br>
			<div class="button-container">
				<input type="submit" value="등록" style="font-size: 18px; background-color: #35C5F0; color: #FFFFFF; border-radius: 5px; width: 6em; height: 3em; border: none;">
				<input type="button" value="목록" style="font-size: 18px; background-color: #35C5F0; color: #FFFFFF; border-radius: 5px; width: 6em; height: 3em; border: none;">
			</div>
		</form>
	</div>
</div>
<!-- 내용 끝 -->

	</div>
</body>
</html>
