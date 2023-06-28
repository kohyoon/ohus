<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/faq.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/notice.css">
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
		<button onclick="location.href='${pageContext.request.contextPath}/notice/listNotice.do'">공지사항</button>
		<button id="this_cs" onclick="location.href='${pageContext.request.contextPath}/inquiry/listInquiry.do'">문의게시판</button>
	</div>
	<!-- 내용 시작 -->
	<div class="container">
		<h2 style="text-align:center;">문의게시판</h2>
				
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th style="width:5%;">글번호</th>
				<th style="width:60%;">제목</th>
				<th>작성자ID</th>
				<th>작성일</th>
				<th>처리여부</th>
			</tr>
			<c:forEach var="inquiry" items="${list}">
			<tr>
				<td>${inquiry.inq_num}</td>
				<td style="text-align:left;"><a href="detailInquiry.do?inq_num=${inquiry.inq_num}">${inquiry.inq_title}</a></td>
				<td>${inquiry.id}</td>
				<td>${inquiry.inq_regdate}</td>
				<td class="status">
					<c:if test="${inquiry.inq_status == 1}">
					<span style="color:blue;">처리전</span>
					</c:if>
					<c:if test="${inquiry.inq_status == 2}">
					<span style="color:red;"><b>처리완료</b></span>
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		<div class="bottoms">
			<!-- 검색창 시작 -->
			<form id="search_form" action="list.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자ID</option>
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
			
			<div class="list-space align-right" style="margin:0;">
				<input type="button" value="글쓰기" onclick="location.href='writeInquiryForm.do'" <c:if test="${empty user_num}">disable="disabled"</c:if>>
				<input type="button" value="전체목록" onclick="location.href='listInquiry.do'">
			</div>
		</div>
		</c:if>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>