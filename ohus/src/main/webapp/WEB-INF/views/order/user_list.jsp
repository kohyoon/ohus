<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sys/user_list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').on('submit', function(){
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요');
				$('#keyword').val('').focus();
				return false;
			}
			if($('#keyfield').val()==1 && isNan($('#keyword').val())){
				alert('주문번호는 숫자만 입력하세요');
				$('#keyword').val('').focus();
				return false;
			}
		})
	})
</script>
</head>
<body>
<div class = "page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<!-- 내용 시작 -->
	<div class = "content-main">
		<h3>구매 목록</h3>
		<form action = "orderList.do" method = "get" id = "search_form">
			<ul class = "search">
			<%-- 검색창 카테고리 --%>
				<li>
					<select name = "keyfield" id = "keyfield">
						<option value = "1" <c:if test = "${param.keyfield == 1}">selected</c:if>>주문번호</option>
						<option value = "2" <c:if test = "${param.keyfield == 2}">selected</c:if>>상품명</option>
					</select>
				</li>
				<li>
					<input type = "search" size = "16" name = "keyword" id = "keyword" value = "${param.keyword}">
				</li>
				<li>
					<input type = "submit" value = "검색">
				</li>
			</ul>
		</form>
		<div class = "align-right">
			<input type = "button" value = "전체목록" onclick = "location.href='orderList.do'">
		</div>
		<c:if test = "${count == 0}">
			<div class = "result-display">
				<span>표시할 주문내역이 없습니다.</span>
			</div>
		</c:if>
		<c:if test = "${count > 0}">
			<table>
				<tr>
					<th>주문번호</th>
					<th>상품명</th>
					<th>총구매금액</th>
					<th>주문날짜</th>
					<th>상태</th>
				</tr>
				<c:forEach var="order" items="${list}">
					<tr>
						<td>
							<a href = "orderModifyForm.do?order_num=${order.order_num}">
								${order.order_num}
							</a>
						</td>
						<td>
							${order.item_name}
						</td>
						<td class = "align-center"><fmt:formatNumber value = "${order.order_total}"/>원</td>
						<td class = "align-center">${order.order_regdate}</td>
						<td class = "align-center">
							<c:if test = "${order.order_status == 1}">배송대기</c:if>
							<c:if test = "${order.order_status == 2}">배송준비중</c:if>
							<c:if test = "${order.order_status == 3}">배송중</c:if>
							<c:if test = "${order.order_status == 4}">배송완료</c:if>
							<c:if test = "${order.order_status == 5}">주문취소</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			<div class = "align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>
















