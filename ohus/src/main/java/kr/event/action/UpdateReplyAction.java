package kr.event.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventReplyVO;

public class UpdateReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8"); 
		
		//전송된 데이터 반환 - 댓글 번호
		int re_num = Integer.parseInt(request.getParameter("re_num"));
		
		EventDAO dao = EventDAO.getInstance();
		EventReplyVO db_reply = dao.getReplyEvent(re_num); 
		
		HttpSession session = request.getSession();
				
		Integer user_num = (Integer)session.getAttribute("user_num"); 
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		//로그인 x
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}
		//로그인 o + 로그인회원=작성자회원
		else if(user_num != null && user_num == db_reply.getMem_num()) {
			
			//자바빈을 생성하고 전송된 데이터를 저장
			EventReplyVO reply = new EventReplyVO();
			reply.setRe_num(re_num);
			reply.setRe_content(request.getParameter("re_content"));
			reply.setRe_ip(request.getRemoteAddr());
			
			dao.updateReplyEvent(reply); //메서드 호출
			
			mapAjax.put("result", "success");
		} 
		//로그인o (로그인은 되어있는데 작성자와 다른 경우)
		else {
			mapAjax.put("result","wrongAccess");
			
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
