package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class ModifyPasswordAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("user_num");

		// 로그인이 되지 않은 경우
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}

		// 로그인 된 경우 - post 방법으로 전송
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");

		// 전송된 데이터 반환 - 아이디, 현재 비밀번호, 새 비밀번호
		String id = request.getParameter("id");
		String origin_passwd = request.getParameter("origin_passwd");
		String passwd = request.getParameter("passwd");

		// 현재 로그인 한 아이디
		String user_id = (String) session.getAttribute("user_id");

		MemberDAO dao = MemberDAO.getInstance();
		// 입력한 아이디를 통해서 회원정보를 반환받음
		MemberVO member = dao.checkMember(id);

		boolean check = false;

		// 사용자가 입력한 아이디가 존재하고 로그인 한 아이디와 일치하는지 체크
		if (member != null && id.equals(user_id)) {
			check = member.isCheckedPassword(origin_passwd);

		}
		if (check) {
			dao.updatePassword(passwd, user_num);
		}

		request.setAttribute("check", check);

		return "/WEB-INF/views/member/modifyPassword.jsp";
	}

}
