package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class AdminDetailUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		//로그인 확인을 위해 사용자 번호를 받아오자
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//A 로그인이 안 되어있는 경우(사용자 번호가 null인 경우)
		if(user_num == null) {
			return "redirect:/member/loginForm.do"; //로그인 폼으로 리다이렉트
		}
		
		//A-1 로그인이 되어있는 경우 - 관리자인지 확인해야하므로 등급을 받아오자
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		//B 관리자(9)가 아닌 경우
		if(user_auth<9) {
			return "/WEB-INF/views/common/notice.jsp"; //알림창
		}
		
		//B-2 관리자로 로그인 한 경우의 처리
		//먼저 회원 번호를 받자
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		
		//자바빈에 회원번호 값을 넘겨주기
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(mem_num);
		
		request.setAttribute("member", member);
		
		return "/WEB-INF/views/member/detailUserForm.jsp";
	}

}
