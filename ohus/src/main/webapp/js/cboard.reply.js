$(function(){
	let currentPage;
	let count;
	let rowCount;
	
// 댓글 목록
function selectList(pageNum) {
  currentPage = pageNum;
  // 로딩 이미지 노출
  $('#loading').show();

  $.ajax({
    url: 'listReply.do',
    type: 'post',
    data: { pageNum: pageNum, cboard_num: $('#cboard_num').val() },
    dataType: 'json',
    success: function (param) {
      // 로딩 이미지 감추기
      $('#loading').hide();
      count = param.count;
      rowCount = param.rowCount;

      if (pageNum == 1) {
        // 처음 호출시는 해당 ID의 div
        // 내부 내용물을 제거
        $('#output').empty();
      }

      $(param.list).each(function (index, item) {
        let output = '<div class="item">';
        output += '<h4>' + item.id + '</h4>';
        output += '<div class="sub-item">';
        output += '<p>' + item.re_content + '</p>';

        // 날짜
        if (item.re_modifydate) {
          output += '<span class="modify-date">' + item.re_modifydate + '</span>';
        } else {
          output += '<span class="modify-date">' + item.re_date + '</span>';
        }
        // 수정, 삭제, 신고 버튼
        // 로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
        if (param.user_num == item.mem_num) {
          // 로그인한 회원번호와 작성자 회원번호 일치
          output += ' <input type="button" data-renum="' + item.re_num + '" value="수정" class="modify-btn">';
          output += ' <input type="button" data-renum="' + item.re_num + '" value="삭제" class="delete-btn">';
          output += ' <input type="button" data-renum="' + item.re_num + '" value="신고" class="report-btn">';
        }

        output += '<hr size="1" noshade width="100%">';
        output += '</div>';
        output += '</div>';

        // 문서 객체에 추가
        $('#output').append(output);
      });

      // page button 처리                
      if (currentPage >= Math.ceil(count / rowCount)) {
        // 다음 페이지가 없음
        $('.paging-button').hide();
      } else {
        // 다음 페이지가 존재
        $('.paging-button').show();
      }

      // 신고하기 버튼 클릭 이벤트 추가
      $('.report-btn').click(function () {
        var renum = $(this).data('renum');
        // 신고하기 동작 수행
       	openReportPopup(renum);
      });
    },
    error: function () {
      // 로딩 이미지 감추기
      $('#loading').hide();
      alert('네트워크 오류 발생');
    }
  });
}

// 팝업 열기
function openReportPopup(renum) {
  // 팝업 창 크기 설정
  var popupWidth = 300;
  var popupHeight = 300;
  var leftPosition = (window.innerWidth - popupWidth) / 2;
  var topPosition = (window.innerHeight - popupHeight) / 2;

  // 팝업 창 생성
  var reportPopup = window.open('', '신고하기', 'width=' + popupWidth + ', height=' + popupHeight + ', left=' + leftPosition + ', top=' + topPosition);

  // 팝업 창 내용 추가
  var reportContent = '<h3>신고하기</h3>';
  reportContent += '<label><input type="radio" name="reportCategory" value="1">주제와 맞지 않음</label><br>';
  reportContent += '<label><input type="radio" name="reportCategory" value="2">광고</label><br>';
  reportContent += '<label><input type="radio" name="reportCategory" value="3">저작권 침해</label><br>';
  reportContent += '<label><input type="radio" name="reportCategory" value="4">욕설/비방</label><br>';
  reportContent += '<label><input type="radio" name="reportCategory" value="5">개인정보 노출</label><br>';
  reportContent += '<label><input type="radio" name="reportCategory" value="6">기타</label><br>';
  reportContent += '<button id="submitReport">신고</button>';

  reportPopup.document.write(reportContent);

  // 팝업 창 내부 스타일 추가
  reportPopup.document.write('<style>body { text-align: center; padding: 20px; }</style>');

  // 팝업 창에서 신고 버튼 클릭 이벤트 처리
  reportPopup.document.getElementById('submitReport').onclick = function () {
    // 선택된 카테고리 가져오기
    var selectedCategory = reportPopup.document.querySelector('input[name="reportCategory"]:checked');
    if (selectedCategory) {
      var categoryValue = selectedCategory.value;

      // AJAX 호출을 통해 서버와 통신
      $.ajax({
        url: 'reportReply.do',
        type: 'post',
        data: {
          dec_category: categoryValue,
          re_num: renum
        },
        dataType: 'json',
        success: function (response) {
          if (response.result === 'success') {
            alert('신고가 접수되었습니다.');
          } else if (response.result === 'logout') {
            alert('로그인이 필요합니다.');
          } else {
            alert('신고 처리 중 오류가 발생하였습니다.');
          }
        },
        error: function () {
          alert('네트워크 오류 발생');
        }
      });
    } else {
      // 카테고리를 선택하지 않았을 때 처리 로직 추가
      alert('신고 카테고리를 선택해주세요.');
    }

    // 팝업 창 닫기
    reportPopup.close();
  };
}


	
	//페이지 처리 이벤트 연결
	//(다음 댓글 보기 버튼 클릭시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	//댓글 등록
	$('#re_form').submit(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#re_content').val('').focus();
			return false;
		}
		
		//form 이하의 태그에서 입력한 데이터를 모두 읽어옴
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'writeReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.')
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을
					//포함해서 첫번째 페이지의 게시글을 
					//다시 호출함
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		
	});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	
	//textarea에 내용 입력시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함
		let inputLength = $(this).val().length;
		
		if(inputLength>300){//300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		}else{//300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id')=='re_content'){
				//등록폼 글자수
				$('#re_first .letter-count').text(remain);
			}else{
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
	
	//댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click','.modify-btn',
	                       function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		//댓글 내용
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
		                                         //g:지정문자열 모두, i:대소문자 무시
		//댓글 수정폼 UI
		let modifyUI = '<form id="mre_form">';
		modifyUI += '<input type="hidden" name="re_num" id="mre_num" value="' + re_num + '">';
		modifyUI += '<textarea rows="3" cols="50" name="re_content" id="mre_content" class="rep-content">' + content + '</textarea>';
		modifyUI += '<div id="mre_first"><span class="letter-count">300/300</span></div>';
		modifyUI += '<div id="mre_second" class="align-right">';
		modifyUI += ' <input type="submit" value="수정">';
		modifyUI += ' <input type="button" value="취소" class="re-reset">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
		
		//이전에 이미 수정하는 댓글이 있을 경우
		//수정버튼을 클릭하면 숨겨져있는 sub-item을
		//환원시키고 수정폼을 초기화함
		initModifyForm();
		//데이터가 표시되어 있는 div 감추기
		$(this).parent().hide();
		//수정폼을 수정하고자 하는 데이터가 있는
		//div에 노출
		$(this).parents('.item').append(modifyUI);
		
		//입력한 글자수 셋팅
		let inputLength = 
		      $('#mre_content').val().length;
        let remain = 300 - inputLength;
		remain += '/300';
		
		//문서 객체에 반영
		$('#mre_first .letter-count').text(remain);
		
	});
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화
	$(document).on('click','.re-reset',
	                           function(){
		initModifyForm();	
	});
	//댓글 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mre_form').remove();
	}
	//댓글 수정
	$(document).on('submit','#mre_form',
	                         function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#mre_content').val('').focus();
			return false;
		}	
		
		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'updateReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result=='success'){
					$('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'<br>'));
					//날짜
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초미만');
					//수정폼 삭제 및 초기화
					initModifyForm();
				}else if(param.result=='wrongAccess'){
					alert('타인의 글을 수정할 수 없습니다.');
				}else{
					alert('댓글 수정 오류');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		
	});
	
	//댓글 삭제
	$(document).on('click','.delete-btn',
	                          function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		
		$.ajax({
			url:'deleteReply.do',
			type:'post',
			data:{re_num:re_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 삭제할 수 있습니다.');
				}else if(param.result == 'success'){
					alert('삭제 완료!');
					selectList(1);
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글을 삭제할 수 없습니다.');
				}else{
					alert('댓글 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	//초기 데이터(목록) 호출
	selectList(1);
	
});





