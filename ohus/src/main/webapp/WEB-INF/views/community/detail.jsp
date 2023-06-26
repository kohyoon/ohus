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
    <style>
        .page-main {
            margin-left: 5cm;
            margin-right: 5cm;
        }
        .content-center {
            text-align: center;
        }
        .detail-info {
            display: flex;
            justify-content: space-between;
        }
        .info-left {
            display: flex;
            align-items: center;
        }
        .info-right {
            display: flex;
            align-items: center;
        }
        .my-photo {
            border-radius: 50%;
        }
        .photo-content {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }
        .move-left {
            margin-right: 0.15cm;
        }
        .move-right {
            position: relative;
            left: 0.15cm;
        }
        .board-date {
            color: #828C94;
        }
        .board-id {
            font-weight: bold;
        }
        .re-title {
            font-weight: bold;
        }
    </style>
</head>

<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <!-- 내용 시작 -->
    <div class="content-main">
        <div class="content-center">
            <h2>${board.cboard_title}</h2><br><br><br>
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
    <hr size="1" noshade="noshade" width="100%">
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

    <div class="detail-info">
        <div class="info-left">
            좋아요  <span id="output_fcount"></span> &nbsp;
            조회 ${board.cboard_hit}
        </div>

        <div class="info-right">
            <div class="detail-sub">
                <div>
                    <c:if test="${user_num == board.mem_num}">
                        <input type="button" value="수정" onclick="location.href='updateForm.do?cboard_num=${board.cboard_num}'">
                        <input type="button" value="삭제" id="delete_btn">
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

    <hr size="1" noshade="noshade" width="100%">
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
    <hr size="1" noshade="noshade" width="100%">
    <br><br>
    <!-- 댓글 시작 -->
    <div id="reply_div">
        <span class="re-title">댓글</span><br><br><br>
        <form id="re_form">
            <c:if test="${!empty board.photo}">
                <img src="${pageContext.request.contextPath}/upload/${board.photo}" width="50" height="50" class="my-photo">
            </c:if>
            <c:if test="${empty board.photo}">
                <img src="${pageContext.request.contextPath}/images/face.png" width="50" height="50" class="my-photo">
            </c:if>
            <input type="hidden" name="cboard_num" value="${board.cboard_num}" id="cboard_num">
            <textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content"
                <c:if test="${empty user_num}">disabled="disabled"</c:if>
            ><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>

            <c:if test="${!empty user_num}">
                <div id="re_second" class="align-right">
                    <input type="submit" value="전송">
                </div>
            </c:if>
        </form>
    </div>

    <!-- 댓글 목록 출력 시작 -->
    <div id="output"></div>
    <div class="paging-button" style="display:none;">
        <input type="button" value="다음글 보기">
    </div>
    <div id="loading" style="display:none;">
        <img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
    </div>
    <!-- 댓글 목록 출력 끝 -->

    <!-- 댓글 끝 -->
</div>
</body>
</html>
