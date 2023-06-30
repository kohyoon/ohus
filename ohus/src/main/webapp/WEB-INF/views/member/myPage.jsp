<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기본 마이페이지 - 프로필</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lyj/table.css"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lss/list.css">
</head>
<style>
/*첫 번째 페이지
-------------*/
	.table-container {
		margin-left: 500px; /* 프로필 사진의 너비와 동일한 값으로 설정 */
		margin-bottom: 60px;	
		width : 120%;
	}


    .table-container h3{
    	margin-right: 350px;
    }
    .table-container th,
    .table-container td {
        padding: 10px;
        border: 1px solid #ddd;
    }
  
  .main {
		display: flex;
		align-items: flex-start; /* 상단 정렬로 변경 */
		justify-content: center;
		background-image: url(images/back.jpg);
		background-position: center;
		background-size: cover;
		margin-right: 70px;
		margin-left: 300px;
		margin-top: 40px;
	}
	.tables-container{
    justify-content: center;
    flex-direction: column;
    align-items: flex-start;
    width: 100%;
    margin-right: 1300px;
    align-items: center;
    }
    
    
.profile-card{
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 190px;
  width: 40%;
  border-radius: 25px;
  padding: 20px;
  border: 1px solid #ffffff40;
  box-shadow: 0 5px 20px rgba(0,0,0,0.4);
  margin-right: 1500px;
  position: absolute;
  margin-left : 90px;
}
.image{
  position: relative;
  height: 100px;
  width: 100px;
}
.image .profile-pic{
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
  box-shadow: 0 5px 20px rgba(0,0,0,0.4);
}
.data{
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 18px;
}

