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
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) {
			return "redirect:/omember/loginForm.do";
		}
		int market_num = Integer.parseInt(request.getParameter("market_num"));
		MarketDAO dao = MarketDAO.getInstance();
		MarketVO db_market = dao.getDetailMarket(market_num);
		
		// 작성자와 로그인한 회원이 일치할 경우에만 삭제 가능
		if(db_market.getMem_num() != mem_num) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		dao.deleteMarket(market_num);
		return "/WEB-INF/views/market/delete.jsp";
	}

}
