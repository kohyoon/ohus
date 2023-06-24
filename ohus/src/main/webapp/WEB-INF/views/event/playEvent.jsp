<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 추첨결과</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<!--  내용 시작 -->
		<div class="content-main">
			<h2>당첨 결과</h2>
			
			<!-- 우측 버튼 시작 -->
			<div class="list-space align-right">
				<input type="button" value="목록" onclick="location.href='endEventList.do'"> 
			</div>	
			<!-- 우측 버튼 끝 -->
			
			<!-- =========오른쪽 버튼처리 끝 =========-->
			
			<!-- ======이벤트 글 리스트 처리 시작=========== -->
			
			<%-- 	<c:forEach var="replyList" items="${list}">
					
										${replyList.re_ip }
									
				
			</c:forEach> --%>
			<!-- ======이벤트 글 리스트 처리 끝=========== -->		
		</div>
		<!-- 내용 끝 -->
	</div>
</body>
</html>