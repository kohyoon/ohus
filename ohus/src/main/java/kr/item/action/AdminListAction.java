package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class AdminListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//미로그인
			return "redirect:/member/loginForm.do";
		}
		/*
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) {//관리자 계정 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		*/
		//관리자로 로그인한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
				
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
				
				
		return "/WEB-INF/views/item/admin_list.jsp";
	}

}
