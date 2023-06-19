package kr.market.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;

public class MarketWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 한 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) {
			return "redirect:/omember/loginForm.do";
		}
		
		return "/WEB-INF/views/market/writeForm.jsp";
	}

}
