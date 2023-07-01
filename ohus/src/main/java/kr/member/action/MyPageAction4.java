package kr.member.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
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
		
		List<Integer> readCheckList = new ArrayList<Integer>();
		// 채팅방에 채팅 메시지의 read Check가 1인게 존재한다면 -> 알림 표시 하기위한 readCheckList 구하기
		for(ChatroomVO chatroom : receiveList) {
			boolean check = false;
			List<ChatVO> chatList = dao.readMessageCheck(chatroom.getChatroom_num(), user_num);
			for(ChatVO chat : chatList) {
				if(chat.getRead_check()==1) {
					readCheckList.add(1);
					check=true;
					break;
				}
			}
			if(!check)readCheckList.add(0);
		}
		
		request.setAttribute("sendList", sendList);
		request.setAttribute("receiveList", receiveList);
		request.setAttribute("readCheckList", readCheckList);
		return "/WEB-INF/views/member/myPage4.jsp";
	}

}
