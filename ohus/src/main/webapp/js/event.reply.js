$(function(){
	let currentPage; //현재 페이지 
	let count;
	let rowCount; //한 화면에 몇 개의 레코드를 보여줄지
	
	//댓글 목록 
	function selectList(pageNum){
		//pageNum을 저장해놓자
		currentPage = pageNum;
		//로딩 이미지 보여지게 하기
		$('#loading').show();
		
		$.ajax({
			
			url : 'listReply.do', 
			type : 'post', 
			data : {pageNum:pageNum, event_num:$('#event_num').val()}, //키:값, 뒤의 value값인 pageNum은 받아온 인자를 의미한다, event_num은 detail.jsp에서 hiddend으로 처리해놨던거
			dataType : 'json',
			//성공
			success : function(param){
				
				//먼저 로딩바 먼저 숨겨주기
				$('#loading').hide();
				
				//count 저장하기
				count = param.count;
				rowCount = param.rowCount;
				
				//새로운 댓글이 있으면 1페이지는 아예 새로운 글부터 업데이트 해줘야함
				
				if(pageNum==1){
					//처음 호출 시에는 해당 ID의 div 내부 내용물을 제거해준다
					/*
					신규 댓글을 가장 위에 올려줘야한다
					새로운 댓글을 추가하는 방식으로 하면 원래 있던 내용이 중복되어 출력되기 때문에 
					1페이지에서 만큼은 한 번 지워주고 추가를 해줘야한다!!
					*/
					$('#output').empty();
					}
					
				$(param.list).each(function(index,item){
					
					let output = '<div class="item">';
					output += '<h4>'+item.id+'</h4>';
					output += '<div class="sub-item">';
					output += '<p>' + item.re_content + '</p>';
					
					//[날짜]
					//수정일이 존재하는 경우
					if(item.re_modifydate){ //데이터가 있으면 true. javascript에서는 값만 넣어서 체크 가능
						//수정된 날짜가 있으면 수정된 날짜를 나타내줌 (최근 수정일)
						output +='<span class="modify-date">최근 수정일 : '+item.re_modifydate+'</span>'; 
					}
					//수정일이 없는 경우 - 등록일. sysdate가 된다
					else{
						output +='<span class="modify-date">등록일 : '+item.re_date+'</span>'; 
					}
					
					//수정 및 삭제 버튼
					//로그인 한 회원 번호와 댓글 작성자의 회원 번호가 일치하는지 체크하고, 수정 및 삭제가 가능하도록 해야한다
					if(param.user_num == item.mem_num){
						//data-renum : 수정 및 삭제 처리할 때 회원 번호를 알고 있으면 편리하기 때문에 넣어준 것, 반드시 버튼은 class로 명시해야 여러개도 접근이 가능
						output +=' <input type="button" value="수정" data-renum="'+item.re_num+'" class="modify-btn">'; //버튼 띄워주기 위해 앞에 공백 하나 
						output +=' <input type="button" value="삭제" data-renum="'+item.re_num+'" class="delete-btn">';
					}
					
					output += '<hr size="1" noshade="noshade" width="100%">';
					output += '</div>' //sub-item
					output += '</div>' //item
					
					//문서 객체에 추가
					$('#output').append(output);
					
				});
				
				//page button 처리
				//현재 페이지가 총 페이지 수/나중 번호 연산의 올림값보다 크거나 같은 경우?
				if(currentPage >= Math.ceil(count/rowCount)){ //ceil - 올림 처리
					//다음 페이지가 없음
					$(' .paging-button').hide(); //숨김 처리
				} else{
					//다음 페이지가 존재
					$(' .paging-button').show(); //보여지게끔 처리
				}
			}, 
			error : function(){
				//로딩바 먼저 멈춰주기
				$('#loading').hide(); //로딩바 숨기기
				alert('네트워크 오류');
			}
		});
	
	}
	
	//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭 시 데이터 추가) - 다음 댓글을 누르면 하단에 다음페이지 댓글이 추가로 보인다
	$(' .paging-button input').click(function(){
		selectList(currentPage + 1); //현재 페이지에서 1 증가하기
		});
	
	//--------------------------------------
	//1. 댓글 등록
	$('#re_form').submit(function(event){ //event를 변수로 받기 - 기본이벤트 제거하기 위함
		//기본 이벤트 제거 --submit 버튼을 누르면 넘어가기 때문에 한 화면에서 처리되도록 기본 이벤트인 submit을 제거해주는 것
		event.preventDefault();
		
		//앞 뒤 공백을 모두 제거했는데도 true : 공백을 입력함
		if($('#re_content').val().trim()==''){
			alert('댓글 내용을 입력하세요!');
			$('#re_content').val('').focus(); //지워주고 포커스 처리
			
			return false;
		}
		
		//form 이하의 태그에서 입력한 데이터를(댓글) 한 번에 모두 읽어오는 방법
		let form_data = $(this).serialize(); //이벤트가 발생한 객체(this)를 받아서 ajax 처리하기
		
		//서버와 통신
		$.ajax({
			url : 'writeReply.do',
			type : 'post',
			data : form_data, //위에서 읽어온 데이터를 넣어줌
			dataType : 'json', 
			success : function(param){
				//success로 들어오는 조건
				//1. 로그아웃 되어있거나
				if(param.result=='logout'){
					alert('로그인 후 작성할 수 있습니다');
				}
				//2. success로 정상 실행되었거나
				else if(param.result='success'){
					
					//폼 초기화 --아래서 정의한 함수
					initForm();
					
					//댓글 작성이 성공하면 새로 작성한 댓글을 포함해 
					//첫 번째 페이지의 게시글을 다시 호출해준다
					selectList(1);
					
				} else{
					alert('댓글 등록 오류 발생');
				}
			}, 
			error : function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	//--------------------------------------
	//1. 댓글 작성 폼 초기화
	function initForm(){
		//textarea에 있는 내용을 지워주고 글자수 체크
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300'); //? first에서 띄워주기 - 후손선택자, 글자수 체크는 나중에 처리함 여기서 되는거 아님
	}
	
	//--------------------------------------
	//2. textarea의 댓글 글자수 체크 -- document.on으로 연결!
	$(document).on('keyup', 'textarea', function(){ //document?
		//입력한 글자수 구하기
		let inputLength = $(this).val().length;
		
		//조건체크 : 300자를 넘는 글자는 잘라내기
		if(inputLength>300){
			$(this).val($(this).val().substring(0,300)); //substring(begin_index, end_index)
		}
		//300자 이하인 경우 - 300에서 글자를 차감해줌
		else{
			let remain = 300 - inputLength;
			remain += '/300'; //234/300과 같이 입력 가능한 글자수와 최대 글자수인 300자가 함께 출력된다
			
			if($(this).attr('id')=='re_content'){
				//등록폼 글자수
				$('#re_first .letter-count').text(remain);
			}
			else{
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
	
	//--------------------------------------
	//[수정]
	//1. 댓글 수정 버튼 클릭 시 수정폼 노출
	$(document).on('click', '.modify-btn', function(){ //미래 태그이므로 document 연결
		
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		
		//댓글 내용
		//javascript에서 모든 태그 중 <br>로 된 태그를 \n으로 처리
		//g : 지정 문자열 모두, i : 대소문자 무시 
		//p태그로 감싸진 댓글 - p태그의 부모는 div인데 div에서 find(p)를 해서 p태그를 찾는 것
		let content = $(this).parent().find('p').html().replace(/<br>/gi, '\n'); //버튼의 부모에 접근해서 부모의 메서드인 find 사용해서 p를 찾음(jsp 코드 봐보기)
		
		//댓글 수정폼 UI -- ajax에서 직접 태그 만들기
		let modifyUI = '<form id="mre_form">';
		//미리보기 - hidden이라 보이진 않음
		modifyUI += '<input type="hidden" name="re_num" id="mre_num" value="'+re_num+'">';//name이 서버로 전송되는 것임
		modifyUI += '<textarea rows="3" cols="50" name="re_content" id="mre_content" class="rep-content">'+content+'</textarea>'; //문자열에서 작성 중이므로 문자열 분리를 해줘야함
		modifyUI += '<div id="mre_first"><span class="letter-count">300/300</span></div>';
		modifyUI += '<div id="mre_second" class="align-right">';
		modifyUI += ' <input type="submit" value="수정">';
		modifyUI += ' <input type="button" value="취소" class="re-reset">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
		
		//수정 중인 댓글이 있을 경우, 다른 댓글의 수정 버튼을 누르면 그 댓글 수정되기 - 하나의 수정만 가능하도록!
		/*
		이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면 숨겨져있는 sub-item을
		환원시키고 수정폼을 초기화함
		*/
		initModifyForm(); //초기화 함수를 호출해주면 됨!
		
		//데이터가 표시되어 있는 div를 감추기
		$(this).parent().hide(); //sub-item div 감추기
		
		//수정폼을 수정하고자 하는 데이터가 있는 div에 노출시키기
		$(this).parents(' .item').append(modifyUI) //이벤트가 발생한 부모들 중에거 클래스가 item에 접근, 기존의 것은 지워지고 수정 UI가 보여짐
		
		//입력한 글자수 셋팅
		let inputLength = $('#mre_content').val().length;
		
		//글자수 연산!
		let remain = 300 - inputLength;
		
		remain += '/300'; //뒤에 300 붙여주기
		
		//글자수를 문서 객체에 반영해주기
		$('#mre_first .letter-count').text(remain);
		
		
		
	});
	
	//--------------------------------------
	//1. 수정폼에서 취소 버튼 클릭 시 수정폼 초기화
	//취소 버튼은 동적으로 만들어진 태그(미래태그)
	$(document).on('click', '.re-reset', function(){
		
		initModifyForm(); //아래 함수 호출
		
	});
	
	//--------------------------------------
	//1. 댓글 수정폼 초기화 - 댓글을 하나 수정할 때 다른 댓글은 그대로이게
	//취소를 누르면 해당 함수가 호출
	function initModifyForm(){
		$('.sub-item').show(); //점 띄어쓰기?
		$('#mre_form').remove();
	}
	//--------------------------------------
	//댓글 수정 - 이벤트 연결하기 --미래이 태그이기 때문에 document 처리를 해줘야한다
	/* submit하면 화면이 바뀌는데 ajax는 같은 화면에서 처리가 되는 것 */
	$(document).on('submit', '#mre_form', function(event){
		//기본 이벤트인 submit 제거하기
		event.preventDefault();
		
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#mre_content').val('').focus();
			
			return false;
		}
		
		//폼에 입력한 데이터 반환 모두 읽어오기 - serialize
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url : 'updateReply.do',
			type : 'post',  
			data : form_data,  //위에 변수 생성을 했으므로 작은 따옴표 없이 바로 쓰기
			dataType : 'json',
			success : function(param){
				//1. 로그인이 안 된 경우
				if(param.result=='logout'){
					alert('로그인 후 수정 가능');
				}
				//2. 정상 처리(로그인+회원번호=작성자번호)
				else if(param.result == 'success'){
					//수정된 데이터를 가져오기
					//수정폼의 부모에서 p태그를 찾음(hide 처리된 p태그를 가져옴), html - 내용 접근, #mre_content를 통해 textarea에 접근
					//괄호 어디?
					//댓글에서 html 태그를 썼을 때 적용되지 않도록 모든 <>태그를 문자로 변경해주고 엔터 처리도 해주기 
					$('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\n/g, '<br>')); //꺽쇠가 있는 모든 걸 &lt;, &gt;(꺽쇠), 변경된 문자열을 반환, 태그를 무력화시키는 것

					
					//날짜 --util에 날짜 처리 넣어줌
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초 미만'); //수정을 하면 최근 수정일 : 5초 미만으로 표시되도록 처리, 새로고침하면 시간이 카운팅 됨(dao-댓글목록)
					
					//수정폼 삭제 및 초기화
					initModifyForm();
					
					alert('수정 완료!');
				}
				//3. 로그인o 하지만 작성자 일치x
				else if(param.result == 'wrongAccess'){
					alert('타인의 글을 수정할 수 없습니다');
				}
				else {
					
					alert('댓글 수정 오류');
				}
			}, 
			error : function(){
				alert('네트워크 오류');
			}
		});
		
	});
	
	//--------------------------------------
	//댓글 삭제
	//document로 처리해야함 - 미래의 태그?
	$(document).on('click', '.delete-btn', function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		
		$.ajax({
			url : 'deleteReply.do', 
			type : 'post',
			data : {re_num:re_num}, 
			dataType : 'json', 
			success : function(param){
				//DeleteReplyAction에서 보면 세가지의 경우가 있음
				
				//1. 로그아웃 된 경우
				if(param.result=='logout'){
					alert('로그인 후 삭제하세요');
				}
				//2. 정상 삭제된 경우(로그인한 회원=댓글 작성자)
				else if(param.result=='success'){
					alert('삭제 완료!');
					//삭제한 후 목록을 보여줘야함
					selectList(1);
				}
				//3. 로그인은 되어있는데 작성자는 아닌 경우
				else if(param.result=='wrongAccess'){
					alert('타인의 글을 삭제할 수 없습니다');
				}
				//오타가 있는 경우
				else{
					alert('댓글 삭제 오류 발생');
				}
			}, 
			error : function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	//--------------------------------------
	//1. 초기 데이터(댓글 목록) 호출 --함수 호출
	selectList(1); //1page만 보여주는 것
});