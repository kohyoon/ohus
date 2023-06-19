
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!-- header 시작 -->

	<!-- 전체 레이아웃-->
	<div class="wrap">
		<!-- 상단 네비게이션 영역-->
		<header class="header">
			<!-- 네비게이션 상단 -->
			<div class="header-upper">
				<div class="inner">
					<i class="fas fa-bars searchMenu"></i>
					<div class="header-upper__inner">
						<!-- 로고 -->
						<div class="header-upper__logo">
							<a href="${pageContext.request.contextPath}/main/main.do">내일의집</a>
						</div>
						<!-- 상단 메뉴 시작-->
						<div class="header-upper__nav">
							<ul>
								
									<li class="header-upper__item upper__active">
										<h3><a href="${pageContext.request.contextPath}/cboard/list.do">
											커뮤니티
										</a></h3>
									</li>	 
										<li class="header-upper__item"><h3><a
											href="${pageContext.request.contextPath}/item/list.do">쇼핑</a></h3></li>
										<li class="header-upper__item"><h3><a
											href="${pageContext.request.contextPath}/market/list.do">상추마켓</a></h3>
										</li>
										<li class="header-upper__item"><h3><a
											href="${pageContext.request.contextPath}/event/list.do">오이벤트</a></h3></li>
							</ul>
						</div>
						
					<!-- 서비스헤더 (오른쪽헤더) 시작-->
					<div class="header-upper__service">
							<i class="mobile-searchBar fas fa-search"></i>
							<!-- 검색창 -->
							<div class="header-upper__searchBar">
								<i class="fas fa-search"></i> 
								<input type="search" placeholder="오늘의집 통합검색" />
							</div>
	
							<!-- ul태그 시작 -->
							<ul class="header-upper_test">
								<!-- 비회원 -->
								<c:if test="${empty user_num}">
									<li class="register_bar"><a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a>
									</li>
									<li class="login_bar"><a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
									</li>
								</c:if>
	
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
									<li class="menu-logout">[<span>관리자모드</span>]</li>
								</c:if>
	
								<!-- 로그아웃 -->
								<c:if test="${!empty user_num}">
									<li class="menu-logout"><a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
									</li>
								</c:if>
	
								<!-- 회원 - 장바구니 -->
								<c:if test="${!empty user_num && user_auth == 2}">
									<li>
										<a href="${pageContext.request.contextPath}/cart/list.do"> 
											<img src="${pageContext.request.contextPath}/images/basket.png" width="25" height="25" class="basket">
										</a>
									</li>
								</c:if>
	
								<!-- 회원 - 글쓰기 -->
								<c:if test="${!empty user_num && user_auth == 2}">
									<li>
										<div class="dropdown">
											<button onclick="myFunction()" class="dropbtn">글쓰기</button>
											<div id="myDropdown" class="dropdown-content">
												<a href="#">커뮤니티글쓰기</a> 
												<a href="#">리뷰등록</a> 
												<a href="#">상추글쓰기</a>
												<a href="#">문의등록</a>
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
							</ul>
						</div>
						<!-- 서비스헤더 (오른쪽헤더) 시작-->
					</div>	
				</div>	
			</div>	
		</header>		
		<!-- 상단 메뉴 끝 -->
		
		<!-- 하단 네비게이션 처리 시작 -->			
		<header>
			<div class="header-lowwer__nav">
				<div class="header-lower">
					<div class="inner">
					<!-- 커뮤니티를 눌렀을 때, 쇼핑을 눌렀을 때, 상추마켓, 오이벤트 -->
						<nav>
							<a class="header-lower__item active" href="">일상</a> <a
								class="header-lower__item" href="">취미</a> <a
								class="header-lower__item" href="">자랑</a> <a
								class="header-lower__item" href="">추천</a>
						</nav>
					</div>
				</div>
			</div>
		</header>	
		<!-- 하단 네비게이션 처리 끝 -->

	</div>		

<script type="text/javascript">
/* 
 글쓰기 버튼 클릭했을 때 드롭다운 처리 
 */
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

//드롭다운이 보이는 상태에서 다른 화면을 클릭했을 때 사라지도록 처리
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}
</script>	

