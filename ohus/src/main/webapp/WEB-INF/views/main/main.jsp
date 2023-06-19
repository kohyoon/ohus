<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body> 
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<!-- 내용 시작 -->
		<div class="content-main">  
			<!-- 홈 페이지 전체 영역-->
			<div class="home-page">
				<!-- 홈 페이지 상단 이미지 -->
				<div class="container home-header">
					<div class="home-header__upper">
						<div>
							<img
								src="https://image.ohou.se/i/bucketplace-v2-development/uploads/projects/cover_images/164309149487027066.jpg?gif=1&w=850&h=567&c=c"
								alt="홈페이지 메인 이미지" />

						</div>
					</div>

				</div>
			</div>
		</div>
		<!-- 하단 내용 시작 -->
		<footer class="footer">
			<div class="footer-upper">
				<div>
					<h4>
						<a href="">고객센터</a>
					</h4>
					<a class="footer-number" href="">1670-1234</a> <span>평일
						09:00 ~ 18:00 (주말 & 공휴일 제외)</span>
				</div>
				
			</div>
			<ul class="footer-lower">
				<li><a href="">브랜드 스토리</a></li>
				<li><a href="">회사소개</a></li>
				<li><a href="">채용정보</a></li>
				<li><a href="">이용약관</a></li>
				<li><a href="">개인정보처리방침</a></li>
				<li><a href="">공지사항</a></li>
				<li><a href="">고객센터</a></li>
				<li><a href="">고객의 소리</a></li>
				<li><a href="">전문가 등록</a></li>
			</ul>
		</footer>
		<!-- 하단 내용 끝 -->
	</div>
</body>
</html>




