package kr.event.action;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventReplyVO;

//댓글 작성 - 회원제 + 한 번만 가능
public class WriteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		
		Map<String, String> mapAjax = new HashMap<String, String>(); 
		
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		int event_num = Integer.parseInt(request.getParameter("event_num"));
		
		
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}
		EventReplyVO reply = null;
		EventDAO dao = EventDAO.getInstance();
		//메서드 호출
		reply=dao.checkReply(user_num, event_num);
		
		
		if(reply!=null) {
			
			mapAjax.put("result", "duplication");
		}
		
		
		//로그인 된 경우
		else {
			request.setCharacterEncoding("utf-8");
			
			
			
			//dao에서 넘긴 값을 그대로 저장해주기
			reply.setMem_num(user_num);
			reply.setRe_content(request.getParameter("re_content"));
			reply.setRe_ip(request.getRemoteAddr());
			reply.setEvent_num(Integer.parseInt(request.getParameter("event_num")));
			
			dao.insertReplyEvent(reply); //댓글 등록을 해줌
			
			mapAjax.put("result", "success");
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		
		//메서드 호출
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		//JSP에 반영
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp"
;
	}

}
