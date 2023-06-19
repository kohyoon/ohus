<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
 <%-- 아이디나 비밀번호가 불일치 할 때 - script 처리 --%>   
 <%-- 정상 삭제 처리는 <% %>로 처리하기 --%>
 
 <c:if test="${check}">   
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>탈퇴 처리</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	</head>
	<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<%-- 내용 시작 --%>
		<div class="content-main">
			<h2>회원 탈퇴 완료</h2>
			<div class="result-display">
				<div class="align-center">
				회원 탈퇴가 완료되었습니다
				<p>
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
				</div>
			</div>
		
		</div>
	</div>
	<%-- 내용 끝 --%>
	
	</body>
	</html>
</c:if>

<%-- 아이디와 비밀번호가 불일치 --%>
<c:if test="${!check}">
	<script>
		alert('입력한 정보가 정확하지 않습니다');
		history.go(-1);
	</script>

</c:if>