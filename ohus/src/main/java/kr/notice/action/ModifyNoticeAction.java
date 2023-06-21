package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.FileUtil;

public class ModifyNoticeAction implements Action {

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
		MultipartRequest multi = FileUtil.createFile(request);
		int notice_num = Integer.parseInt(multi.getParameter("notice_num"));
		String filename = multi.getFilesystemName("filename");
		NoticeDAO dao = NoticeDAO.getInstance();
		
		//수정 전 데이터 반환
		NoticeVO db_notice = dao.getNotice(notice_num);
		//로그인 한 회원번호와 작성자 회원번호 일치 여부 확인
		if(user_num != db_notice.getMem_num()) {
			//불일치
			FileUtil.removeFile(request, filename);
			return "/WEB-INF/views/common/notice.jsp";
		}
		//일치
		NoticeVO notice = new NoticeVO();
		notice.setNotice_num(notice_num);
		notice.setNotice_title(multi.getParameter("title"));
		notice.setNotice_content(multi.getParameter("content"));
		notice.setNotice_filename(filename);
		
		dao.updateNotice(notice);
		
		//새 파일로 교체할 때 원래 파일 제거
		if(filename != null) {
			FileUtil.removeFile(request, db_notice.getNotice_filename());
		}
		
		return "redirect:/notice/detailNotice.do?notice_num=" + notice_num;
	}

}
