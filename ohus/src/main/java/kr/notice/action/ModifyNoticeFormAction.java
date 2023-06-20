package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.StringUtil;

public class ModifyNoticeFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) { //관리자가 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		//관리자로 로그인 된 경우
		int notice_num = Integer.parseInt(request.getParameter("user_num"));
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeVO notice = dao.getNotice(notice_num);
		//로그인 한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != notice.getMem_num()) {
			//불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//큰 따옴표 처리(수정폼의 input 태그에서 오동작)
		notice.setNotice_title(StringUtil.parseQuot(notice.getNotice_title()));
		
		//관리자로 로그인 되어 있고 로그인 한 번호와 작성자 회원번호가 일치
		request.setAttribute("notice", notice);
		
		return "/WEB-INF/views/notice/modifyNoticeForm.jsp";
	}

}
