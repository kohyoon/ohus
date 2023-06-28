package kr.event.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventReplyVO;

public class UserEventResultListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 회원 확인
        HttpSession session = request.getSession();
        Integer user_num = (Integer) session.getAttribute("user_num");

        if (user_num == null) {
            return "redirect:/member/loginForm.do";
        } 
        
        request.setCharacterEncoding("utf-8");
        EventDAO dao = EventDAO.getInstance();
        //내가 댓글 단 정보 리스트를 가져옴
        List<EventReplyVO> mylist = new ArrayList<EventReplyVO>();
        mylist = dao.getEventRepliesByMember(user_num);

        
        for (EventReplyVO eventReplyVO : mylist) {
			System.out.println(eventReplyVO);
		}
        request.setAttribute("mylist", mylist);
       

		return "/WEB-INF/views/event/userEventResultList.jsp";
	}

}
