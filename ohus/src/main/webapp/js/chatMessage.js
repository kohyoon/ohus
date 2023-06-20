$(function(){
	
	// 채팅 목록
	function messageList(chatroom_num){
		$.ajax({
			url: 'chatMessageList.do',
			type: 'get',
			data:{
				chatroom_num:chatroom_num
			},
			dataType:'json',
			success:function(param){
				$('#output').empty();
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<h4>' + item.mem_num + '</h4>';
					output += '<div class="sub-item">';
					output += '<p>' + item.message + '</p>';
					
					$('#output').append(output);
				});
			},
			error:function(){
				alert('네트워크 통신 오류');
			}
		});
	}
	
	// 메시지 등록
	$('#chat_form').submit(function(event){
		event.preventDefault();
		
		if($('#message').val().trim() == ''){
			alert('내용을 입력하세요.');
			$('#message').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		
		$.ajax({
			url: 'writeChatMessage.do',
			type: 'post',
			data: form_data,
			dataType: 'json',
			success: function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.')
				}else if(param.result == 'success'){
					// 폼 초기화
					initForm();
					// 댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출함
					messageList(param.chatroom_num);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error: function(){
				alert('네트워크 통신 오류');
			}
			
		});
	});
	
	function initForm(){
		$('textarea').val('');
	}
	setInterval(function(){
		messageList(chatroom_num)
	},1000);
	
});