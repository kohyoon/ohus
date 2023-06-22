<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품문의 게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
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
		<h2>상품문의 게시판 글쓰기</h2>
		<form id="write_form" action="writeQna.do" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" id="title" maxlength="50">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content" id="content"></textarea>
				</li>
				<li>
					<label for="filename">파일</label>
					<input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<label for="detail_num">구매내역</label>
					<select>
						<%-- <c:forEach var="">
						
						</c:forEach> --%>
					</select>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="등록">
				<input type="button" value="목록" onclick="location.href='qnaList.do'">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>