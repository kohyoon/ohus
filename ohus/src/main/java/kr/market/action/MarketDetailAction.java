package kr.market.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;
import kr.util.StringUtil;

public class MarketDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인한 회원만 상세 페이지 이동 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num"); // 세션에 로그인한 회원 정보 (이름 확인 필요)
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num == null) { // 로그인 하지 않았을 때
			return "redirect:/member/loginForm.do"; // (로그인 폼 경로 확인 필요)
		}
		
		MarketDAO dao = MarketDAO.getInstance();
		MarketVO market = dao.getDetailMarket(Integer.parseInt(request.getParameter("market_num")));
		dao.updateMarketHit(market.getMarket_num());
		market.setMarket_title(StringUtil.useNoHtml(market.getMarket_title()));
		market.setMarket_content(StringUtil.useBrNoHtml(market.getMarket_content()));
		request.setAttribute("user_num",user_num);
		request.setAttribute("user_auth", user_auth);
		request.setAttribute("market", market);
		
		return "/WEB-INF/views/market/detail.jsp";
	}
	
}
