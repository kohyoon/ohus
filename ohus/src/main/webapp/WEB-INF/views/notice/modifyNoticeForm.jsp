<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>공지사항 글 수정</h2>
		<form id="modify_form" action="modifyNotice.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="notice_num" value="${notice.notice_num}">
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" value="${notice.notice_title}" id="title" maxlength="30">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content" id="content">${notice.notice_content}</textarea>
				</li>
				<li>
					<label for="filename">파일</label>
					<input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty notice.notice_filename}">
					<div id="file_detail">
						(${notice.notice_filename})파일이 등록되어 있습니다.
						<input type="button" value="파일삭제" id="file_del">
					</div>
					<script type="text/javascript">
						$(function(){
							$('#file_del').click(function(){
								let choice = confirm('파일을 삭제하시겠습니까?');
								if(choice){
									$.ajax({
										url:'deleteNotice.do',
										type:'post',
										data:{notice_num:${notice.notice_num}},
										dataType:'json',
										success:function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용하세요.');
											}else if(param.result == 'success'){
												$('#file_detail').hide();
											}else if(param == 'wrongAccess'){
												alert('잘못된 접근입니다.');
											}else{
												alert('파일 삭제 오류 발생!');
											}
										},
										error:function(){
											alert('네트워크 오류 발생');
										}
									});
								}
							}); //end of click
						});
					</script>
					</c:if>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="이전" onclick="location.href='detailNotice.do?notice_num=${notice.notice_num}'">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>