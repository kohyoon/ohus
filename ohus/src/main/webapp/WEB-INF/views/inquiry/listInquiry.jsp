<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('keyword').val().trim() == ''){
				alert('검색어를 입력하세요.');
				$('#keyword').val('').focus();
				return false;
			}
		}); //end of submit
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>문의게시판</h2>
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
		<div class="list-space align-right">
			<input type="button" value="글쓰기" onclick="location.href='writeInquiryForm.do'" <c:if test="${empty user_num}">disable="disabled"</c:if>>
			<input type="button" value="전체목록" onclick="location.href='listInquiry.do'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>처리여부</th>
			</tr>
			<c:forEach var="inquiry" items="${list}">
			<tr>
				<td>${inquiry.inq_num}</td>
				<td><a href="detailInquiry.do?inq_num=${inquiry.inq_num}">${inquiry.inq_title}</a></td>
				<td>${inquiry.id }</td>
				<td>${inquiry.inq_regdate}</td>
				<td>
					<c:if test="${inquiry.inq_status == 1}">
					처리전
					</c:if>
					<c:if test="${inquiry.inq_status == 2 }">
					<b>처리완료</b>
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>