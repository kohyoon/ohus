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

public class DeleteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8"); 
		
		int re_num = Integer.parseInt(request.getParameter("re_num"));
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		//작성자 정보 읽어오기
		EventDAO dao = EventDAO.getInstance();
		EventReplyVO db_reply = dao.getReplyEvent(re_num);

		HttpSession session = request.getSession();
		
		//session에서 회원번호 user_num을 받아온다
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 x
		if(user_num==null) {
			mapAjax.put("result", "logout");
		} 
		//로그인 o + 로그인번호=작성자 번호
		//즉, 로그인 되어있고 그 로그인 한 회원이 작성자인 경우
		else if(user_num!=null && user_num==db_reply.getMem_num()) {
			dao.deleteReplyEvent(re_num); //삭제 메서드 호출
			mapAjax.put("result", "success"); //정상 처리된 것을 알려주기
		}
		//로그인 o
		//로그인은 됐지만 작성자는 아닌 경우
		else {
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON 데이터 생성하기! ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		
		//메서드를 통해 변환작업
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
