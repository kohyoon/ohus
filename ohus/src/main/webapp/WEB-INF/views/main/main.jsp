<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<style type="text/css">


/* 홈페이지 상단 이미지
------------------------------------------------------------------ */
.home-header__upper {
  position: relative;
  overflow: hidden;
  cursor: pointer;
}

.home-header__upper:hover img {
  transform: scale(110%);
}

.home-header__upper:hover .home-header-tit a {
  background: #35c5f0;
  border: none;
}

.home-header__upper img {
  width: 100%;
  filter: brightness(65%);
  transition: 0.3s;
  vertical-align: bottom;
}

.home-header-tit {
  position: absolute;
  top: 50%;
  left: 0;
  transform: translateY(-50%);
  padding: 2.5rem;
  color: #fff;
}

.home-header-tit h4 {
  font-size: 1.4rem;
  padding: 1rem 0;
}

.home-header-tit a {
  display: inline-block;
  width: 100px;
  height: 32px;
  text-align: center;
  line-height: 32px;
  border-radius: 5px;
  border: 1px solid #fff;
  font-size: 0.8rem;
  font-weight: 600;
}  

</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy.css">
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
	<%-- 당첨자 알림창 --%>
    <c:if test="${showWinnerAlert}">
        <script>
            alert('당첨을 축하합니다!');
        </script>
    </c:if>
</body>
</html>




