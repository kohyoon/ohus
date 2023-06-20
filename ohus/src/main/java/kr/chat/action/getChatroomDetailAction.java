package kr.chat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;

public class getChatroomDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인한 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		
		request.setAttribute("chatroom_num", chatroom_num);
		request.setAttribute("user_num", user_num);
		
		return "/WEB-INF/views/chatting/chatting.jsp";
	}
	
}
