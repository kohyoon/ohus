package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;


import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.util.FileUtil;

public class AdminUpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//파일은 필수이므로 나눌 필요 x
		//관리자 체크
		/*
		 * 글 수정 case1 : 제목, 내용 작성
		 * 			 case2 : 제목, 내용, 파일 모두 작성한 경우 
		 */
		//관리자 확인
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth !=9) {
			return "/WEB-INF/views/common/notice.jsp";

		}
	
		MultipartRequest multi = FileUtil.createFile(request); //파일 업로드 해주는 역할
		
		//파일이 있는 경우) 글 수정 후 파일을 삭제 해줘야한다! 안 그러면 쓰레기값이 남음 
		//- 번호와 파일명을 변수에 저장
		int event_num = Integer.parseInt(multi.getParameter("event_num"));
		String event_photo = multi.getFilesystemName("event_photo");
		
		EventDAO dao = EventDAO.getInstance();
		
		//수정 전 데이터 반환
		EventVO db_event = dao.getEvent(event_num); 
		
		//작업시작
		EventVO event = new EventVO();
		event.setEvent_num(event_num);
		event.setEvent_title(multi.getParameter("event_title"));
		event.setEvent_content(multi.getParameter("event_content"));
		event.setEvent_photo(event_photo);
		event.setEvent_start(multi.getParameter("event_start"));
		event.setEvent_end(multi.getParameter("event_end"));
		
		dao.updateEvent(event);
		
		//새 파일로 교체할 때 원래 파일 제거해주기(수정 전 내용 지워주기)
		if(event_photo != null) {
			//파일을 새로 수정한 경우
			FileUtil.removeFile(request, db_event.getEvent_photo());
		}
		
		//수정 다 하고는 상세 페이지로 이동하기!
		return "redirect:/event/detail.do?event_num="+event_num;
	}

}
