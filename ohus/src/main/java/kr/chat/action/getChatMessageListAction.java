package kr.chat.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;

public class getChatMessageListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 한 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		// 채팅 메시지 목록 가져오기
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		ChatDAO dao = ChatDAO.getInstance();
		List<ChatVO> list = dao.getChatMessageList(chatroom_num);
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("list", list); // 채팅방에 대한 채팅 메시지 목록
		mapAjax.put("user_num", user_num); // 현재 로그인한 회원
		mapAjax.put("listSize", list.size());
		mapAjax.put("chatroom_num", chatroom_num);
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
