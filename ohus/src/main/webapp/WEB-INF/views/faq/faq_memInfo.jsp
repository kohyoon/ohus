<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<title>FAQ</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy.css">
<style type="text/css">
#faq_category{
	font-size:12px;
	text-align:center;
}
#faq_category button{
	border-radius: 20px;
	background-color:skyblue;
	border:none;
	color:white;
	padding:6px 20px;
}
#faq_category #this_page{
	border-radius:20px;
	background-color:#dae1ed;
	border:none;
	color:black;
	padding:6px 20px;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
//<![CDATA[
$(document).ready(function($){
	/**
	 * ===================================================================================
	 * = 컨텐츠 슬라이드
	 * ===================================================================================
	 */
	var $info = $("#info_wrap");
		$info.h4 = $info.find("> h4");
		$info.a = $info.h4.find("> a");
		$info.div = $info.find("> div");

	$info.a.bind("click",function(e){
		if($(this).parent("h4").next("div").is(":hidden")) {
			$(this).parent("h4").attr("class","on").siblings("h4").removeClass("on");
			$(this).parent("h4").next("div").slideDown(150,"swing").siblings("div").slideUp(150,"swing");
		} else {
			$info.h4.removeClass("on");
			$info.div.slideUp(150,"swing");
		}
		$(this).focus();
		return false;
	});

});
//]]>
</script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
		<!-- FAQ 카테고리 시작 -->
		<div id="faq_category">
			<button onclick="location.href='faqPay.do'">결제</button>
			<button onclick="location.href='faqDelivery.do'">배송관련</button>
			<button id="this_page" onclick="location.href='faqMemInfo.do'">회원정보</button>
			<button onclick="location.href='faqService.do'">서비스/기타</button>
		</div>
		<!-- FAQ 카테고리 끝 -->
		<!-- FAQ 내용 시작 -->
		<!-- <div class="info_box">
		<p>제목을 클릭하시면 세부내용을 확인하실 수 있습니다.</p>
		</div> -->
		<div id="info_wrap" class="toggle">
			<h4><a href="#">Q : 내일의집에 가입한 적 없는데 이미 가입된 이메일이라고 나와요. 어떻게 하나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 현재 오늘의집은 회원 가입 시 이메일 인증 절차를 거치므로 이러한 문제가 발생되지 않지만 인증 절차 도입 전 다른 고객님께서 이메일을 잘못 입력하신 경우 '이미 가입한 이메일'이란 메시지가 노출될 수 있습니다.
						<br>
						이는 고객님의 정보가 노출된 것이 아니니 걱정하지 마시고, 번거로우시겠지만 고객센터로 문의 부탁드립니다.
						<br>
						내일의집 고객센터 1111-1234 (운영 시간 : 평일 09:00~18:00)로 문의
					</li>
				</ul>
			</div>
			<h4 class="on"><a href="#">Q : 비밀번호 변경은 어떻게 하나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : [마이페이지 > 비밀번호 변경] 페이지에서 변경 가능합니다.</li>
				</ul>
			</div>
			<h4><a href="#">Q : 회원탈퇴 후 재가입이 가능한가요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : 내일의집 재가입은 가능하지만 같은 아이디로 재가입은 불가능합니다.</li>
				</ul>
			</div>
			<h4><a href="#">Q : 회원정보를 수정하고 싶은데 어떻게 해야하나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : [마이페이지 > 회원정보수정] 페이지에서 수정 가능합니다.
					</li>
				</ul>
			</div>
			<h4><a href="#">Q : 회원탈퇴는 어떻게 하나요>?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 내일의집 PC 웹사이트에서 가능합니다.
						<br>
						우측 상단 프로필 사진 클릭 후 [마이페이지 > 설정 > 회원정보수정] 상단의 탈퇴하기 버튼 클릭해주세요.
					</li>
				</ul>
			</div>
		</div>
		<!-- FAQ 내용 끝 -->
		
	
	
		<section>
			<h1><a href="faq.do">FAQ</a></h1>
			<nav>
				<label>결제</label>
				<label>회원정보</label>
				<label>서비스</label>
				<label>기타</label>
			</nav>
			
		</section>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>