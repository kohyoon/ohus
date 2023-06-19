<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
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
			
			<div class="align-center">
			<!-- 관리자인 경우 수정 버튼 안 보이게 하기 -->
			<c:if test="${member.auth != 9 }">
				<input type="submit" value="수정">
			</c:if>
				<input type="button" value="목록" onclick="location.href='memberList.do'">
			</div>
			
			<ul>
				<li>
					<label>이름</label>${member.name} <!-- 일정 간격 띄우는 css 넣어놔서 label 쓰는 것 -->
				</li>
				
				<li>
					<label>전화번호</label>${member.phone} 
				</li>
				
				<li>
					<label>이메일</label>${member.email} 
				</li>
				
				<li>
					<label>우편번호</label>${member.zipcode} 
				</li>
				
				<li>
					<label>주소</label>${member.address1} ${member.address2} 
				</li>
				
			</ul>
		
		</form>
		
		
		
	</div>
	<!-- 내용 끝 -->

</div>

</body>
</html>