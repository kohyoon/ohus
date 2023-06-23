<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>커뮤니티 글쓰기</h2>
		<form id="write_form" action="write.do"
		        method="post" 
		       enctype="multipart/form-data">
			<ul>
				<li>
					<label for="cboard_category">카테고리</label>
					<select name="cboard_category" id="category">
						<option value="0" selected>일상</option>
						<option value="1">취미</option>
						<option value="2">자랑</option>
						<option value="3">추천</option>
					</select>
				</li>
				<li>
					<label for="cborad_title">제목</label>
					<input type="text" name="cboard_title"
					      id="title" maxlength="50">
				</li>
				<li>
					<label for="cboard_content">내용</label>
					<textarea rows="5" cols="30" name="cboard_content"
					      id="content" maxlength="50"></textarea>
				</li>
				<li>
					<label for="cboard_photo1">사진1</label>
					<input type="file" name="cboard_photo1"
					      id="filename" 
					      accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<label for="cboard_photo2">사진2</label>
					<input type="file" name="cboard_photo2"
					      id="filename" 
					      accept="image/gif,image/png,image/jpeg">
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="등록">
				<input type="button" value="목록"
				   onclick="location.href='list.do'">
			</div>      
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>



