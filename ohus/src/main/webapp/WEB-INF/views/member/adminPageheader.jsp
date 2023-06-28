<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
</head>
<body>
    <header>
        <div class="header-lower__nav">
            <div class="header-lower">
                <div class="inner">
                    <nav>
                        <a class="header-lower__item" href="adminPage.do">회원관리</a>
                        <a class="header-lower__item" href="adminPageItem.do">상품관리</a>
                        <a class="header-lower__item" href="adminPageOrder.do">배송관리</a>
                        <a class="header-lower__item" href="adminPageMarket.do">상추마켓관리</a>
                        <a class="header-lower__item" href="adminPageEvent.do">이벤트관리</a>
                    </nav>
                </div>
            </div>
        </div>
    </header>
</body>
</html>
