<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이벤트 추첨</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container">
    <h1>이벤트 추첨</h1>
    
    <<c:if test="${!empty plz}">
    <!-- 이미 추첨이 실행된 경우, 추첨 결과를 표시 -->
    <table>
        <!-- 테이블 헤더 -->
        <tbody>
            <c:forEach var="plz" items="${plz}">
                <!-- 테이블 데이터 -->
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty plz}">
    <!-- 추첨이 실행되지 않은 경우, 추첨 시작 버튼을 표시 -->
    <input type="button" value="추첨 시작" onclick="location.href='playEvent.do?event_num=${event.event_num}'">
</c:if>

</div>
</body>
</html>
