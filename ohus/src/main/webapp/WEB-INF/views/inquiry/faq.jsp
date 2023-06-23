<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FAQ</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy.css">
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
		<section>
			<h1><a href="faq.do">FAQ</a></h1>
			<nav>
				<label>결제</label>
				<label>회원정보</label>
				<label>서비스</label>
				<label>기타</label>
			</nav>
			<ul>
				<li>
					<span class="faq-mark">Q</span>
					<h3>
						<button>
							<span class="question-text">결제 방법은 어떤것이 있나요?</span>
						</button>
					</h3>
					<div>
						<p>신용카드 및 체크카드, 무통장 입금, 휴대폰 소액결제, 네이버페이를 이용해 결제 가능합니다.</p>
					</div>
				</li>
				
				<li>
					<span class="faq-mark">Q</span>
					<h3>
						<button>
							<span class="question-text">신용카드 무이자 할부가 되나요?</span>
						</button>
					</h3>
					<div>
						<p>각 카드사 별로 상이하며, 카드사를 통해 확인 가능합니다.</p>
					</div>
				</li>
			
			</ul>
		</section>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>