<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 글등록 폼</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">

	$(function(){
		$('#write_form').submit(function(){ 
			
			//유효성 검사(조건) 시작 - 입력되지 않은 경우
			
			//제목을 입력하지 않은 경우
			if($('#event_title').val().trim()==''){
				alert('제목을 입력하세요');
				$('#title').val('').focus();
				return false;
			}
			
			//내용을 입력하지 않은 경우
			if($('#event_content').val()==''){ //숫자라 num 에서 trim은 알아서 체크해줌
				alert('내용을 입력하세요');
				$('#content').focus();
				return false;
			}

			
			//사진 x
			if($('#event_photo').val()==''){ //파일도 마찬가지로 trim() 없이, 입력하는게 아니라 파일 선택하는 것이므로
				alert('사진을 선택하세요');
				$('#photo').focus();
				return false;
			}
			
			//이벤트 시작
			if($('#event_start').val().trim()==''){
				alert('이벤트 시작일을 입력하세요');
				$('#event_start').val('').focus();
				return false;
			}
			//이벤트 종료일
			if($('#event_end').val().trim()==''){
				alert('이벤트 종료일을 입력하세요');
				$('#event_end').val('').focus();
				return false;
			}
			//당첨자수
			if($('#winner_count').val()==''){
				alert('당첨자 수를 입력하세요');
				$('#winner_count').focus();
				return false;
			}
			
		});
	
	
	})
	


</script>
</head>
<body>

	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<!--  내용 시작 -->
		<div class="content-main">
			<h2>이벤트 등록</h2>	
			<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
				<ul>
					<li>
						<label for="event_title">제목</label>
						<input type="text" name="event_title" id="event_title" maxlength="50">
					</li>
					
					<li>
						<label for="event_content">내용</label>
						<textarea rows="5" cols="30" name="event_content" id="event_content" maxlength="50"></textarea>
					</li>
					
					<li>
						<label for="event_photo">사진</label>
						<input type="file" name="event_photo" id="event_photo" accept="image/gif, image/png, image/jpeg">
					</li>
					
					<li>
						<label for="event_start">이벤트 시작일</label>
						<input type="date" name="event_start" id="event_start">
					</li>
					
					<li>
						<label for="event_end">이벤트 종료일</label>
						<input type="date" name="event_end" id="event_end">
					</li>
					
					<li>
						<label for="winner_count">당첨자수</label>
						<input type="number" name="winner_count" id="winner_count" min="0" max="100">
					</li>
					
				</ul>
			
				<div class="align-center">
					<input type="submit" value="등록">
					<input type="submit" value="목록" onclick="location.href='list.do'"> <!-- 등록을 안 하고 싶을 때 목록으로 이동하게 버튼 만들어줌 -->
				</div>
			
			
			</form>
		</div>		
		<!--  내용 끝 -->
	</div>

</body>

</html>