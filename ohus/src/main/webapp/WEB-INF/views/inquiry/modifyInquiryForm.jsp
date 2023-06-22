<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#modify_form').submit(function(){
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
		});//end of submit
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>문의글 수정</h2>
		<form id="modify_form" action="modifyInquiry.do" method="post">
			<input type="hidden" name="inq_num" value="${inquiry.inq_num}">
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" value="${inquiry.inq_title}">
				</li>
				<li>
					<label for="category">카테고리</label>
					<select name="category">
						<option value="1" <c:if test="${inquiry.inq_category == 1}">selected</c:if>>사이트 문의</option>
						<option value="2" <c:if test="${inquiry.inq_category == 2}">selected</c:if>>기타 문의</option>
					</select>
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content">${inquiry.inq_content}</textarea>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="이전" onclick="location.href='detailInquiry.do?inq_num=${inquiry.inq_num}'">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>