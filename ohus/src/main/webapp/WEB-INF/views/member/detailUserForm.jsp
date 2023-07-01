<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/table.css">
</head>

<style>
.button{
text-align: center;
margin-top : 
}

</style>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>${member.id}의 회원정보(관리자 전용)</h2>
		<form action="detailUser.do" method="post" id="detail_form">
			<input type="hidden" name="mem_num" value="${member.mem_num}">
			<ul>
				<li>
					<label>등급</label>
					<!-- 회원 등급이 관리자가 아니라면 -->
					<c:if test="${member.auth !=9}"> <!-- input 안에 checked 넣어서 정지 일반 중 하나 선택할 수 있음 -->
						<input type="radio" name="auth" value="1" id="auth1"
							<c:if test="${member.auth==1}">checked</c:if>> 정지
						<input type="radio" name="auth" value="2" id="auth2"
							<c:if test="${member.auth==2}">checked</c:if>> 일반	
					</c:if>	
					
					<!-- 체크가 하나밖에 없음 -->
					<c:if test="${member.auth==9}">
						<input type="radio" name="auth" value="9" id="auth9" checked>관리
					</c:if>
							
					
				</li>
			
			</ul>
			
			
			<table>
				<!-- 항목 -->
				<tr>
					<th>이름</th>
					<th>전화번호</th>
					<th>이메일</th>
					<th>우편번호</th>
					<th>주소</th>
				</tr>	
				
				<tr>
					<td>${member.name}</td>
					<td>${member.phone} </td>
					<td>${member.email}</td>
					<td>${member.name}</td>
					<td>${member.address1} ${member.address2}</td>
				</tr>
			
		
		
		
	</div>
	<!-- 내용 끝 -->
</div>

<!-- 관리자인 경우 수정 버튼 안 보이게 하기 -->
			<div class="button">
			<c:if test="${member.auth != 9 }">
				<input type="submit" value="수정">
			</c:if>
				<input type="button" value="목록" onclick="location.href='adminPage.do'">
			</div>
		</form>

</body>
</html>