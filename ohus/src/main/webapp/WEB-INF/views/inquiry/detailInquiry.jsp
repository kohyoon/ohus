<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/faq.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/detail.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/notice.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/inquiry.answer.js"></script>
</head>  
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div id="cs_center">
		<button onclick="location.href='${pageContext.request.contextPath}/faq/faqPay.do'">FAQ</button>
		<button onclick="location.href='${pageContext.request.contextPath}/notice/listNotice.do'">공지사항</button>
		<button id="this_cs" onclick="location.href='${pageContext.request.contextPath}/inquiry/listInquiry.do'">문의게시판</button>
	</div>
	<!-- 내용 시작 -->
	<div class="container">
		<ul class="detail-info">
			<li>
				<h2><span>TITLE</span> ${inquiry.inq_title}</h2>
			</li>
			<li>
				<span>WRITER : </span>${inquiry.id}
				<span>| CATEGORY : </span>
				<c:if test="${inquiry.inq_category == 1}">사이트 문의</c:if>
				<c:if test="${inquiry.inq_category == 2}">기타 문의</c:if>
				<span> | 작성일 : </span>${inquiry.inq_regdate}
				<c:if test="${!empty notice.notice_mdate}">
				<span> | 최근 수정일 : </span>${inquiry.inq_modifydate}
				</c:if>
				<span> | </span>
				<c:if test="${inquiry.inq_status == 2}">
				<input type="checkbox" checked disabled>처리완료
				</c:if>
				<c:if test="${inquiry.inq_status == 1}">
				<input type="checkbox" disabled>처리전
				</c:if>
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<p class="content">
			${inquiry.inq_content}
		</p>

		<div class="detail-sub">
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
			</c:if>
			<input type="button" value="목록" onclick="location.href='listInquiry.do'">
		</div>
		<hr size="1" noshade="noshade" width="100%">
		<!-- 답변 시작 -->
		<div id="reply_div">
		<!-- 답변 목록 출력 시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div class="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
		<!-- 답변 목록 출력 끝 -->
		<%-- <c:if test="${user_auth == 9}"> --%>
		<div id="reply_div">
			<form id="ans_form">
				<input type="hidden" name="inq_num" value="${inquiry.inq_num}" id="inq_num">
				<textarea rows="3" cols="50" name="ans_content" id="ans_content" class="rep-content" placeholder="답변 입력"
						<c:if test="${user_auth < 9}">disabled="disabled"</c:if>><c:if test="${user_auth < 9}">관리자만 작성할 수 있습니다.</c:if></textarea>
				<c:if test="${!empty user_num}">
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right" style="margin:0;">
					<input type="submit" value="전송">
				</div>
				</c:if>
			</form>
		</div>
		<%-- </c:if> --%>
		</div>
		<!-- 답변 끝 -->
		
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>   
</div>
</body>
</html>