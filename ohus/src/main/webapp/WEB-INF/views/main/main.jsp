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
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/koy.css">
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
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>   
		<!-- 하단 내용 끝 -->
	</div>
</body>
</html>




