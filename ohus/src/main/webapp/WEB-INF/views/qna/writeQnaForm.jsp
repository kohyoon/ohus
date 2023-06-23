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
			if(isNaN($('#order_num').val())){
				alert('구매내역을 선택해야 합니다. 구매내역이 없을 상품문의글을 남길 수 없습니다.');
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
					<label for="qna_title">제목</label>
					<input type="text" name="qna_title" id="qna_title" maxlength="50">
				</li>
				<li>
					<label for="qna_content">내용</label>
					<textarea rows="5" cols="30" name="qna_content" id="qna_content"></textarea>
				</li>
				<li>
					<label for="qna_filename">파일</label>
					<input type="file" name="qna_filename" id="qna_filename" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<label for="order_num">구매내역${count}</label>
					<select name="order_num" id="order_num">
					<c:if test="${count == 0}">
						<option disabled>===선택===</option>
					</c:if>
					<c:if test="${count > 0}">
						<option>===선택===</option>
						<c:forEach var="order" items="${list}">
						<option value="${order.order_num}">[${order.order_num}] ${order.item_name}</option>
						</c:forEach>
					</c:if>
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