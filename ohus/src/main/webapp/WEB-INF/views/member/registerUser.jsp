<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<div class="result-display">
			<div class="align-center">
				회원가입 완료!
				<p>
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
				<input type="button" value="로그인" onclick="location.href='${pageContext.request.contextPath}/member/loginForm.do'">
			</div>
		</div>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>



