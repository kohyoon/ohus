$(function(){
	let currentPage;
	let count;
	let rowCount;
	
	//초기 데이터(목록) 호출
	selectList(1);
	
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		
		$.ajax({
			url:'reviewList.do',
			type:'post',
				  //key  :  value
			data:{pageNum:pageNum,item_num:$('#item_num').val()},
			dataType:'json',
			success:function(param){
				
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index, item){
					let output = '<br><div class="item">';
					output += '<div class="sub-item">';
					output += '<img src="../upload/' + item.review_photo + '" width="300" height="300" style="border-radius: 7px;"><br>';
					output += '<h4>' + item.id + '</h4>';
					output += '<span><big><font color="#35c5f0">★</font></big><small>' + item.item_score + '</small></span>';
					//날짜
					if(item.review_mdate){
						output += '<span class="modify-date"><small> | ' + item.review_mdate + '</small></span>';
					}else{
						output += '<span class="modify-date"><small> |' + item.review_regdate + '</small></span>';
					}
					output += '<p>' + item.review_content + '</p>';
					
					
					//수정 삭제 버튼
					//로그인한 회원번호와 작성자의 회원번호 일치여부 확인
					if(param.user_num == item.mem_num){
						//로그인한 회원번호와 작성자회원번호가 일치하는 경우
						output += ' <input type="button" data-renum="' + item.review_num + '" value="수정" class="modify-btn">';
					}
					
					output += '<hr size="0.5" noshade width="100%" color="#d9d9d9">';
					output += '</div>';
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				
				//page button 처리
				if(currentPage >= Math.ceil(count/rowCount)){
					//다음페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음페이지 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
});