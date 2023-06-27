<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/form.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/notice.css">
<style type="text/css">
#list{
	width: 30%;
	padding: 13px;
	background-color: #35c5f0 ;
	border: none;
	color: white;
	font-weight: bold;
	font-size : 15px;
	cursor: pointer;
	margin-right: 200px;
	margin-top: 10px;
}
  #two_buttons {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }

  #two_buttons input[type="submit"],
  #two_buttons input[type="button"] {
    margin: 0 10px;
  }
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#write_form").submit(function(){
			if($('#title').val().trim() == ''){
				alert('제목을 입력하세요.');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim() == ''){
				alert('내용을 입력하세요.');
				$('#content').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
		<h2 style="text-align:center;">공지사항 글쓰기</h2>
		<form id="write_form" action="writeNotice.do" method="post" enctype="multipart/form-data" style="border:none;">
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" id="title" maxlength="10">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content" id="content"></textarea>
				</li>
				<li>
					<label for="filename">파일</label>
					<input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
				</li>
			</ul>
			<div class="align-center" id="two_buttons">
				<input type="submit" value="등록">
				<input type="button" value="목록" id="list" onclick="location.href='listNotice.do'">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>