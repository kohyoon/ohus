<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%-- 
<%
String page = (String) request.getAttribute("page");
if (page.equals("profile")) {
      // 프로필 페이지 처리 로직
      // ...
      return "myPage1.jsp";
    } /* else if (page.equals("shopping")) {
      // 나의 쇼핑 페이지 처리 로직
      // ...
      return "myPage2.jsp";
    } else if (page.equals("participation")) {
      // 나의 참여 페이지 처리 로직
      // ...
      return "myPage3.jsp";
    } else if (page.equals("settings")) {
      // 설정 페이지 처리 로직
      // ...
      return "myPage4.jsp";
    } */
%>
 --%>

<body>
<!-- 프로필 페이지 사진 -->
	    	<li><a href="#">사진</a></li>
			<!-- 왼쪽 : 프로필 수정가능 -->
			<div class="align-left" style="margin-top: 100px;">
				<c:if test="${empty member.photo}">
					<img src="${pageContext.request.contextPath}/images/face.png"
					   width="200" height="200" class="my-photo">
				</c:if>
				<c:if test="${!empty member.photo}">
					<img src="${pageContext.request.contextPath}/upload/${member.photo}"
					   width="200" height="200" class="my-photo">
				</c:if>
	
				<div class="align-center"> <!-- 수정버튼 클릭 시, 프로필 수정으로 이동 -->
					<input type="button" value="수정" id="photo_btn" onclick="location.href='modifyUserForm.do'">
				</div>
				
				
			</div>
			
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
				
		    	<!--  -->
	    
	      <li><a href="#">상추글</a></li>
	      <li><a href="#">스크랩</a></li>
	      <li><a href="#">문의내용</a></li>
</body>
</html>