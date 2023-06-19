package kr.market.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;
import kr.util.PageUtil;

public class MarketListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		MarketDAO dao = MarketDAO.getInstance();
		// 페이지 처리
		int count = dao.getListMarketCount(keyfield, keyword);
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum),count,8,10,"list.do");
		
		List<MarketVO> list = null;
		
		if(count > 0) {
			list = dao.getListMarket(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		request.setAttribute("page", page.getPage());
		return "/WEB-INF/views/market/list.jsp";
	}

}
