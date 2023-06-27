<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터 메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/faq.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/koy/notice.css">
<style type="text/css">
.title{
    margin:30px 0;
	width:100%;
	float:center;
 	display:inline-block;
 	text-align:center;
 	
}
.title img{
	padding:0;
}
.title span{
    padding:10px;
	font-size:40px;
	font-weight:bold;
}

.align-left{
    width: 800px;
    height: auto;
    margin: 0 auto;
    margin-top: 36px;
    border-top: 1px solid #e9e9e9;
    padding-top: 10px;
    float: left; /* Added to align sections to the left */
  	clear: both; /* Added to clear float */
}

#faq_div{
	width:45%;
	float:left;
}

#faq_category button:hover{
	background-color:#163566;
}

#inquiry_div{
	width:45%;
	border:none;
	border-radius:10px;
	background:#dedede;
	float: right;
  	margin-top: 10px;
  	padding: 10px;
}

#inquiry_div span{
	padding:10px;
	font-size:24px;
	font-weight:bold;
}

#inquiry_btn {
	width: 100%;
	padding: 13px;
	background-color: #35c5f0 ;
	border: none;
	color: white;
	font-weight: bold;
	font-size : 15px;
	cursor: pointer;
	margin-right: 200px;
	margin-top: 10px;
	text-align:center;
	clear:both;
}

#inquiry_btn:hover{
	background-color: #09addb;
	color : white;
}

#notice_div{
	width:100%;
	clear:both;
  	margin-top: 10px;
  	padding: 10px;
}


</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="home-page">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="container">
		<div class="title">
			<img src="${pageContext.request.contextPath}/images/customer-service.png" width="30" height="33">
			<span>고객센터</span>
		</div>
	<div>
		<div id="faq_div">
			<h2>
				<a href="${pageContext.request.contextPath}/faq/faqPay.do">FAQ</a>
			</h2>
			<div id="faq_category">
				<button onclick="location.href='${pageContext.request.contextPath}/faq/faqPay.do'">결제</button>
				<button onclick="location.href='${pageContext.request.contextPath}/faq/faqDelivery.do'">배송관련</button>
				<p>
				<button onclick="location.href='${pageContext.request.contextPath}/faq/faqMemInfo.do'">회원정보</button>
				<button onclick="location.href='${pageContext.request.contextPath}/faq/faqService.do'">서비스/기타</button>
			</div>
		</div>
		<div id="inquiry_div">
			<span>고객센터 09:00 ~ 18:00</span>
			<ul style="list-style-type: disc;">
				<li>평일 : 전체 문의 가능</li>
				<li>주말/공휴일 : 내일의집 직접배송에 한해 전화 상담 가능</li>
			</ul>
			<img src="${pageContext.request.contextPath}/images/telephone.png" width="23" height="26">
			<span>1670-0876</span>
			<br>
			<button id="inquiry_btn" onclick="location.href='${pageContext.request.contextPath}/inquiry/listInquiry.do'">문의게시판</button>
		</div>
		<div id="notice_div">
			<h2>
				<a href="${pageContext.request.contextPath}/notice/listNotice.do">공지사항</a>
			</h2>
			<table>
				<tr style="pointer-events: none;">
					<th width="70%">제목</th>
					<th>작성일</th>
				</tr>
				<c:forEach var="notice" items="${noticeList}">
				<tr>
					<td width="80%" style="text-align:left;"><a href="${pageContext.request.contextPath}/notice/detailNotice.do?notice_num=${notice.notice_num}"><b>[공지]</b> ${notice.notice_title}</a></td>
					<td>${notice.notice_regdate}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	
	
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>   
</div>
</body>
</html>