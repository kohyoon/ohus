package kr.chat.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;

public class InsertChatMessageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인한 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else {
			// 인코딩 처리
			request.setCharacterEncoding("utf-8");
			ChatVO chat = new ChatVO();
			chat.setChatroom_num(chatroom_num);
			chat.setMem_num(user_num);
			chat.setMessage(request.getParameter("message"));
			
			ChatDAO dao = ChatDAO.getInstance();
			dao.insertChatMessage(chat);
			mapAjax.put("result", "success");
			mapAjax.put("chatroom_num", chatroom_num);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
