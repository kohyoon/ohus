<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/inquirt.answer.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>${inquiry.inq_title}</h2>
		<ul class="detail-info">
			<li>
				<label>카테고리</label>
				<c:if test="${inquiry.inq_category == 1}">사이트 문의</c:if>
				<c:if test="${inquiry.inq_category == 2}">카테고리 문의</c:if>
			</li>
			<li>
				<label>작성자</label>
				${inquiry.id}
			</li>
			<li>
				<label>작성일</label>
				${inquiry.inq_regdate}
			</li>
			<li>
				<label>처리상태</label>
				: *체크박스추가해야됨
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<p>${inquiry.inq_content}</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			<li>
				작성일 : ${inquiry.inq_regdate}
				<c:if test="${user_num == inquiry.mem_num}">
				<input type="button" value="수정" onclick="location.href='modifyInquiryForm.do?inq_num=${inquiry.inq_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick = function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('deleteInquiry.do?inq_num=${inquiry.inq_num}');
						}
					};
				</script>
				<input type="button" value="수정">
				</c:if>
				<input type="button" value="목록" onclick="location.href='listInquiry.do'">
			</li>
		</ul>
		<!-- 답변 시작 -->
		<%-- <c:if test="${user_auth == 9}"> --%>
		<div id="reply_div">
			<span class="re-title">댓글 달기</span>
			<form id="inq_form">
				<input type="hidden" name="inq_num" value="${inquiry.inq_num}" id="inq_num">
				<textarea rows="3" cols="50" name="inq_content" id="inq_content" class="rep-content"
						<c:if test="${empty user_num}">disabled="disabled"</c:if>><c:if test="${empty user_num}">로그인 후 작성할 수 있습니다.</c:if></textarea>
				<c:if test="${!empty user_num}">
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="전송">
				</div>
				</c:if>
			</form>
		</div>
		<%-- </c:if> --%>
		<!-- 답변 목록 출력 시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="목록">
		</div>
		<div class="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
		<!-- 답변 목록 출력 끝 -->
		<!-- 답변 끝 -->
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>