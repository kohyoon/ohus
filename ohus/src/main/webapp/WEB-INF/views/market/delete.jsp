<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래글 삭제</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>거래글 삭제 성공</h2>
		<div class="result-display">
			<div class="align-center">
				글이 삭제 되었습니다.
				<p>
				<input type="button" value="목록보기" onclick="location.href='list.do'">
			</div>
		</div>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>








