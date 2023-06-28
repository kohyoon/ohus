package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.util.PageUtil;

public class AdminPageEventAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do"; 
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_auth<9) {
			return "/WEB-INF/views/common/notice.jsp"; 
		}
		
		String pageNum = request.getParameter("pageNum");
		
		//pageNum이 없는 경우 pageNum을 1페이지로 간주함 
		if(pageNum==null) {
			pageNum = "1";
		}
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		EventDAO dao = EventDAO.getInstance();
		int count = dao.getEventCount(keyfield, keyword);
		
		PageUtil page =  //keyfield, keyword, currentPage, count, rowCount, pageCount, 요청url 순서
				new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "list.do");
		
		List<EventVO> list = null;
		if(count>0) {
			list = dao.getListEvent(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		} 
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/member/adminPageEvent.jsp";
	}

}
