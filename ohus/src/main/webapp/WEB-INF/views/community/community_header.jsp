<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 상품 네비게이션 처리 시작 -->
<script>
    function setActiveLink() {
        var links = document.getElementsByClassName("header-lower__item");
        var currentUrl = window.location.href;
        for (var i = 0; i < links.length; i++) {
            var linkUrl = links[i].href;
            if (currentUrl.includes(linkUrl)) {
                links[i].classList.add("active");
            } else {
                links[i].classList.remove("active");
            }
        }
    }
    window.addEventListener("DOMContentLoaded", setActiveLink);
</script>
<header>
    <div class="header-lowwer__nav">
        <div class="header-lower">
            <div class="inner">
                <nav>
                    <a href="${pageContext.request.contextPath}/community/list.do?" style="margin-left: 1cm; font-size: 1.0rem; font-weight: 700; color: #424242; cursor: pointer; padding: 11px 10px; flex-shrink: 0;">전체</a>
                    <a class="header-lower__item" href="${pageContext.request.contextPath}/community/list.do?cboard_category=0" style="margin-left: 1cm;">일상</a>
                    <a class="header-lower__item" href="${pageContext.request.contextPath}/community/list.do?cboard_category=1" style="margin-left: 1cm;">취미</a>
                    <a class="header-lower__item" href="${pageContext.request.contextPath}/community/list.do?cboard_category=2" style="margin-left: 1cm;">자랑</a>
                    <a class="header-lower__item" href="${pageContext.request.contextPath}/community/list.do?cboard_category=3" style="margin-left: 1cm;">추천</a>
                </nav>
            </div>
        </div>
    </div>
    <p>
</header>
<!-- 상품 네비게이션 처리 끝 -->