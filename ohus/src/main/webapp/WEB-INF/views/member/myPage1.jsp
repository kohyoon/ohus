<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의쇼핑</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/myPage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/table.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
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
<style>
   .tables-container {
        display: flex;
        justify-content: space-between;
    }

    .table-container {
        width: 55%;
        box-sizing: border-box;
        margin-right: 40px;
    }

    .table-container:first-child {
        margin-right: 5%;
    }

    .table-container:last-child {
        margin-left: 5%;
    }

.table-container h3{
text-align: left;
margin-left: 40px;
}

    .table-container table {
        width: 100%;
    }

    .table-container th,
    .table-container td {
        padding: 5px;
        border: 1px solid #ddd;
    }
</style>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/member/myPageheader.jsp"/>
<div class="page-main">
<div class="content-main">
<!-- 내용 시작 -->
	<div class = "tables-container">
		<div class="table-container">
		    <h3>주문내역</h3>
		    
		    <c:if test="${!empty orderList }">
		    <table>
			    <tr>
				    <th>주문번호</th>
			        <th>상품명</th>
			        <th>주문날짜</th>
			        <th>배송상태</th>
			    </tr>
			    
			    <c:forEach var="order" items="${orderList}">
					<tr> 
						<td>
							<a href="${pageContext.request.contextPath}/order/orderModifyForm.do?order_num=${order.order_num}">
							${order.order_num}</a>
						</td>
						 <td>${order.item_name}</td> 
						 <td>(${order.order_regdate})</td>
						 <td>
							<c:if test="${order.order_status==1 }">배송대기</c:if>
							<c:if test="${order.order_status==2 }">배송준비중</c:if>
							<c:if test="${order.order_status==3 }">배송중</c:if>
							<c:if test="${order.order_status==4 }">배송완료</c:if>
							<c:if test="${order.order_status==5 }">주문취소</c:if>
						</td>
					</tr>
				</c:forEach>
		    </table>
		    </c:if>
		    
		    <c:if test="${empty orderList }">
	    		<span class="no-list">주문내역이 없습니다</span>
	    	</c:if>
		    
		    
	</div>
			
	<div class="table-container">
	
	    <h3>내가 찜한 상품</h3>
	    <c:if test="${!empty itemList }">
	    <table>
	        <tr>
	            <th>상품번호</th>
	            <th>상품명</th>
	            <th>가격</th>
	        </tr>
	        <c:forEach var="item" items="${itemList}">
	            <tr> 
	                <td>${item.item_num}</td>
	                <td><a href="${pageContext.request.contextPath}/item/detail.do?item_num=${item.item_num}">${item.item_name}</a></td>
	                <td><fmt:formatNumber value="${item.item_price}"/>원</td>
	            </tr>
	        </c:forEach>
	    </table>
	    </c:if>
	    <c:if test="${empty itemList }">
	    		<span class="no-list">찜한 상품이 없습니다</span>
	    	</c:if>
	    
	    
	</div>
	 </div>
</div>
</div>
</body>
</html>