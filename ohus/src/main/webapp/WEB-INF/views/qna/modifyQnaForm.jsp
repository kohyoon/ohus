<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품문의 게시판 글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/form.css">
<style type="text/css">

  #three_buttons {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }

  #three_buttons input[type="submit"],
  #three_buttons input[type="button"] {
    margin: 0 10px;
  }
</style>
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
		<h2 style="text-align:center;">상품문의 게시판 글 수정</h2>
		<form id="write_form" action="modifyQna.do" method="post">
			<input type="hidden" name="qna_num" value="${qna.qna_num}">
			<ul>
				<li>
					<label for="qna_title">제목</label>
					<input type="text" name="qna_title" id="qna_title" maxlength="50" value="${qna.qna_title}">
				</li>
				<li>
					<label>카테고리</label>
					<select name="qna_category">
						<option selected>==카테고리 선택==</option>
						<option value="1" <c:if test="${qna.qna_category == 1}">selected</c:if>>상품</option>
						<option value="2" <c:if test="${qna.qna_category == 2}">selected</c:if>>배송</option>
						<option value="3" <c:if test="${qna.qna_category == 3}">selected</c:if>>반품</option>
						<option value="4" <c:if test="${qna.qna_category == 4}">selected</c:if>>교환</option>
						<option value="5" <c:if test="${qna.qna_category == 5}">selected</c:if>>환불</option>
						<option value="6" <c:if test="${qna.qna_category == 6}">selected</c:if>>기타</option>
					</select>
				</li>
				<li>
					<label for="qna_content">내용</label>
					<textarea rows="5" cols="30" name="qna_content" id="qna_content">${qna.qna_content}</textarea>
				</li>
				<li>
					<label>상품명</label>
					<select name="item_num">
						<option selected>==상품 선택==</option>
						<c:forEach var="item" items="${list}">
						<option value="${item.item_num}" <c:if test="${item.item_num == qna.item_num}">selected</c:if>>${item.item_name}</option>
						</c:forEach>
					</select>
					
				</li>
			</ul>
			<div class="align-center" id="three_buttons">
				<input type="submit" value="수정">
				<input type="button" value="이전" onclick="location.href='detailQna.do?qna_num=${qna.qna_num}'">
				<input type="button" value="목록" onclick="location.href='qnaList.do'">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>