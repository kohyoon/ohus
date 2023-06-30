<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<style type="text/css">
	footer {
  position: relative;
  bottom: 0;
   padding-top: 10px;
  padding-bottom: 20px;
  position : absolute;
  transform : translateY(150%);
  background-color: #f2f2f2;
}
.footer-upper{
	padding-top: 20px;
}
	
</style>
		<!-- 하단 내용 시작 -->
		<footer class="footer">
			<div class="footer-upper">
				<div>
					<h4>
						<a href="${pageContext.request.contextPath}/inquiry/customerCenter.do">고객센터</a>
					</h4>
					<span class="footer-number">☎1111-1234 | 평일
						09:00 ~ 18:00 (주말 & 공휴일 제외)</span>
				</div>
				
			</div>
			
			<div>
				<p>
					<!-- <img> -->
					로고
				</p>
				<p>
					<strong>내일의 집</strong>
					<br>
					서울특별시 강남구 테헤란로 132 8층
					<br>
					대표이사 : 뫄뫄뫄
					<br>
					사업자등록번호 : 111-22-33333 사업자정보확인
					<br>
					통신판매업신고 : 강남 12345호 FAX : 02-1111-2222
				</p>
				<p>
					<strong>고객센터</strong>
					<br>
					Tel : 1111-2222 (평일 09:00 ~ 18:00)
					<br>
					서울특별시 강남구 테헤란로 132 8층
					<br>
					FAX: 02-1234-5678 | Mail : ohuse@ohuse.co.kr
					<br>
				</p>
			</div>
		</footer>
		<!-- 하단 내용 끝 -->