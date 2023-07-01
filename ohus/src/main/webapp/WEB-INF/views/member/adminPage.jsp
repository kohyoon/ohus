<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 마이페이지 - 회원관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/table.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

</head>
<body>
<div>
	<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요');
				$('#keyword').val('').focus();
				return false;
			}		
		});
	});
</script>
</head>

<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<jsp:include page="/WEB-INF/views/member/adminPageheader.jsp"/>
		<!--  내용 시작 -->
		<div class="content-main">
			<h2>회원 목록(관리자 전용)</h2>
			<!--  검색창 시작 --> 
			<form id="search_form" action="adminPage.do" method="get"> <%-- 자기 자신, 이 jsp파일을 호출함 --%>
			<ul class="search">
				<li>
					<select name="keyfield"> <%-- option 태그 안에 if를 넣어 조건체크를 해주고 selected는 선택된 것 표시 --%>
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>아이디</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>이름</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>이메일</option>
						<option>
						<%--만약에 아이디 검색에 내용을 적고 옵션을 이메일로 바꿔도 내용이 남아있도록 처리한 것! --%>
					</select>
				</li>
				
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
								
			</ul>
			
			</form>
		
			<!--  검색창 끝 -->
			
			<!-- 글쓰기 -->
			<div class="list-space align-right">
				
				<input type="button" value="목록" onclick="location.href='adminPage.do'"> <!-- 목록에서 목록 넣는 이유 -->
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
			<!-- 글쓰기 끝 -->
			
			<c:if test="${count ==0 }"> <!-- 표시할 게시물이 없음 -->
				<div class="result-display">
					표시할 회원 정보가 없습니다.
				</div>
			</c:if>
			
			<c:if test="${count>0 }"> <!-- 목록이 있는 경우. 테이블로 작업 -->
				<table>
					<!-- 항목 -->
					<tr>
						<th>아이디</th>
						<th>이름</th>
						<th>이메일</th>
						<th>전화번호</th>
						<th>가입일</th>
						<th>등급</th>
						<th>신고누적횟수</th>
					</tr>
					<!-- 레코드는 반복문으로 작업해줌, 확장 for문같은 역할 -->
					<c:forEach var="member" items="${list}"> 
						<tr>
							<td>
								<c:if test="${member.auth>0}">
									<a href="detailUserForm.do?mem_num=${member.mem_num}">${member.id}</a> <%-- 상세 정보 보기 위해 링크 만들기 --%>
								</c:if>
								<c:if test="${member.auth ==0 }">
									${member.id}
								</c:if>
							</td>
							
							<td>${member.name }</td> <!-- 제목을 누르면 상세 페이지를 볼 수 있도록 링크 처리 -->
							<td>${member.email }</td>
							<td>${member.phone }</td>
							<td>${member.reg_date }</td>
							<td>
								<c:if test="${member.auth==0}">탈퇴회원</c:if>
								<c:if test="${member.auth==1}">정지회원</c:if>
								<c:if test="${member.auth==2}">일반회원</c:if>
								<c:if test="${member.auth==9}">관리자</c:if>
							</td>
							<td>${member.reports }
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- 페이지 보여주기 -->
			</c:if>			
		</div>
		<!-- 내용 끝 -->
	<!-- 내용 끝 -->
</div>
</body>
</html>