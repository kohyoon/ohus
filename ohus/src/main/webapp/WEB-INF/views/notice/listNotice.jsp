<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/faq.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/notice.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/table.css">
<style type="text/css">
table{
	width:1200px;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim() == ''){
				alert('검색어를 입력하세요.');
				$('#keyword').val('').focus();
				return false;
			}
		}); //end of submit
	});
</script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div id="cs_center">
		<button onclick="location.href='${pageContext.request.contextPath}/faq/faqPay.do'">FAQ</button>
		<button id="this_cs" onclick="location.href='${pageContext.request.contextPath}/notice/listNotice.do'">공지사항</button>
		<button onclick="location.href='${pageContext.request.contextPath}/inquiry/listInquiry.do'">문의게시판</button>
		<button onclick="location.href='${pageContext.request.contextPath}/qna/qnaList.do'">상품문의게시판</button>
	</div>
	<!-- 내용 시작 -->
	<div class="container">
		<h2 style="text-align:center;">공지사항 목록</h2>	
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 공지사항이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr style="pointer-events: none;">
				<th>번호</th>
				<th style="width:75%;">제목</th>
				<th>작성일</th>
			</tr>
			<c:forEach var="notice" items="${list}">
			<tr>
				<td>${notice.notice_num}</td>
				<td style="text-align:left;"><a href="detailNotice.do?notice_num=${notice.notice_num}"><b>[공지]</b> ${notice.notice_title}</a></td>
				<td>${notice.notice_regdate}</td>
			</tr>
			</c:forEach>
		</table> 
		<div class="bottoms">
			<!-- 검색창 시작 -->
			<form id="search_form" action="listNotice.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
							<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>내용</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
				</ul>
			</form>
			<!-- 검색창 끝 -->	
			<div class="list-space">
				<input type="button" value="글쓰기" onclick="location.href='writeNoticeForm.do'" <c:if test="${empty user_num}">disable="disabled"</c:if>>
				<input type="button" value="전체목록" onclick="location.href='listNotice.do'" style="text-align:center;">
			</div>		
		</div>
		</c:if>
		<br>
		<br>
		<div class="align-center" style="width:100%;">${page}</div>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>