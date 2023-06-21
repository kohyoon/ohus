package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.controller.Action;

import kr.util.FileUtil;

public class DeleteAction implements Action{
	//이벤트 글삭제하기
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//회원제 서비스이므로 로그인 체크
		HttpSession session = request.getSession(); 
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		//로그인x
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//관리자 x
		if(user_auth<9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		int event_num = Integer.parseInt(request.getParameter("event_num"));
		
		EventDAO dao = EventDAO.getInstance();
		EventVO db_event = dao.getEvent(event_num);
		
		dao.deleteEvent(event_num);
		
		//파일(사진) 삭제
		FileUtil.removeFile(request, db_event.getEvent_photo());
		
		return "redirect:/event/list.do";
	}

}
