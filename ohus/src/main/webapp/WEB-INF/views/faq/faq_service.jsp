<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<title>FAQ</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/faq.css">
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
	<div id="cs_center">
		<button id="this_cs" onclick="location.href='${pageContext.request.contextPath}/faq/faqPay.do'">FAQ</button>
		<button onclick="location.href='${pageContext.request.contextPath}/notice/listNotice.do'">공지사항</button>
		<button onclick="location.href='${pageContext.request.contextPath}/inquiry/listInquiry.do'">문의게시판</button>
	</div>
	<!-- 내용 시작 -->
	<div class="container">
		<!-- FAQ 카테고리 시작 -->
		<div id="faq_category">
			<button onclick="location.href='faqPay.do'">결제</button>
			<button onclick="location.href='faqDelivery.do'">배송관련</button>
			<button onclick="location.href='faqMemInfo.do'">회원정보</button>
			<button id="this_faq" onclick="location.href='faqService.do'">서비스/기타</button>
		</div>
		<!-- FAQ 카테고리 끝 -->
		<!-- FAQ 내용 시작 -->
		<!-- <div class="info_box">
		<p>제목을 클릭하시면 세부내용을 확인하실 수 있습니다.</p>
		</div> -->
		<div id="info_wrap" class="toggle">
			<h4><a href="#">Q : "좋아요"를 누른 콘텐츠(사진/집들이/노하우/등)들은 어디서 볼 수 있나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 우측 상단 프로필 사진을 클릭 후 [마이페이지 > 좋아요] 페이지에서 확인 가능합니다.
					</li>
				</ul>
			</div>
			<h4 class="on"><a href="#">Q : 제품의 자세한 정보는 어떻게 알 수 있나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : 각 제품의 상세 페이지에서 확인 가능하며, 더욱 자세한 정보는 제품상세페이지 내 문의하기를 통해 판매 업체에 문의 가능합니다.</li>
				</ul>
			</div>
			<h4><a href="#">Q : 좋지않은 댓글을 받았습니다. 어떻게 해야하나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : 댓글 아래에 신고 버튼을 눌러 댓글 신고가 가능합니다.</li>
				</ul>
			</div>
			<h4><a href="#">Q : 내일의집에서 다루는 컨텐츠는 어떤것이 있나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 오늘의집에서 다루는 컨텐츠는 크게 사진, 온라인집들이, 전문가집들이, 노하우로 구분됩니다.
						<br>
						사진은 자신만의 인테리어 공간 및 소품등을 공유하는 소셜네트워크 공간입니다.여러분의 스타일링 사진을 자유롭게 올려주세요.
						<br>
						온라인집들이는 오늘의집 사용자들이 직접 집을 꾸민 후 인테리어에 후기와 구체적인 정보를 공유하는 컨텐츠 입니다. 여러분의 집을 소개하거나 다른 분의 집을 제보해주세요.
						<br>
						전문가집들이는 오늘의집의 인테리어 전문가들이 실제 시공 후 작성하는 포트폴리오 입니다.
						<br>
						노하우는 오늘의집유저, 파워블로거, 인테리어 전문가 분들이 만들어 가는 인테리어 가이드입니다. 여러분의 인테리어 관심을 언제나 열려있는 노하우 작가신청을 통해 공유해주세요.
					</li>
				</ul>
			</div>
			<h4><a href="#">Q : 상담방법과 상담가능시간, 유선번호는 어떻게 되나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 상담 시간은 평일 09:00 ~ 18:00 (주말 & 공휴일 제외)이며, 전화번호는 1111-1234 입니다.
						<br>
						하단의 고객센터 버튼 클릭 후, 문의게시판을 통해 문의 가능합니다.
					</li>
				</ul>
			</div>
		</div>
		<!-- FAQ 내용 끝 -->
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>