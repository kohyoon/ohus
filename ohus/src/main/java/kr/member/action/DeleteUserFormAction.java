package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class DeleteUserFormAction implements Action{

	//삭제는 회원인 경우에만 가능하므로 경로 반환만 하는게 아니라 로그인 체크를 해줘야한다
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//로그인 여부 체크
		HttpSession session = request.getSession();
		
		//세션에서 로그인을 받아옴
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인이 안되어있는 경우
		if(user_num==null) {
			return "redirect:/member/loginForm.do"; //redirect: 가 앞에 있으면 forward하지 않고 do가 붙어 있으므로 저기로 이동만 해줌
		}
		
		//로그인이 되어있는 경우
		//JSP 경로 반환
		return "/WEB-INF/views/member/deleteUserForm.jsp";
	}

}
