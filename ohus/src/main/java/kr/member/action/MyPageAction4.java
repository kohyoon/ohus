package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatroomVO;
import kr.controller.Action;

public class MyPageAction4 implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인한 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		ChatDAO dao = ChatDAO.getInstance();
		List<ChatroomVO> sendList = dao.getBuyerChatroomList(user_num);
		List<ChatroomVO> receiveList = dao.getSellerChatroomList(user_num);
		request.setAttribute("sendList", sendList);
		request.setAttribute("receiveList", receiveList);
		return "/WEB-INF/views/member/myPage4.jsp";
	}

}
