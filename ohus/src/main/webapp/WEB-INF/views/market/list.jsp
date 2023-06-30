<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상추 마켓</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ssk/market_list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('input[type="search"]').attr('placeholder','상추 마켓에서 검색');
		
		function searchData(){
			let data = $('input[type="search"]').val();
			location.href="list.do?keyfield=1&keyword="+data;
		};
		
		$('input[type="search"]').keypress(function(){
			if(event.keyCode==13){
				searchData();	
			}
			
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<c:if test="${count == 0}">
		<div class="result-display">
			등록된 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<div class="content-wrap">
			<c:forEach var="market" items="${list}">
			<div class="box-item">
				<a href="detail.do?market_num=${market.market_num}">
					<div class="img-box">
						<img src="${pageContext.request.contextPath}/upload/${market.market_photo1}" width="250" height="250" class="market-photo"><br>
					</div>
					<h3 class="box-title">${market.market_title}</h3>
					<div class="box-writer">
						<c:if test="${market.market_status == 1}"><span class="sold">거래완료</span></c:if>
						<c:if test="${market.market_status == 0}"><span class="selling">판매중</span></c:if>
						${market.id}
					</div>
					<div class="box-date-hit">
						${market.market_regdate} 조회수 ${market.market_hit}
					</div>
				</a>
			</div>
			</c:forEach>
		</div>
		<div class="page">${page}</div>
		</c:if>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>