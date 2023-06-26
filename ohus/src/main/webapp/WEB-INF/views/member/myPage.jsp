<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/myPage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

</head>
<body>
<!-- ---============ -->
<!-- 상품 네비게이션 처리 시작 -->		
<jsp:include page="/WEB-INF/views/common/header.jsp"/>	
<header>
	<div class="header-lowwer__nav">
		<div class="header-lower">
			<div class="inner">
				<nav>
					<a href="myPage1.do" class="toggle">프로필</a>
						<div>
				        <ul class="submenu">
				          <%-- <%@ include file="myPage1.jsp" %> --%>
				        </ul>
				        </div>
					<a class="header-lower__item" href="myPage2.do">나의쇼핑</a> 
						<ul class="submenu">
				          <%@ include file="myPage2.jsp" %>
				        </ul>
					<a class="header-lower__item" href="myPage3.do">나의참여</a> 
					<ul class="submenu">
		          <%@ include file="myPage3.jsp" %>
		        </ul>
					
					<a class="header-lower__item" href="myPage4.do">설정</a>
					<ul class="submenu">
		          <%@ include file="myPage4.jsp" %>
		        </ul>
				</nav>
			</div>
		</div>
	</div>
	<p>
</header>	
<!-- 상품 네비게이션 처리 끝 -->


<%-- <!-- 전체 div 시작-->
<div class="wrap">
		
	<div class="page-main">

		<div class="content-main">
			<ul class="">
		      <li>
		        <a href="#" class="toggle">프로필</a>
		        <ul class="submenu">
		          <%@ include file="myPage1.jsp" %>
		        </ul>
		      </li>
		      <li>
		        <a href="#" class="toggle">나의쇼핑</a>
		        <ul class="submenu">
		          <%@ include file="myPage2.jsp" %>
		        </ul>
		      </li>
		      <li>
		        <a href="#" class="toggle">나의참여</a>
		        <ul class="submenu">
		          <%@ include file="myPage3.jsp" %>
		        </ul>
		      </li>
		      <li>
		        <a href="#" class="toggle">설정</a>
		        <ul class="submenu">
		          <%@ include file="myPage4.jsp" %>
		        </ul>
		</div>
	</div>		
</div>
<!-- 전체 div 끝--> --%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
function toggleSubMenu(element) {
  var submenu = element.nextElementSibling;
  submenu.classList.toggle("show");
}

$(document).ready(function() {
  // 하위 메뉴 초기 상태는 숨김으로 설정
  $('.submenu').hide();

  // 상단 메뉴 클릭 이벤트 처리
  $('.toggle').click(function() {
    // 다른 하위 메뉴 숨김
    $('.submenu').hide();
    
    // 클릭한 상단 메뉴의 하위 메뉴만 보이게 설정
    $(this).siblings('.submenu').show();
  });
});
</script>



</body>
</html>



