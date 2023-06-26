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
			<button id="this_faq" onclick="location.href='faqDelivery.do'">배송관련</button>
			<button onclick="location.href='faqMemInfo.do'">회원정보</button>
			<button onclick="location.href='faqService.do'">서비스/기타</button>
		</div>
		<!-- FAQ 카테고리 끝 -->
		<!-- FAQ 내용 시작 -->
		<!-- <div class="info_box">
		<p>제목을 클릭하시면 세부내용을 확인하실 수 있습니다.</p>
		</div> -->
		<div id="info_wrap" class="toggle">
			<h4><a href="#">Q : 배송비는 얼마인가요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 내일의집은 상품정보 중계 및 판매 매체이며, 판매 업체 별로 배송비 정책이 상이합니다각 상품상세페이지에서 배송비를 확인하실 수 있습니다.
					</li>
				</ul>
			</div>
			<h4 class="on"><a href="#">Q : 배송확인은 어떻게 하나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : 우측 상단 프로필의 [나의쇼핑]을 통해 배송단계를 한눈에 보실 수 있습니다.
					<br>
					또한 배송이 시작되면 카카오톡 알림톡 또는 SMS로 안내메시지가 발송됩니다..</li>
				</ul>
			</div>
			<h4><a href="#">Q : 배송은 얼마나 걸리나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : 상품 배송 기간은 배송 유형에 따라 출고 일자 차이가 있습니다.자세한 사항은 구매하신 상품의 상세 페이지에서 확인 가능하며, 배송 유형 별 기본 출고 기간은 아래와 같습니다.
					<br>
					∙ 일반 택배 / 화물 택배 : 결제 후 1~3 영업일 이내 출고됩니다.
					<br>
					∙ 업체 직접 배송 : 배송 지역에 따라 배송 일자가 상이할 수 있으므로 상품 상세 페이지에서 확인 해주세요.
					<br>
					※ 영업일은 주말, 공휴일을 제외한 기간입니다.
					<br>
					※ 제조사의 사정에 따라 출고일은 지연될 수 있는 점 양해 부탁드립니다.
					</li>
				</ul>
			</div>
			<h4><a href="#">Q : 여러 상품을 묶음 배송 받으려면 어떻게 해야하나요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>A : 각 상품별로 배송처가 상이할 수 있기 때문에 묶음 배송은 어렵습니다.
					<br>
					단, 배송처가 같은 경우 배송처의 정책에 따라 가능 할 수 있습니다.
					</li>
				</ul>
			</div>
			<h4><a href="#">Q : 해외배송이 가능한가요?</a></h4>
			<div class="on" style="display:none">
				<ul class="basic_ul">
					<li>
						A : 현재는 국내배송만 진행하고 있습니다.
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