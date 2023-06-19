package kr.market.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;
import kr.util.FileUtil;

public class MarketUpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인 한 회원만 접근 가능
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
				
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		String photo1 = multi.getFilesystemName("market_photo1");
		String photo2 = multi.getFilesystemName("market_photo2");
		int market_num = Integer.parseInt(request.getParameter("market_num"));
		MarketDAO dao = MarketDAO.getInstance();
		MarketVO db_market = dao.getDetailMarket(market_num);
		
		if(db_market.getMem_num() != user_num) {
			FileUtil.removeFile(request, photo1);
			FileUtil.removeFile(request, photo2);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		MarketVO market = new MarketVO();
		
		market.setMarket_title(multi.getParameter("market_title"));
		market.setMarket_content(multi.getParameter("market_content"));
		market.setMarket_status(Integer.parseInt(multi.getParameter("market_status")));
		market.setMarket_photo1(multi.getFilesystemName("market_photo1"));
		market.setMarket_photo2(multi.getFilesystemName("market_photo2"));
		market.setMem_num(user_num);
		
		dao.updateMarket(market);
		
		if(photo1 != null) {
			FileUtil.removeFile(request, photo1);
		}
		
		if(photo2 != null) {
			FileUtil.removeFile(request, photo2);
		}
		
		return "redirect:/market/detail.do?market_num=" + market_num;
	}

}
