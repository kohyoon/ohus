package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

//DAO에서 처리한 회원 정보 수정 - auth와 mem_num을 인자로 받아 update 처리했다

public class AdminDetailUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*처리할 내용 : 
		 로그인이 안된 경우) user_num으로 확인 --> 로그인 폼으로 리다이렉트 해주기
		 로그인이 된 경우 ) 관리자인지 확인하기 위해 먼저 등급을 받아옴
		 					 ) 등급을 받아왔는데 관리자가 아닌 경우 -- 알림창 띄워주기(notice)
		 					 ) 관리자 등급이 맞는 경우 mem_num, auth를 받아와서 dao에서 생성한 메서드 호출하기
		 					   그리고 jsp로 return
		 */
		//로그인체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//1-1. 로그인이 안 되어있는 경우
		if(user_num == null) {
			return "redirect:/member/loginForm.do"; //로그인 폼으로 리다이렉트
		}
		
		//1-2. 로그인이 되어있는 경우 - 관리자인지 확인해야하므로 등급을 받아오자
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		//2. 관리자(9)가 아닌 경우
		if(user_auth<9) {
			return "/WEB-INF/views/common/notice.jsp"; //알림창
		}
		
		//2-1. 관리자로 로그인 한 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//전송된 데이터 반환
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		int auth = Integer.parseInt(request.getParameter("auth"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.updateMemberByAdmin(auth, mem_num);
		
		request.setAttribute("mem_num", mem_num);
		
		return "/WEB-INF/views/member/detailUser.jsp";
		

	}

}
