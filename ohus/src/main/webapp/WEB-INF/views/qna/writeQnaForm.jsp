<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품문의 게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/form.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('#qna_title').val().trim() == ''){
				alert('제목을 입력하세요.');
				$('#qna_title').val('').focus();
				return false;
			}
			if($('#qna_content').val().trim() == ''){
				alert('내용을 입력하세요.');
				$('#qna_content').val('').focus();
				return false;
			}
			if(isNaN($('select[name="qna_category"]').val())){
				alert('카테고리를 선택해야 합니다.');
				return false;
			}
			if(isNaN($('select[name="item_num"]').val())){
				alert('상품을 선택해야 합니다.');
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
		<form id="write_form" action="writeQna.do" method="get">
			<ul>
				<li>
					<label for="qna_title">제목</label>
					<input type="text" name="qna_title" id="qna_title" maxlength="50">
				</li>
				<li>
					<label>카테고리</label>
					<select name="qna_category">
						<option selected>==카테고리 선택==</option>
						<option value="1">상품</option>
						<option value="2">배송</option>
						<option value="3">반품</option>
						<option value="4">교환</option>
						<option value="5">환불</option>
						<option value="6">기타</option>
					</select>
				</li>
				<li>
					<label for="qna_content">내용</label>
					<textarea rows="5" cols="30" name="qna_content" id="qna_content"></textarea>
				</li>
				<li>
					<label>상품명</label>
					<select name="item_num">
						<option <c:if test="${item_num == 0}">selected</c:if>>==상품 선택==</option>
						<c:forEach var="item" items="${list}">
						<option value="${item.item_num}" <c:if test="${item_num == item.item_num}">selected</c:if>>${item.item_name}</option>
						</c:forEach>
					</select>
					
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="등록">
				<input type="button" value="목록" onclick="location.href='qnaList.do'">
				<input type="button" value="이전" onclick="history.go(-1)">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>