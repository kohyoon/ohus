package kr.chat.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatroomVO;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

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
		// 구매자 회원번호, 판매자 회원번호
		int buyer_num = Integer.parseInt(request.getParameter("buyer_num"));
		int seller_num = Integer.parseInt(request.getParameter("seller_num"));
		ChatDAO dao = ChatDAO.getInstance();
		MemberDAO memberDao = MemberDAO.getInstance();
		MemberVO member = null;
		// 채팅방번호 -> market_title을 가져올 수 있나 -> chatroom_vo에 존재하긴함..
		ChatroomVO chatroom = dao.getDetailChatroomInfo(chatroom_num);
		
		// 로그인한 회원과 seller_num이 같다면 상대방은 buyer_num
		if(user_num == chatroom.getSeller_num()) {
			member = memberDao.getMember(chatroom.getBuyer_num());
		}else {
			member = memberDao.getMember(chatroom.getSeller_num());
		}
		request.setAttribute("your", member);
		request.setAttribute("market_title", chatroom.getMarket_title());
		request.setAttribute("chatroom_num", chatroom_num);
		request.setAttribute("user_num", user_num);
		
		return "/WEB-INF/views/chatting/chatting.jsp";
	}
	
}
