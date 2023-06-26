<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<title>이벤트 수정 폼</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/admin_updateForm.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript"> 

	$(function(){ 
		$('#update_form').submit(function(){
			
			//종료날짜가 시작 날짜보다 큰 경우
			var startDate = $('#event_start').val();
			var endDate = $('#event_end').val();

			var startArray = startDate.split('-');
			var endArray = endDate.split('-');
			//배열에 담겨있는 연월일을 사용해서 Date 객체 생성
			var start_date = new Date(startArray[0], startArray[1], startArray[2]);
			var end_date = new Date(endArray[0], endArray[1], endArray[2]);
			//날짜를 숫자형대의 날짜 정보로 변환하여 비교한다.
			if(start_date.getTime() > end_date.getTime()) {
				alert('시작일은 종료일보다 클 수 없습니다')
				return false;
				}
			
			
			if($('#event_title').val().trim()==''){
				alert('제목을 입력하세요');
				$('#title').val('').focus(); 
				return false;  
			}
			
			if($('#event_content').val()==''){ 
				alert('내용을 입력하세요');
				$('#content').focus();
				return false;
			}

			if($('#event_photo').val()==''){ 
				alert('사진을 선택하세요');
				$('#photo').focus();
				return false;
			}
			
			if($('#event_start').val().trim()==''){
				alert('이벤트 시작일을 입력하세요');
				$('#event_start').val('').focus();
				return false;
			}
			
			if($('#event_end').val().trim()==''){
				alert('이벤트 종료일을 입력하세요');
				$('#event_end').val('').focus();
				return false;
			}
			
			if($('#winner_count').val()==''){
				alert('당첨자 수를 입력하세요');
				$('#winner_count').focus();
				return false;
			}
			
		});
		
	});
	
</script>

</head>
<body>

<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<!--  내용 시작 -->
<div class="content-main">
	<h2>글 수정</h2>	
	<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
	
	<ul> 
		<li>
			<label for="event_title">제목</label>
			<input type="text" name="event_title" value="${event.event_title}" id="event_title" maxlength="50">
		</li>
		
		<li>
			<label for="event_content">내용</label>
			<textarea rows="5" cols="30" name="event_content" id="event_content" maxlength="50">${event.event_content}</textarea>
		</li>
		
		<li>
			<label for="event_photo">사진</label>
			<input type="file" name="event_photo" id="event_photo" accept="image/gif, image/png, image/jpeg">
				<%--파일이 있으면 보여지도록 --%> 
				<c:if test="${!empty event.event_photo}">
					<div id="file_detail">
						(${event.event_photo}) 파일이 등록되어있습니다.	
						<input type="button" value="파일삭제" id="file_del">
					</div>
					<script type="text/javascript">
						$(function(){
							$('#file_del').click(function(){
								
								let choice = confirm('삭제하시겠습니까?');
								
								if(choice){
									$.ajax({
										url : 'deleteFile.do',
										type : 'post',
										data : {event_num : ${event.event_num}}, 
										dataType : 'json',
										success : function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용가능');
											} else if(param.result== 'success'){
												$('#file_detail').hide(); //파일 삭제됐으므로 파일명을 안 보이게 해줌
											} else if(param.result== 'wrongAccess'){
												alert('잘못된 접근');
											} else { //오타 내거나 그 외
												alert('파일 삭제 오류 발생');
											}
											
										},
										error : function(){
											alert('네트워크 오류 발생');
										}
									});	
								}
								
							});
							
						});
					
					</script>
				</c:if>	
		</li>
		
		<li>
			<label for="event_start">이벤트 시작일</label>
			<input type="date" name="event_start" id="event_start" value="${event.event_start}">
		</li>
		
		<li>
			<label for="event_end">이벤트 종료일</label>
			<input type="date" name="event_end" id="event_end" value="${event.event_end}">
		</li>
		
		<li>
			<label for="winner_count">당첨자수</label>
			<input type="number" name="winner_count" id="winner_count" min="0" max="100" value="${event.winner_count}">
		</li>
		
		
	<input type="hidden" name="event_num" value="${event.event_num }">
	<input type="hidden" name="event_status" value="${event.event_status}">

	</ul>
	
	<div class="align-center">
		<input type="submit" value="수정">
	</div>
	
	</form>
</div>		
<!--  내용 끝 -->
</div>

</body>
</html>