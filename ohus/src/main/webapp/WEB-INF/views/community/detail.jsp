<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>글상세</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/cboard.fav.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/cboard.reply.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lss/detail.css">
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var commentTextarea = document.getElementById('re_content');
        var commentMessage = document.getElementById('comment-message');

        commentTextarea.addEventListener('focus', function() {
            commentMessage.style.display = 'none';
        });

        commentTextarea.addEventListener('blur', function() {
            if (commentTextarea.value === '') {
                commentMessage.style.display = 'block';
            }
        });
    });
</script>
</head>



<body>
	<div class="header-detail">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
	</div>
<div class="page-main">
    <!-- 내용 시작 -->
    <div class="content-main">
        <div class="content-center">
        <br><br>
            <h1>${board.cboard_title}</h1><br><br><br>
            <div class="detail-info">
                <div class="info-left">
                    <div class="move-left">
                        <c:if test="${!empty board.photo}">
                            <img src="${pageContext.request.contextPath}/upload/${board.photo}" width="50" height="50" class="my-photo">
                        </c:if>
                        <c:if test="${empty board.photo}">
                            <img src="${pageContext.request.contextPath}/images/face.png" width="50" height="50" class="my-photo">
                        </c:if>
                    </div>
                    <div class="move-right">
                        <span class="board-id">${board.id}</span><br>
                        <c:if test="${empty board.cboard_modifydate}">
                            <span class="board-date">${board.cboard_regdate}</span>
                        </c:if>
                        <c:if test="${!empty board.cboard_modifydate}">
                            <span class="board-date">${board.cboard_modifydate}</span>
                        </c:if>
                    </div>
                </div>
                <div class="info-right">
                    <img id="output_fav" data-num="${board.cboard_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
                </div>
            </div>
        </div>
    </div>
    <br><br>
    <div class="hr-container">
    <hr class="custom-hr" size="1" noshade="noshade" width="100%">
    </div>
    <br><br><br><br><br><br>

    <c:if test="${!empty board.cboard_photo1}">
        <div class="align-center">
            <img src="${pageContext.request.contextPath}/upload/${board.cboard_photo1}" class="detail-img" width="800" height="600">
        </div>
    </c:if>
    <br><br>
    <c:if test="${!empty board.cboard_photo2}">
        <div class="align-center">
            <img src="${pageContext.request.contextPath}/upload/${board.cboard_photo2}" class="detail-img" width="800" height="600">
        </div>
    </c:if>

    <div class="align-center">
        <p>${board.cboard_content}</p>
    </div>

    <br><br><br><br><br><br>

    <div class="detail-info" style="color: #828C94;">
        <div class="info-left">
    		좋아요  <span id="output_fcount"></span> &nbsp;
    		조회 ${board.cboard_hit}
		</div>
        <div class="info-right">
            <div class="detail-sub">
                <div>
                    <c:if test="${user_num == board.mem_num}">
                        <input type="button" value="수정" onclick="location.href='updateForm.do?cboard_num=${board.cboard_num}'" style="background: transparent; color: #828C94; border: none;">
						<input type="button" value="삭제" id="delete_btn" style="background: transparent; color: #828C94; border: none;">
                        <script type="text/javascript">
                            let delete_btn = document.getElementById('delete_btn');
                            // 이벤트 연결
                            delete_btn.onclick=function(){
                                let choice = confirm('삭제하겠습니까?');
                                if(choice){
                                    location.replace('delete.do?cboard_num=${board.cboard_num}');
                                }
                            };
                        </script>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

	<div class="hr-container">
    <hr class="custom-hr" size="1" noshade="noshade" width="100%">
    </div>
    
    <br><br>
    <div class="detail-info">
        <div class="info-left">
            <div class="move-left">
                <c:if test="${!empty board.photo}">
                    <img src="${pageContext.request.contextPath}/upload/${board.photo}" width="50" height="50" class="my-photo">
                </c:if>
                <c:if test="${empty board.photo}">
                    <img src="${pageContext.request.contextPath}/images/face.png" width="50" height="50" class="my-photo">
                </c:if>
            </div>
            <div class="move-right">
                <span class="board-id">${board.id}</span><br>
                <c:if test="${empty board.cboard_modifydate}">
                    <span class="board-date">${board.cboard_regdate}</span>
                </c:if>
                <c:if test="${!empty board.cboard_modifydate}">
                    <span class="board-date">${board.cboard_modifydate}</span>
                </c:if>
            </div>
        </div>
        <div class="info-right">
        </div>
    </div>
    <br><br>
    <div class="hr-container">
    <hr class="custom-hr" size="1" noshade="noshade" width="100%">
    </div>
    <br><br>
    <!-- 댓글 시작 -->
    <div id="reply_div">
    <span class="re-title">댓글 <span style="color: #35C5F0;">${replyCount}</span></span><br><br><br>
    <form id="re_form" style="display: flex; align-items: center;">
        <c:if test="${!empty board.photo}">
            <img src="${pageContext.request.contextPath}/upload/${board.photo}" width="50" height="50" class="my-photo">
        </c:if>
        <c:if test="${empty board.photo}">
            <img src="${pageContext.request.contextPath}/images/face.png" width="50" height="50" class="my-photo">
        </c:if>
        <div style="flex-grow: 1; margin-left: 10px; position: relative;">
            <input type="hidden" name="cboard_num" value="${board.cboard_num}" id="cboard_num">
            <div style="display: flex;">
                <textarea rows="3" name="re_content" id="re_content" class="rep-content"
                    <c:if test="${empty user_num}">disabled="disabled"</c:if>
                style="width: 100%; border-color: #C2C8CC;"
               	><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
				<div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); color: #C2C8CC;" id="comment-message">
    				칭찬과 격려의 댓글은 작성자에게 큰 힘이 됩니다:)
				</div>
               	<c:if test="${!empty user_num}">
                   <div id="re_second" class="align-right" style="margin-left: 10px; display: flex; flex-direction: column; align-items: center;">
    				<input type="submit" value="전송" style="background: transparent; color: #C2C8CC; border: none; margin-top: auto; margin-bottom: auto;">
					</div>
                </c:if>
            </div>
        </div>
    </form>
</div>
<br><br>




    <!-- 댓글 목록 출력 시작 -->
    <div id="output"></div>
    <div class="paging-button" style="display:none; color: #828C94; border: none;">
        <input type="button"  value="다음글 보기" style="background: transparent; color: #C2C8CC; border: none; margin-top: auto; margin-bottom: auto;">
    </div>
    <div id="loading" style="display:none;">
        <img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
    </div>
    <!-- 댓글 목록 출력 끝 -->

    <!-- 댓글 끝 -->
</div>
</body>
</html>
