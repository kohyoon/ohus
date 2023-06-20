package kr.market.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;

public class MarketDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인한 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		int market_num = Integer.parseInt(request.getParameter("market_num"));
		MarketDAO dao = MarketDAO.getInstance();
		MarketVO db_market = dao.getDetailMarket(market_num);
		
		// 작성자와 로그인한 회원이 일치할 경우에만 삭제 가능
		if(db_market.getMem_num() != user_num) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		// 채팅방과 참조하고 있어서 채팅방이 생성되어 있는 경우 cascade로 해야할지 정해야함.
		dao.deleteMarket(market_num);
		return "/WEB-INF/views/market/delete.jsp";
	}

}
