<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	location.href='${pageContext.request.contextPath}/chatting/chatting.do?chatroom_num=${chatroom.chatroom_num}&buyer_num=${chatroom.buyer_num}&seller_num=${chatroom.seller_num}';
</script>