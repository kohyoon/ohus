<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품구매상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<c:if test = "${order.order_status < 2}">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//주문 유효성 체크
		$('#order_modify').submit(function(){
			// 배송대기일 경우만 조건체크
			if($('input[type=radio]:checked').val()==1){
				let items = document.querySelectorAll('input[type="text"]');
				 for(let i=0;i<items.length;i++){
				    if(items[i].value.trim()==''){
						let label = document.querySelector('label[for="'+items[i].id+'"]');
						alert(label.textContent + ' 항목 필수 입력');
						items[i].value = '';
						items[i].focus();
						return false;
				    }
				}
			} 
		}) 
		let origin_status = ${order.order_status};
		$('input[type=radio]').click(function(){
			if(origin_status == 1 && $('input[type=radio]:checked').val()!=1){
				$('input[type=text],textarea').parent().hide();
			} else {
				$('input[type=text],textarea').parent().show();
			}
		}) 
	});
</script>
</c:if>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>구매 내역</h2>
		<hr size = "1" noshade = "noshade" width = "100%">
		<table>
			<tr>
				<th>상품명</th>
				<th>수량</th>
				<th>상품가격</th>
				<th>합계</th>
			</tr>
			<c:forEach var="detail" items="${detailList}">
			<td>
				<a href = "${pageContext.request.contextPath}/item/detail.do?item_num=${cart.item_num}">
					<img src = "${pageContext.request.contextPath}/upload/${cart.itemVO.item_photo1}" width = "80"> <!-- 사진 크기 -->
					${cart.itemVO.item_name}
				</a>
			</td>
			<tr>
				<td>
					${detail.item_name}
				</td>
				<td class="align-center">
					<fmt:formatNumber 
					     value="${detail.order_quantity}"/>
				</td>
				<td class="align-center">
					<fmt:formatNumber
					     value="${detail.item_price}"/>원
				</td>
				<td class="align-center">
					<fmt:formatNumber
					     value="${detail.item_total}"/>원
				</td>
				<td>
					<a href="${pageContext.request.contextPath}/item/userReviewForm.do?item_num=${detail.item_num}">후기작성</a>
				</td>
			</tr>	
			</c:forEach>
			<tr>
				<td colspan="3" class="align-right">
				 	<b>총구매금액</b>
				</td>
				<td class="align-center">
					<fmt:formatNumber value="${order.order_total}"/>원
				</td>
			</tr>
		</table>
		<form id="order_modify" 
		  action="orderModify.do" method="post">
		  <input type = "hidden" name = "order_num" value = "${order.order_num}">
		  <input type = "hidden" name = "status" value = "${order.order_status}">
			<ul>
				<c:if test = "${order.order_status < 2}">
				<li>
					<span id = "delivery_text">*배송대기일 경우만 배송관련 정보를 수정할 수 있습니다.</span>
				</li>
				<li>
					<label for="order_name">수령자</label>
					<input type="text" name="order_name" id="order_name" 
						maxlength="10" value = "${order.order_name}">
				</li>
				<li>
					<label for="mem_phone">주문자 전화번호</label>
					<input type="text" name="mem_phone" id="mem_phone" 
						maxlength="15" value = "${order.mem_phone}">
				</li>
				<li>
					<label for="zipcode">우편번호</label>
					<input type="text" name="order_zipcode" id="zipcode" 
						maxlength="5" value = "${order.order_zipcode}">
					<input type="button" value="우편번호 찾기" onclick="execDaumPostcode()">  
				</li>
				<li>
					<label for="address1">주소</label>
					<input type="text" name="order_address1" id="address1" 
						maxlength="30" value = "${order.order_address1}">
				</li>
				<li>
					<label for="address2">나머지 주소</label>
					<input type="text" name="order_address2" id="address2" 
						maxlength="30" value = "${order.order_address2}">
				</li>
				<li>
					<label for="order_notice">남기실 말씀</label>
					<textarea rows="5" cols="30" name="order_notice" id="order_notice" maxlength="1300"
					   >${order.order_notice}</textarea>
				</li> 
				</c:if> 
				<c:if test = "${order.order_status >= 2}">
				<li>
					<label>수령자</label>
					${order.order_name}
				</li>
				<li>
					<label>전화번호</label>
					${order.mem_phone}
				</li>
				<li>
					<label>우편번호</label>
					${order.order_zipcode}
				</li>
				<li>
					<label>주소</label>
					${order.order_address1}
				</li>
				<li>
					<label>상세주소</label>
					${order.order_address2}
				</li>
				<li>
					<label>남기실 말씀</label>
					${order.order_notice}
				</li>
				</c:if>
				<li>
					<label>결제 수단</label>
					<c:if test = "${order.order_payment == 1}">은행입금</c:if>
					<c:if test = "${order.order_payment == 2}">카드결제</c:if>   
				</li>
				<li>
					<label>배송상태</label>
					<c:if test = "${order.order_status == 1}">배송대기</c:if>
					<c:if test = "${order.order_status == 2}">배송준비중</c:if>
					<c:if test = "${order.order_status == 3}">배송중</c:if>
					<c:if test = "${order.order_status == 4}">배송완료</c:if>
					<c:if test = "${order.order_status == 5}">주문취소</c:if>
				</li>
			</ul> 
			<div class="align-center">
				<c:if test = "${order.order_status < 2}"><%-- 사용자모드이기때문에 status가 1일 경우에만 수정 가능 --%>
					<input type="submit" value="수정">
					<input type = "button" value = "주문취소" id = "order_cancel">
					<script type="text/javascript">
						let order_cancel = document.getElementById('order_cancel');
						order_cancel.onclick = function(){
							let choice = confirm('주문을 취소하시겠습니까?');
							if(choice) {
								location.replace('orderCancel.do?order_num=${order.order_num}');
							}
						}
					</script>
				</c:if>
				<c:if test = "${order.order_status == 4}">
					<input type="button" value="상품후기작성" onclick = "">
				</c:if>
				<input type = "button" value = "MY페이지"
				 onclick = "location.href='${pageContext.request.contextPath}/member/myPage.do'">
				<input type="button" value="주문목록" onclick="list.do">
			</div> 
		</form>
	</div>
	<!-- 내용 끝 -->
<!-- 우편번호 검색 시작 -->
<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        // ifame을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
    }

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    //(주의)address1에 참고항목이 보여지도록 수정
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //(수정) document.getElementById("address2").value = extraAddr;
                
                } 
                //(수정) else {
                //(수정)    document.getElementById("address2").value = '';
                //(수정) }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode;
                //(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
                document.getElementById("address1").value = addr + extraAddr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address2").focus();

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 400; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 5; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>
	<!-- 우편번호 검색 끝 -->
</div>
</body>
</html>




