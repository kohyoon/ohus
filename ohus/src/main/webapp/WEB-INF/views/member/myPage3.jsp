<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>설정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/myPage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/member/myPageheader.jsp"/>
<!-- ajax통신으로..? 아니면 회원 정보를 보여주고 정보수정, 비밀번호 버튼을 따로 만들기 -->
<!-- 회원 정보를 보여주고 버튼 생성 -->
<input type="button" onclick="location.href='modifyUserForm.do'" value="정보수정">
<input type="button" onclick="location.href='modifyPasswordForm.do.do'" value="비밀번호변경">

</body>
</html>