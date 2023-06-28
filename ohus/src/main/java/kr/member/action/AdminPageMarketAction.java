package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;
import kr.util.PageUtil;

public class AdminPageMarketAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		if(user_auth < 9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
				
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		MarketDAO dao = MarketDAO.getInstance();
		
		int count = dao.getListMarketCount(keyfield, keyword);
		
		//페이지 처리
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 10, 10, "list.do");
				
		
		List<MarketVO> list = null;
		
		if(count>0) {
			list = dao.getListMarket(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		//deletMarket(int market_num)이 삭제 메서드
		
		
		
		
		return "/WEB-INF/views/member/adminPageMarket.jsp";
	}

}
