package kr.event.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventWinnerVO;

public class AdminPlayEventResultAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 관리자 확인
        HttpSession session = request.getSession();
        Integer user_num = (Integer) session.getAttribute("user_num");
        Integer user_auth = (Integer) session.getAttribute("user_auth");

        if (user_num == null) {
            return "redirect:/member/loginForm.do";
        }
        if (user_auth != 9) {
            return "/WEB-INF/views/common/notice.jsp";
        }

        // 이벤트 번호 저장
        int event_num = Integer.parseInt(request.getParameter("event_num"));

        EventDAO dao = EventDAO.getInstance();
        List<EventWinnerVO> win =dao.getListEventWin(event_num);
        
        request.setAttribute("win", win);
        
        
        return "/WEB-INF/views/event/playEventResult.jsp";
		
	}

}
