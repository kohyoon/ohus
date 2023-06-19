package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.PageUtil;

public class AdminMemberListAction implements Action{
	//관리자 권한으로 회원 정보 조회
	//로그인이 되어 있어야하며, 관리자(auth=9)만 접근할 수 있음
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		//회원 번호를 읽어옴- 로그인 확인을 위해, 회원 상태번호 - 관리자인지 확인하기 위해서
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		//로그인 되지 않은 경우 - 로그인 페이지로 리다이렉트
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//관리자가 아닌 사람이 회원 관리에 접근하는 경우
		if(user_auth <9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//위의 두 조건을 만족하는 경우의 처리! (아래내용)
		
		//관리자로 로그인 한 경우
		String pageNum = request.getParameter("pageNum");
		
		//검색 처리
		if(pageNum == null) { //pageNum이 null인 경우 페이지 넘버를 1로 간주
			pageNum = "1"; 
		}
		
		String keyfield =request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		//count 읽어오기
		MemberDAO dao = MemberDAO.getInstance();
		
		int count = dao.getMemberCountByAdmin(keyfield, keyword); //MemberDAO에서 정의한 목록 메서드
		
		//페이지 처리
		//keyfield, keyword, currentPage,count, rowCount, pageCount, pageUrl
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "memberList.do");
		
		List<MemberVO> list = null;
		
		if(count>0) {
			list = dao.getListMemberByAdmin(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		//JSP 경로반환
		return "/WEB-INF/views/member/memberList.jsp";
	}

}
