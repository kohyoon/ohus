package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

//이벤트 글쓰기 - 로그인 + 관리자만 가능
public class AdminWriteFormAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num==null) {
			return "redirect:/member/loginForm.do"; //로그인 안되어있으면 로그인 하도록
		}
		if(user_auth !=9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		
		return "/WEB-INF/views/event/admin_writeForm.jsp";
	}

}
