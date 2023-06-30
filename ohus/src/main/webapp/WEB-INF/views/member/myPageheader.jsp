<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
</head>

<style>
/* 네비게이션 하단 영역 ---------------------------------------------------*/
.header-lower {
  border-bottom: 1px solid #ededed;
  overflow-x: auto;
  overflow-y: hidden;
}

.header-lower .inner {
  display: flex;
  justify-content: center;
  align-self: center;
  height: 40px;
}

.header-lower .inner nav {
  display: flex;
  flex-flow: row nowrap;
  justify-content: flex-start;
  align-items: center;
  color: #424242;
}

.header-lower__item {
  font-size: 0.8rem;
  font-weight: 700;
  color: #424242;
  cursor: pointer;
  padding: 11px 10px;
  flex-shrink: 0;
}

.header-lower__item:hover,
.header-lower__item.active {
  color: #35c5f0;
}

.header-lower__item.active {
  border-bottom: 2px solid #35c5f0;
}

.header-lower .inner div {
  display: none;
}



</style>
<body>
    <header>
        <div class="header-lower__nav">
            <div class="header-lower">
                <div class="inner">
                    <nav>
                        <a class="header-lower__item" href="myPage.do">프로필</a>
                        <a class="header-lower__item" href="myPage1.do">나의 쇼핑</a>
                        <a class="header-lower__item" href="myPage2.do">나의 참여</a>
                        <a class="header-lower__item" href="myPage3.do">설정</a>
                    </nav>
                </div>
            </div>
        </div>
    </header>
</body>
<script type="text/javascript">
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
</html>
