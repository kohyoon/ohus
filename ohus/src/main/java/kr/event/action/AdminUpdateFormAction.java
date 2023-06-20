package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.util.StringUtil;

public class AdminUpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(); 
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth !=9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		int event_num = Integer.parseInt(request.getParameter("event_num"));
		
		EventDAO dao = EventDAO.getInstance();
		EventVO event = dao.getEvent(event_num);
		
		//큰따옴표로 제목을 쓴 경우 수정 누르면 이상하게 됨 - 수정 폼의 input 태그 오동작 잡기
		event.setEvent_title(StringUtil.parseQuot(event.getEvent_title()));
		
		request.setAttribute("event", event);
		
		return "/WEB-INF/views/event/admin_updateForm.jsp";
	}

}
