package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.FileUtil;

public class WriteNoticeAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) { //관리자가 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//관리자로 로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		NoticeVO notice = new NoticeVO();
		notice.setNotice_title(multi.getParameter("title"));
		notice.setNotice_content(multi.getParameter("content"));
		notice.setNotice_filename(multi.getFilesystemName("filename"));
		notice.setMem_num(user_num);
		
		NoticeDAO dao = NoticeDAO.getInstance();
		dao.insertNotice(notice);
		
		return "/WEB-INF/views/notice/writeNotice.jsp";
	}

}