.row{
  display: flex;
  align-items: center;
  margin-top: 2px;
}
.row .info{
  text-align: center;
  padding: 0 13px;
}
.buttons{
  display: flex;
  align-items: center;
  margin-top: 15px;
}
.buttons .btn{
  color: #fff;
  width : 70%;
  text-decoration: none;
  margin: 0 20px;
  padding: 5px 15px;
  border-radius: 17px;
  font-size: 13px;
  white-space: nowrap;
  background: linear-gradient(to left, #33ccff 0%, #ff99cc 100%);
}

.buttons .btn:hover{
  box-shadow: inset 0 5px 20px rgba(0,0,0,0.4);
}
.buttons .btn2{
	text-align: center;
	margin-left: 60px;
}
.no-list{
	text-align: center;
	margin-left: 260px;
	color : gray;
}
  
  

</style>
<script type="text/javascript">
	$(function(){
		//수정버튼 이벤트 연결
		$('#photo_btn').click(function(){
			$('#photo_choice').show();
			$(this).hide();//수정버튼 감추기
		});//end of click - 수정버튼
		
		//이미지 미리 보기
		//처음 화면에 보여지는 이미지 저장
		let photo_path = $('.my-photo').attr('src');
		//선택한 이미지
		let my_photo;
		$('#photo').change(function(){
			my_photo = this.files[0];
			if(!my_photo){
				$('.my-photo').attr('src', photo_path);
				return;
			}
			//파일 용량 체크
			if(my_photo.size > 1024*1024){
				alert(Math.round(my_photo.size/1024) 
				  + 'kbytes(1024kbytes까지만 업로드 가능)');
				$('.my-photo').attr('src',photo_path);
				$(this).val('');//선택한 파일 정보 지우기
				return;
			}
			
			let reader = new FileReader();
			reader.readAsDataURL(my_photo);
			
			reader.onload=function(){$('.my-photo').attr('src', reader.result);
			};
		});//end of change		
		
		//전송버튼 이벤트 연결
		$('#photo_submit').click(function(){
			if($('#photo').val()==''){
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}
			
			//서버에 파일(사진) 전송
			let form_data = new FormData();
			form_data.append('photo',my_photo);
			$.ajax({
				url:'updateMyPhoto.do',
				type:'post',
				data:form_data,
				dataType:'json',
				contentType:false,//데이터 객체를 문자열로 바꿀지에 대한 설정,true면 일반 문자
				processData:false,//해당 타입을 true로 하면 일반 text로 구분
				enctype:'multipart/form-data',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						alert('프로필 사진이 수정되었습니다.');
						//업로드한 이미지로 초기 이미지 대체 -- 이미지를 변경하려다가 취소했을 때 face.jpg가 아닌
						//그 이후에 변경한 이미지로 남아있어야되기 때문에 중간에 바꾼 이미지를 초기 이미지로 인식하게 처리해줘야한다
						photo_path = $('.my-photo').attr('src');
						$('#photo').val('');
						$('#photo_choice').hide();
						$('#photo_btn').show();
					}else{
						alert('파일 전송 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
			
		});//end of click - 전송버튼
		
		//취소버튼 이벤트 연결
		$('#photo_reset').click(function(){
			//초기 이미지 표시
			$('.my-photo').attr('src',photo_path);
			$('#photo').val('');
			$('#photo_choice').hide();
			$('#photo_btn').show(); //수정버튼 노출
		});//end of click - 취소버튼
		
	});
</script>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/member/myPageheader.jsp"/>

<div class="page-main">
<div class="content-main">
<section class="main">
	 <!-- 프로필 사진 시작 -->
  <div class="profile-card">
    <div class="image">
      <c:if test="${empty member.photo}">
		<img src="${pageContext.request.contextPath}/images/face.png"
		   width="200" height="200" class="profile-pic">
		</c:if>
		<c:if test="${!empty member.photo}">
		<img src="${pageContext.request.contextPath}/upload/${member.photo}"
		   width="200" height="200" class="profile-pic">
		</c:if>
    </div>
    <div class="data"> <!-- 자기소개 -->
      <h2>${member.name}</h2>
    </div>
    <div class="row">
      <div class="info">
        <h3><a href='${pageContext.request.contextPath}/qna/myQnaList.do'><img src="${pageContext.request.contextPath}/images/love.png" width="25"></a></h3>
      </div>
      <div class="info">
        <h3><a href='myPage4.do'><img src="${pageContext.request.contextPath}/images/dialog.png" width="40"></a></h3>
      </div>
    </div>
    <div class="buttons">
	    <input type="button" value="수정" id="photo_btn" class="btn">
		<div id="photo_choice" style="display:none;">
			<input type="file" id="photo" accept="image/gif,image/png,image/jpeg" class="btn2">
			<br>
			<input type="button" value="전송" id="photo_submit" class="btn2">
			<br>
			<input type="button" value="취소" id="photo_reset" class="btn2">                        
		</div>
     </div>
  </div>


<div class="tables-container">
	<div class="table-container">
	    <h3>좋아요 누른 게시물 목록</h3>
	    <c:if test="${!empty commList}">
	    <table>
	        <tr>
	            <th>제목</th>
	            <th>작성자</th>
	            <th>등록일</th>
	        </tr>
	        <c:forEach var="cboard" items="${commList}">
	            <tr> 
	                <td><a href="${pageContext.request.contextPath}/community/detail.do?cboard_num=${cboard.cboard_num}">${cboard.cboard_title}</a></td>
	                <td>${cboard.id}</td>
	                <td>${cboard.cboard_regdate}</td>
	            </tr>
	        </c:forEach>
	    </table>
	    </c:if>
	    
	    <c:if test="${empty commList }">
	    	<span class="no-list">좋아요 누른 게시글이 없네요!</span>
	    </c:if>
	</div>

	<div class="table-container">
	    <h3>내가 올린 상추글</h3>
	     <c:if test="${!empty marketList }">
	    <table>
	        <tr>
	            <th>제목</th>
	            <th>등록일</th>
	            <th>상태</th>
	        </tr>
	        <c:forEach var="market" items="${marketList}">
	            <tr> 
	                <td><a href="${pageContext.request.contextPath}/market/detail.do?market_num=${market.market_num}">${market.market_title}</a></td>
	                <td>${market.market_regdate}</td>
	                <td>
	                    <c:if test="${market.market_status == 0}">판매중</c:if>
	                    <c:if test="${market.market_status != 0}">거래완료</c:if>
	                </td>
	            </tr>
	        </c:forEach>
	    </table>
	    </c:if>
	    
	    <c:if test="${empty marketList }">
	    	<span class="no-list">상추글을 올려보세요!</span>
	    </c:if>
	    
	    
	</div>

	<div class="table-container">
	    <h3>내가 작성한 문의내용</h3>
	    <c:if test="${!empty inquiryList }">
	    <table>
	        <tr>
	            <th>제목</th>
	            <th>등록일</th>
	            <th>상태</th>
	        </tr>
	        <c:forEach var="inquiry" items="${inquiryList}">
	            <tr> 
	                <td><a href="${pageContext.request.contextPath}/inquiry/detailInquiry.do?inq_num=${inquiry.inq_num}">${inquiry.inq_title}</a></td>
	                <td>${inquiry.inq_regdate}</td>
	                <td>
	                    <c:if test="${inquiry.inq_status == 1}">처리전</c:if>
	                    <c:if test="${inquiry.inq_status != 1}">처리완료</c:if>
	                </td>
	            </tr>
	        </c:forEach>
	    </table>
	    </c:if>
	    
	    <c:if test="${empty inquiryList }">
	    	<span class="no-list">문의한 내역이 없습니다</span>
	    </c:if>
	    
	    
	</div>
</section>
</div>
</div>				
</body>
</html>



