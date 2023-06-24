<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<style type="text/css">

form {
	width: 600px;
	margin: 0 auto;
	padding: 10px 10px 10px 30px;
	align-items: center;
	justify-content: center;
}

form ul li {
	clear: both;
}

ul {
	list-style: none;
}

label {
	width: 130px;
	float: left; /*태그를 왼쪽에 정렬*/
}

input {
	margin-top: 4px;
}

input[type="text"], input[type="password"], input[type="email"] {
	width: 310px;
}

#register_form input[id="id"] {
	width: 223px;
}

input[id="zipcode"] {
	width: 205px;
}

input[type="number"] {
	width: 100px;
}

.form-notice {
	margin-left: 130px;
}
input[type="submit"]{
	width : 500px;
	text-align: center;
}


</style>


<script type="text/javascript">
	$(function(){
		//0:중복 체크 미실시, id 중복
		//1:id 미중복
		let idChecked = 0;
		
		//아이디 중복 체크
		$('#id_check').click(function(){
			
			if(!/^[A-Za-z0-9]{4,12}$/.test($('#id').val())){
				alert('영문 또는 숫자 사용, 최소 4자 ~ 최대 12자를 사용하세요');
				$('#id').val('');
				$('#id').focus();
				return false;
			}
			
			//서버와의 통신
			$.ajax({
				url:'checkDuplicatedId.do',
				type:'post',
				data:{id:$('#id').val()},
				dataType:'json',
				success:function(param){
					if(param.result == 'idNotFound'){
						//id 미중복
						idChecked = 1;
						$('#message_id').css('color','blue')
						                .text('등록 가능 ID');
					}else if(param.result == 'idDuplicated'){
						//id 중복
						idChecked = 0;
						$('#message_id').css('color','red')
						                .text('중복된 ID');
						$('#id').val('').focus();
					}else{
						idChecked = 0;
						alert('아이디 중복 체크 오류 발생');
					}
				},
				error:function(){
					idChecked = 0;
					alert('네트워크 오류 발생');
				}
			});
			
		});//end of click
		
		//아이디 중복 안내 메시지 초기화 및 아이디
		//중복 값 초기화
		$('#register_form #id').keydown(function(){
			idChecked = 0;
			$('#message_id').text('');
		});//end of keydown
		
		//회원 정보 등록 유효성 체크
		$('#register_form').submit(function(){
			let items = document.querySelectorAll(
					   'input[type="text"],input[type="password"],input[type="email"]');
			 for(let i=0;i<items.length;i++){
				 
			    if(items[i].value.trim()==''){
					let label = 
						document.querySelector(
					 'label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 항목 필수 입력');
					items[i].value = '';
					items[i].focus();
					return false;
			    }
			    
			    if(items[i].id == 'id' && 
			    	 !/^[A-Za-z0-9]{4,12}$/.test(
			    	             $('#id').val())){
					alert('영문 또는 숫자 사용, 최소 4자 ~ 최대 12자를 사용하세요');
					$('#id').val('');
					$('#id').focus();
					return false;
				}
			    
			    if(items[i].id == 'id' && 
			    		            idChecked == 0){
					alert('아이디 중복 체크 필수');
					return false;
			    }
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
		<h2>회원가입</h2>
		<form id="register_form" 
		  action="registerUser.do" method="post">
			<ul>
				<li>
					<label for="id"><b>아이디</b></label>
					<input type="text" name="id"
					   id="id" maxlength="12"
					   autocomplete="off" placeholder="아이디">
					<input type="button" 
					 value="id중복체크" id="id_check">
					<span id="message_id"></span>
					<div class="form-notice">*영문 또는 숫자(4자~12자)</div>    
				</li>
				<li>
					<label for="name"><b>이름</b></label>
					<input type="text" name="name"
					  id="name" maxlength="10" placeholder="이름">
				</li>
				<li>
					<label for="password"><b>비밀번호</b></label>
					<input type="password" name="password"
					  id="password" maxlength="12" placeholder="비밀번호">
				</li>
				<li>
					<label for="phone"><b>전화번호</b></label>
					<input type="text" name="phone"
					  id="phone" maxlength="15" placeholder="전화번호">
				</li>
				<li>
					<label for="email"><b>이메일</b></label>
					<input type="email" name="email"
					  id="email" maxlength="50" placeholder="이메일">
				</li>
				<li>
					<label for="zipcode"><b>우편번호</b></label>
					<input type="text" name="zipcode"
					  id="zipcode" maxlength="5" placeholder="우편번호">
					<input type="button" value="우편번호 찾기"
					 onclick="execDaumPostcode()">  
				</li>
				<li>
					<label for="address1"><b>주소</b></label>
					<input type="text" name="address1"
					  id="address1" maxlength="30" placeholder="주소">
				</li>
				<li>
					<label for="address2"><b>상세주소</b></label>
					<input type="text" name="address2"
					  id="address2" maxlength="30" placeholder="상세주소">
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="회원가입하기">
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
        // iframe을 넣은 element를 안보이게 한다.
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




