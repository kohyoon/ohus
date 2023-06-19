package kr.market.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;

public class MarketUpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인 한 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
				
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
				
		// 작성자와 로그인한 회원이 같은 경우에만 수정 접근 가능
		int martket_num = Integer.parseInt(request.getParameter("market_num"));
		MarketDAO dao = MarketDAO.getInstance();
		MarketVO market = dao.getDetailMarket(martket_num);
				
		if(user_num != market.getMem_num()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
				
		request.setAttribute("market", market);
		
		return "/WEB-INF/views/market/updateForm.jsp";
		
	}

}
