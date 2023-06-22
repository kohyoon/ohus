package kr.event.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.util.PageUtil;

public class EndEventListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//관리자 작업
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth !=9) {
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
		
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "endEventList.do");
		
		List<EventVO> list = null;
		if(count>0) { //리스트에 이벤트 종료된 것만 넣어줌
			list = dao.getListEndEvent(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		} 
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		
		
		return "/WEB-INF/views/event/endEventList.jsp";
	}

}
