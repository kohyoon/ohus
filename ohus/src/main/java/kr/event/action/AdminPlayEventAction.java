package kr.event.action;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.event.vo.EventWinnerVO;

public class AdminPlayEventAction implements Action {

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
        
     // 이벤트 추첨 여부 확인
        EventDAO dao = EventDAO.getInstance();
        EventVO event = dao.getEvent(event_num);
        if (event.getEvent_check() == 1) {
            // 이미 추첨이 실행된 경우, 추첨 결과를 가져온다
            List<EventWinnerVO> plz = dao.getListEventWin(event_num);
            request.setAttribute("plz", plz);

            // 리다이렉션을 수행하도록 추가
            response.sendRedirect("playEventResult.jsp");
            return null; // 리다이렉션을 수행하므로 다음으로 진행할 필요가 없음
        }

        return "/WEB-INF/views/event/playEvent.jsp";
    }
}
