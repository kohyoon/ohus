package kr.chat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatroomVO;
import kr.controller.Action;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;
import kr.util.PageUtil;

public class getChatroomListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인한 회원만 접근가능 
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		int market_num = Integer.parseInt(request.getParameter("market_num"));
		int count = 0;
		PageUtil page = new PageUtil(null,null,Integer.parseInt(pageNum),count,10,5,"chatroom.do");
		
		// 로그인한 회원과 거래글 작성자가 같은지 확인
		MarketDAO marketDao = MarketDAO.getInstance();
		MarketVO db_market = marketDao.getDetailMarket(market_num);
		if(db_market.getMem_num() != user_num) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		ChatDAO dao = ChatDAO.getInstance();
		List<ChatroomVO> list = dao.getChatroomList(page.getStartRow(),page.getEndRow(),user_num, market_num);
		count = dao.getChatroomCount(user_num, market_num);
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		request.setAttribute("page", page.getPage());
		return "/WEB-INF/views/chatting/chatroom.jsp";
	}

}
