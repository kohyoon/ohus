<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<style>

li .dropbtn {
    display: inline-block;
    background-color : skyblue;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

li a:hover, .dropdown:hover .dropbtn {
    background-color: white;
    color : black;
}

li.dropdown {
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    text-align: left;
}

.dropdown-content a:hover {background-color: white}

.show {display:block;}
</style>
</head>

<body>

<!-- header 시작 -->
<!-- 내일의 집 로고 -->
<div id="main_logo">
	<h1 class="align-left"><a href="${pageContext.request.contextPath}/main/main.do">내일의집</a></h1>
</div>

<div id="main_nav">
	<ul>
		<!-- 로그인 여부 상관 없이 보이는 메인 헤더 -->
		<li>
			<a href="${pageContext.request.contextPath}/cboard/list.do">커뮤니티</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/itemr/list.do">쇼핑</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/market/list.do">상추마켓</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/event/list.do">오이벤트</a>
		</li>
	
		<span class="search"></span>
		<input type="text" placeholder="통합검색" autocomplete="off" aria-autocomplete="list" class="serach_bar" value="">
	
		<!-- 로그인 o, 사진 o -->	
		<c:if test="${!empty user_num && !empty user_photo}">
		<li class="menu-profile">
		<a href="${pageContext.request.contextPath}/member/myPage.do">
			<img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo"> <!-- 이미지 누르면 마이페이지로 이동하게 처리 -->
		</a>
		</li>
		</c:if>
		
		<!-- 로그인 o, 사진 x -->
		<c:if test="${!empty user_num && empty user_photo}">
		<li class="menu-profile">
			<a href="${pageContext.request.contextPath}/member/myPage.do">
				<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo"> 
			</a>	
		</li>
		</c:if>
		
		<!-- 관리자 헤더 -->
		<c:if test="${!empty user_num && user_auth == 9}">
			<li class="menu-logout">
				[<span>관리자모드</span>]
			</li>
		</c:if>
		
		<!-- 로그아웃 -->
		<c:if test="${!empty user_num}">
		<li class="menu-logout">
			<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</li>
		</c:if>
		
		<!-- 회원 - 장바구니 -->
		<c:if test="${!empty user_num && user_auth == 2}">
			<li>
				<a href="${pageContext.request.contextPath}/cart/list.do">
					<img src="${pageContext.request.contextPath}/images/basket.png"  width="25" height="25" class="basket">
				</a> 
			</li>

		</c:if>
		
		<!-- 회원 - 글쓰기 -->
		<c:if test="${!empty user_num && user_auth == 2}">
			<li>
				<!-- 글쓰기 드롭다운-->
				<div class="dropdown">
					  <button onclick="myFunction()" class="dropbtn">글쓰기</button>
					  <div id="myDropdown" class="dropdown-content">
						    <a href="#">커뮤니티글쓰기</a>
						    <a href="#">리뷰쓰기</a>
						    <a href="#">상추글쓰기</a>
						    <a href="#">문의하기</a>
					  </div>
				</div>
			</li>
		</c:if>
		
		<!-- 관리자 - 글쓰기 -->
		<c:if test="${!empty user_num && user_auth == 9}">
			<li>
				<div class="dropdown">
				  <button onclick="myFunction()" class="dropbtn">글쓰기</button>
				  <div id="myDropdown" class="dropdown-content"> 
				    <a href="#">공지등록</a>
				    <a href="#">이벤트등록</a>
				    <a href="#">상품등록</a>
				  </div>
				</div>
			</li>
		</c:if>
		
		<!-- 비회원 -->
		<c:if test="${empty user_num}">
			<li>
				<a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
			</li>
		</c:if>
	</ul>
</div>
<!-- header 끝 -->

<script>
//드롭다운처리
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(e) {
  if (!e.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    for (var d = 0; d < dropdowns.length; d++) {
      var openDropdown = dropdowns[d];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}
</script>

</body>
</html>
