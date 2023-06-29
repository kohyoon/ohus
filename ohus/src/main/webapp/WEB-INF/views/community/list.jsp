<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>커뮤니티-일상</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/lss/list.css">
    <script type="text/javascript">
		$(function(){
			$('input[type="search"]').attr('placeholder','커뮤니티 검색');
			
			function searchData(){
				let data = $('input[type="search"]').val();
				if(data == ''){
					alert('검색어를 입력하세요.');
					$('input[type="search"]').val('').focus();
					return false;
				}
				location.href="list.do?keyword="+data;
			};
			
			$('input[type="search"]').keypress(function(){
				if(event.keyCode==13){
					searchData();	
				}
				
			});
		});			
	</script>		
</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <jsp:include page="/WEB-INF/views/community/community_header.jsp"/>
    <!-- 내용 시작 -->
    <div class="content-main">

        <div class="result-display" style="color: #424242";>
        전체 ${count}
    	</div>
        <div class = "community-main">
        <div class="community-container">
        <c:if test="${count == 0}">
            <div class="result-display">
                표시할 게시물이 없습니다.
            </div>
        </c:if>
        
        <c:if test="${count > 0}">
            
                <c:forEach var="board" items="${list}">
                <div class="community">
                   
                           <a href="${pageContext.request.contextPath}/community/detail.do?cboard_num=${board.cboard_num}">
                            <img src="${pageContext.request.contextPath}/upload/${board.cboard_photo1}" class="photo1">
                           </a>
                            <div class="community-center" style="font-weight: bold; font-size: 16px;">${board.cboard_title}</div>

 							<!-- 가운데 정렬 -->
                         
                            <div class="community-center">
                            <c:choose>
                                <c:when test="${not empty board.photo}">
                                    <img src="${board.photo}" alt="photo" width="15" height="15">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/face.png" width="15" height="15">
                                </c:otherwise>
                            </c:choose>
                            <span style="color: #424242; font-size: 14px;">${board.id}</span>
                            <br>
                            <div class="community-center" style="color: #757575; font-size: 13px;" >좋아요 ${board.favCount} 조회 ${board.cboard_hit}</div>

                           </div>
                           </div>
                           </c:forEach>
                           </c:if>
                           </div>
                           <c:if test="${count != 0}">
                           <div class = "align-center">${page}</div>
							</c:if>
							</div>
							</div>


    <!-- 내용 끝 -->
    </div>
</body>
</html>
