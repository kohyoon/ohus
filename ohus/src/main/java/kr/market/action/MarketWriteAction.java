package kr.market.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;
import kr.util.FileUtil;

public class MarketWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		// post 방식 처리
		request.setCharacterEncoding("utf-8");
		
		MultipartRequest multi = FileUtil.createFile(request);
		MarketDAO dao = MarketDAO.getInstance();
		MarketVO market = new MarketVO();
		
		market.setMarket_title(multi.getParameter("market_title"));
		market.setMarket_content(multi.getParameter("market_content"));
		market.setMarket_hit(0);
		market.setMarket_status(Integer.parseInt(multi.getParameter("market_status")));
		market.setMarket_photo1(multi.getFilesystemName("market_photo1"));
		market.setMarket_photo2(multi.getFilesystemName("market_photo2"));
		market.setMem_num(user_num);
		
		dao.insertMarket(market);
		
		return "/WEB-INF/views/market/write.jsp";
	}

}
