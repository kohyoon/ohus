<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>글 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#update_form').submit(function () {
                if ($('#title').val().trim() === '') {
                    alert('제목을 입력하세요!');
                    $('#title').val('').focus();
                    return false;
                }
                if ($('#content').val().trim() === '') {
                    alert('내용을 입력하세요!');
                    $('#content').val('').focus();
                    return false;
                }
            });
        });
    </script>
    
    
</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <!-- 내용 시작 -->
    <div class="content-main">
        <h2>게시글 수정</h2>
        <form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
            <input type="hidden" name="cboard_num" value="${board.cboard_num}" />
            <ul>
                <li>
                    <label for="title">제목</label>
                    <input type="text" name="cboard_title" value="${board.cboard_title}" id="title" maxlength="50" />
                </li>
                <li>
                    <label for="content">내용</label>
                    <textarea rows="5" cols="30" name="cboard_content" id="content" maxlength="50">${board.cboard_content}</textarea>
                </li>
                <li>
                    <label for="filename">사진1</label>
                    <input type="file" name="cboard_photo1" id="filename" accept="image/gif,image/png,image/jpeg" />
                    <c:if test="${not empty board.cboard_photo1}">
                        <div id="file_detail">
                            (${board.cboard_photo1})파일이 등록되어 있습니다.
                            <input type="button" value="파일삭제" id="file_del1" />
                        </div>
                        <script type="text/javascript">
                            $(function () {
                                $('#file_del1').click(function () {
                                    let choice = confirm('삭제하시겠습니까?');
                                    if (choice) {
                                        $.ajax({
                                            url: 'deleteFile.do',
                                            type: 'post',
                                            data: { board_num: ${board.cboard_num} },
                                            dataType: 'json',
                                            success: function (param) {
                                                if (param.result === 'logout') {
                                                    alert('로그인 후 사용하세요');
                                                } else if (param.result === 'success') {
                                                    $('#file_detail').hide();
                                                } else if (param.result === 'wrongAccess') {
                                                    alert('잘못된 접속입니다.');
                                                } else {
                                                    alert('파일 삭제 오류 발생');
                                                }
                                            },
                                            error: function () {
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
                    <label for="filename">사진2</label>
                    <input type="file" name="cboard_photo2" id="filename" accept="image/gif,image/png,image/jpeg" />
                    <c:if test="${not empty board.cboard_photo2}">
                        <div id="file_detail">
                            (${board.cboard_photo2})파일이 등록되어 있습니다.
                            <input type="button" value="파일삭제" id="file_del2" />
                        </div>
                        <script type="text/javascript">
                            $(function () {
                                $('#file_del2').click(function () {
                                    let choice = confirm('삭제하시겠습니까?');
                                    if (choice) {
                                        $.ajax({
                                            url: 'deleteFile.do',
                                            type: 'post',
                                            data: { board_num: ${board.cboard_num} },
                                            dataType: 'json',
                                            success: function (param) {
                                                if (param.result === 'logout') {
                                                    alert('로그인 후 사용하세요');
                                                } else if (param.result === 'success') {
                                                    $('#file_detail').hide();
                                                } else if (param.result === 'wrongAccess') {
                                                    alert('잘못된 접속입니다.');
                                                } else {
                                                    alert('파일 삭제 오류 발생');
                                                }
                                            },
                                            error: function () {
                                                alert('네트워크 오류 발생');
                                            }
                                        });
                                    }
                                });
                            });
                        </script>
                    </c:if>
                </li>
            </ul>
            <div class="align-center">
                <input type="submit" value="수정" />
                <input type="button" value="글상세" onclick="location.href='detail.do?cboard_num=${board.cboard_num}'" />
            </div>
        </form>
    </div>
    <!-- 내용 끝 -->
</div>
</body>
</html>
