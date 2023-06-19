package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.FileUtil;

//로그인이 됐는지 확인해야함
public class DeleteUserAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 로그인 여부 체크
		HttpSession session = request.getSession();

		// 세션에서 회원 번호를 받아옴
		Integer user_num = (Integer) session.getAttribute("user_num");

		// 로그인 안 된 경우
		if (user_num == null) {

			return "redirect:/member/loginForm.jsp"; //
		}

		// 로그인 된 경우 - 인코딩 처리 --> 데이터 반환 --> 로그인 일치 여부 체크 --> 삭제 작업!
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");

		String user_id = (String) session.getAttribute("user_id");

		// 아이디가 일치하는지 확인하고 삭제작업을 해줘야함
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO db_member = dao.checkMember(id);
		boolean check = false;

		// 조건체크 -- 사용자의 아이디가 존재 + 로그인 아이디와 입력한 아이디가 일치 + 입력한 이메일과 저장된 이메일이 일치하는지 체크
		// checkMember 메서드 - 중복 체크 및 로그인 처리. getEmail로 vo의 get 메서드로 읽어옴! -- 잘 활용
	
		if (db_member != null && id.equals(user_id) && email.equals(db_member.getEmail())) {

			check = db_member.isCheckedPassword(passwd);

		}
		// 인증선공
		if (check) {
			dao.deleteMember(user_num);
			FileUtil.removeFile(request, db_member.getPhoto());
			session.invalidate(); // 로그아웃
		}

		request.setAttribute("check", check);

		return "/WEB-INF/views/member/deleteUser.jsp";
	}

}
