package kr.inquiry.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;

public class CustomerCenterAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//공지사항 목록
		NoticeDAO noticeDao = NoticeDAO.getInstance();
		List<NoticeVO> noticeList = noticeDao.getListNotice(1, 5, null, null);
		
		request.setAttribute("noticeList", noticeList);
		
		return "/WEB-INF/views/inquiry/customer_center.jsp";
	}

}
