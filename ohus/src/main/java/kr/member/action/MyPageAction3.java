package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
//[설정] --member
//회원 정보를 보여주고 회원 정보 수정 버튼과 비밀번호 수정 버튼, 회원 탈퇴 버튼 만들어주기
public class MyPageAction3 implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 + 회원 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";			
		}
		if(user_auth !=2) {
			return "/WEB-INF/views/common/notice.jsp";
		}



		//로그인 된 경우
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(user_num);
		
		request.setAttribute("member", member);		
		
		return "/WEB-INF/views/member/myPage3.jsp";
	}

}
