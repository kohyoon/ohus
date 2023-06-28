<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기본 마이페이지 - 프로필</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/myPage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>

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
</script>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/member/myPageheader.jsp"/>
		<!-- 프로필 시작 -->
		<!-- 왼쪽부분 - 사진 -->
			<!-- 프로필 사진 시작 -->
			<div class="align-left">
				<ul>
					<li>
						<c:if test="${empty member.photo}">
						<img src="${pageContext.request.contextPath}/images/face.png"
						   width="200" height="200" class="my-photo">
						</c:if>
						<c:if test="${!empty member.photo}">
						<img src="${pageContext.request.contextPath}/upload/${member.photo}"
						   width="200" height="200" class="my-photo">
						</c:if>
					</li>
					<li>
						<div class="align-center">
							<input type="button" value="수정" id="photo_btn">
						</div>
						<div id="photo_choice" style="display:none;">
							<input type="file" id="photo" accept="image/gif,image/png,image/jpeg">
							<br>
							<input type="button" value="전송" id="photo_submit">
							<input type="button" value="취소" id="photo_reset">                        
						</div>
					</li>
				</ul>
			</div>	
			<!-- 프로필 사진 끝 -->
			
			<!-- 오른쪽 내가 커뮤니티에 올린 사진 -->
			<div class="align-right">
				<div class="mypage-div">
					<table>
						<c:forEach var="cboard" items="${cboardList}">
							<tr> 
								<td>
									<a href="${pageContext.request.contextPath}/cboard/detail.do?cboard_num=${cboard.cboard_num}">
										<img src="${pageContext.request.contextPath}/upload/${cboard.cboardVO.photo1}" width="80">
									</a>
								</td>
							</tr>
						</c:forEach>	
					</table>
				</div>
			</div>
				
		    <!-- 오른쪽 커뮤니티에 올린 사진 끝  -->
	    
	     <!-- 상추글, 문의내용, 스크랩 -->
<!-- <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
function toggleSubMenu(element) {
  var submenu = element.nextElementSibling;
  submenu.classList.toggle("show");
}

$(document).ready(function() {
  // 하위 메뉴 초기 상태는 숨김으로 설정
  $('.submenu').hide();

  // 상단 메뉴 클릭 이벤트 처리
  $('.toggle').click(function() {
    // 다른 하위 메뉴 숨김
    $('.submenu').hide();
    
    // 클릭한 상단 메뉴의 하위 메뉴만 보이게 설정
    $(this).siblings('.submenu').show();
  });
});
</script -->



</body>
</html>



