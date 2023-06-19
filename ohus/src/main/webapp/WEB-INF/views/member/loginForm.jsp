<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#login_form').submit(function(){
			if($('#id').val().trim()==''){
				alert('아이디를 입력하세요');
				$('#id').val('').focus();
				return false;
			}
			if($('#password').val().trim()==''){
				alert('비밀번호를 입력하세요');
				$('#password').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>내일의집</h2>
		<form id="login_form" action="login.do" method="post">
			<ul>
				<li>
					<label for="id">아이디</label>
					<input type="text" name="id" id="id" maxlength="12" autocomplete="off">
				</li>
				<li>
					<label for="password">비밀번호</label>
					<input type="password" name="password" id="password">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="로그인">
			</div> 
			<!-- 로그인 하단 -->
			 <div>
			 	<input type="button" value="회원가입" onclick="location.href='registerUserForm.jsp'">
			 	<input type="submit" value="문의사항" onclick="location.href='${pageContext.request.contextPath}/inquiry/inquiry.do'">
			 </div>                     
		</form>
	</div>
	<!-- 내용 끝 -->
</div>





</body>
</html>



