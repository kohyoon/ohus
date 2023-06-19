<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- --%>
<script type="text/javascript">
	alert('회원정보 수정 완료!');
	location.href='detailUserForm.do?mem_num=${mem_num}'; <%-- {mem_num} request에 저장된 --%>
</script>