<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.event.dao.EventDAO" %>
<%@ page import="kr.event.vo.EventVO" %>
<%@ page import="kr.event.vo.EventReplyVO" %>
<%@ page import="kr.event.vo.EventWinnerVO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이벤트 추첨 결과</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <h1>이벤트 추첨 결과</h1>
	<c:if test="${!empty win}">
	    <table>
	        <thead>
	            <tr>
	                <th>이벤트번호</th>
	                <th>댓글번호</th>
	                <th>회원번호</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:forEach var="win" items="${win}">
	                <tr>
	                    <td>${win.event_num}</td>
	                    <td>${win.re_num}</td>
	                    <td>${win.mem_num }</td>
	                </tr>
	            </c:forEach>
	        </tbody>
	    </table>
	</c:if>

    
    

	    <c:if test="${empty win}">
	        <p>추첨 결과가 없습니다.</p>
	    </c:if>
	</div>
	
	<div class="align-right">
		<input type="button" value="목록" onclick="location.href='endEventList.do'" class="btn" >
	</div>	
</div>	
</body>
</html>
<style>
.page-main{
   max-width: 1700px;
      margin: 0 auto;
      padding: 20px;
}
table {
  border: 1px #a39485 solid;
  font-size: .9em;
  box-shadow: 0 2px 5px rgba(0,0,0,.25);
  width: 70%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  margin : 0 auto;
}

th {
  text-align: center;
  background-color: #35c5f0;
  color: white;
  padding: 10px 0;
  border-bottom: 1px solid #fff; /* 상단에 흰색 선 추가 */
}

td,
th {
  padding: 1em .5em;
  vertical-align: middle;
  text-align: center;
  border-right: 1px solid white; /* 각 열에 세로 선 추가 */
}

td:last-child,
th:last-child {
  border-right: none; /* 마지막 열의 오른쪽 선 제거 */
}

td {
  border-bottom: 1px solid #a39485; /* 각 행에 가로 선 추가 */
  background: #fff;
}

</style>
