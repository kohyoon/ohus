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
			<button id="this_faq" onclick="location.href='faqPay.do'">결제</button>
			<button onclick="location.href='faqDelivery.do'">배송관련</button>
			<button onclick="location.href='faqMemInfo.do'">회원정보</button>
			<button onclick="location.href='faqService.do'">서비스/기타</button>
		</div>
		<!-- FAQ 카테고리 끝 -->
		<!-- FAQ 내용 시작 -->
		<!-- <div class="info_box">
		<p>제목을 클릭하시면 세부내용을 확인하실 수 있습니다.</p>
		</div> -->
		<div id="info_wrap" class="toggle">
			<h4 class="on"><a href="#">Q : 결제 방법은 어떤것이 있나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : 신용카드 및 체크카드, 무통장 입금, 휴대폰 소액결제, 네이버페이를 이용해 결제 가능합니다.</li>
				</ul>
			</div>
			<h4><a href="#">Q : 신용카드 무이자 할부가 되나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : 각 카드사 별로 상이하며, 카드사를 통해 확인 가능합니다.</li>
				</ul>
			</div>
			<h4><a href="#">Q : 신용카드 결제 후 할부 개월 수를 변경 가능한가요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 결제 후 결제 정보 변경은 불가능 합니다.
						<br>
						단, 결제 완료 단계에서는 취소 후 재주문을 통해 변경 가능합니다.
					</li>
				</ul>
			</div>
			<h4><a href="#">Q : 주문 후 결제 방법을 변경하고 싶은데 어떻게 해야 하나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 결제 후 결제 정보 변경은 불가능합니다.
						<br>
						단, 입금 대기 및 결제 완료 단계에서는 취소 후 재주문을 통해 변경 가능합니다.
					</li>
				</ul>
			</div>
			<h4><a href="#">Q : 결제 시 에러 메시지가 나오는 경우 어떻게 해야 하나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 문의게시판에 글을 남겨주시건 고객센터로 전화 주시기 바랍니다.
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