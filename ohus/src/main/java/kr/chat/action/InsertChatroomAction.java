package kr.chat.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatroomVO;
import kr.controller.Action;

public class InsertChatroomAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인한 회원만 채팅 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}

		int market_num = Integer.parseInt(request.getParameter("market_num"));
		int buyer_num = Integer.parseInt(request.getParameter("buyer_num"));
		int seller_num = Integer.parseInt(request.getParameter("seller_num"));

		ChatDAO dao = ChatDAO.getInstance();
		ChatroomVO chatroom = new ChatroomVO();
		ChatroomVO db_chatroom = dao.getChatroomByBuyer(market_num, buyer_num);
		// 생성된 채팅방이 존재한지 체크
		
		if(db_chatroom == null) {
			chatroom.setMarket_num(market_num);
			chatroom.setBuyer_num(buyer_num);
			chatroom.setSeller_num(seller_num);
			dao.insertChatroom(chatroom);
			db_chatroom = dao.getChatroomByBuyer(market_num, buyer_num);
		}
		request.setAttribute("chatroom", db_chatroom);

		return "/WEB-INF/views/chatting/chatroomSuccess.jsp";
	}

}
