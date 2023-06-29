<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>설정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/modifyUserForm.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
	$(function(){
		//수정버튼 이벤트 연결
		$('#photo_btn').click(function(){
			$('#photo_choice').show();
			$(this).hide();//수정버튼 감추기
		});//end of click - 수정버튼
		
		//이미지 미리 보기
		//처음 화면에 보여지는 이미지 저장
		let photo_path = $('.my-photo').attr('src');
		//선택한 이미지
		let my_photo;
		$('#photo').change(function(){
			my_photo = this.files[0];
			if(!my_photo){
				$('.my-photo').attr('src', photo_path);
				return;
			}
			//파일 용량 체크
			if(my_photo.size > 1024*1024){
				alert(Math.round(my_photo.size/1024) 
				  + 'kbytes(1024kbytes까지만 업로드 가능)');
				$('.my-photo').attr('src',photo_path);
				$(this).val('');//선택한 파일 정보 지우기
				return;
			}
			
			let reader = new FileReader();
			reader.readAsDataURL(my_photo);
			
			reader.onload=function(){$('.my-photo').attr('src', reader.result);
			};
		});//end of change		
		
		//전송버튼 이벤트 연결
		$('#photo_submit').click(function(){
			if($('#photo').val()==''){
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}
			
			//서버에 파일(사진) 전송
			let form_data = new FormData();
			form_data.append('photo',my_photo);
			$.ajax({
				url:'updateMyPhoto.do',
				type:'post',
				data:form_data,
				dataType:'json',
				contentType:false,//데이터 객체를 문자열로 바꿀지에 대한 설정,true면 일반 문자
				processData:false,//해당 타입을 true로 하면 일반 text로 구분
				enctype:'multipart/form-data',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						alert('프로필 사진이 수정되었습니다.');
						//업로드한 이미지로 초기 이미지 대체 -- 이미지를 변경하려다가 취소했을 때 face.jpg가 아닌
						//그 이후에 변경한 이미지로 남아있어야되기 때문에 중간에 바꾼 이미지를 초기 이미지로 인식하게 처리해줘야한다
						photo_path = $('.my-photo').attr('src');
						$('#photo').val('');
						$('#photo_choice').hide();
						$('#photo_btn').show();
					}else{
						alert('파일 전송 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
			
		});//end of click - 전송버튼
		
		//취소버튼 이벤트 연결
		$('#photo_reset').click(function(){
			//초기 이미지 표시
			$('.my-photo').attr('src',photo_path);
			$('#photo').val('');
			$('#photo_choice').hide();
			$('#photo_btn').show(); //수정버튼 노출
		});//end of click - 취소버튼
		
	});
	
	$(function(){
		//회원 정보 등록 유효성 체크
		$('#modify_form').submit(function(){
			let items = document.querySelectorAll('input[type="text"],input[type="email"]');
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
			}
		});
	});
</script>
<style>
.table-container {
		margin-left: 500px; /* 프로필 사진의 너비와 동일한 값으로 설정 */
		margin-bottom: 60px;	
		width : 120%;
	}


    .table-container h3{
    	margin-right: 350px;
    }
    .table-container th,
    .table-container td {
        padding: 10px;
        border: 1px solid #ddd;
    }
  
.buttons{
  display: flex;
  align-items: center;
  margin-top: 15px;
}
.buttons .btn{
  color: #fff;
  width : 70%;
  text-decoration: none;
  margin: 0 20px;
  padding: 5px 15px;
  border-radius: 17px;
  font-size: 13px;
  white-space: nowrap;
  background: linear-gradient(to left, #33ccff 0%, #ff99cc 100%);
}

.buttons .btn:hover{
  box-shadow: inset 0 5px 20px rgba(0,0,0,0.4);
}
.button-container{
	margin-bottom: 90px;
	text-align:center;
	margin-left: 10px;
	margin-right : 190px;
	margin-top:-9px;
}


</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/member/myPageheader.jsp"/>
<!-- ajax통신으로..? 아니면 회원 정보를 보여주고 정보수정, 비밀번호 버튼을 따로 만들기 -->
<!-- 회원 정보를 보여주고 버튼 생성 -->
<div class="page-main"> 
	<div class="content-main">	
		<form id="modify_form"  action="modifyUser.do" method="post">
			<ul>
			<h2>회원정보 수정</h2>
			<br>
				<li>
					<label for="name">이름</label>
					<input type="text" name="name"
					  value="${member.name}"
					  id="name" maxlength="10">
				</li>
				<li>
					<label for="phone">전화번호</label>
					<input type="text" name="phone"
					  value="${member.phone}"
					  id="phone" maxlength="15">
				</li>
				<li>
					<label for="email">이메일</label>
					<input type="email" name="email"
					  value="${member.email}"
					  id="email" maxlength="50">
				</li>
				<li>
					<label for="zipcode">우편번호</label>
					<input type="text" name="zipcode"
					  value="${member.zipcode}"
					  id="zipcode" maxlength="5">
					<input type="button" value="우편번호 찾기"
					 onclick="execDaumPostcode()">  
				</li>
				<li>
					<label for="address1">주소</label>
					<input type="text" name="address1"
					  value="${member.address1}"
					  id="address1" maxlength="30">
				</li>
				<li>
					<label for="address2">나머지 주소</label>
					<input type="text" name="address2"
					  value="${member.address2}"
					  id="address2" maxlength="30">
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="수정">
			</div> 
		</form>
	</div>
</div>
<!-- 내용 끝 -->
<div class="button-container">
		<input type="submit" onclick="location.href='modifyPasswordForm.do'" value="비밀번호변경" class="button">
		<span class="divider">|</span>
		<input type="submit" onclick="location.href='deleteUserForm.do'"value="회원탈퇴" class="button">
</div>
	
	
	<!-- 우편번호 검색 시작 -->
	<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>
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




</body>
</html>