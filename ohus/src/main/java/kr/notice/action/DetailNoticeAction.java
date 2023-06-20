package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.StringUtil;

public class DetailNoticeAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//공지사항 번호
		int notice_num = Integer.parseInt(request.getParameter("notice_num"));
		NoticeDAO dao = NoticeDAO.getInstance();
		dao.updateReadcount(notice_num); //조회수 증가
		
		NoticeVO notice = dao.getNotice(notice_num);
		
		//HTML 태그 허용X
		notice.setNotice_title(StringUtil.useNoHtml(notice.getNotice_title()));
		//HTML 태그 허용X & 줄바꿈O
		notice.setNotice_content(StringUtil.useBrNoHtml(notice.getNotice_content()));
		
		request.setAttribute("notice", notice);
		
		return "/WEB-INF/views/notice/detailNotice.jsp";
	}

}
