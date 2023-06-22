package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.StringUtil;

public class UserDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//상품 번호 반환
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		ItemDAO dao = ItemDAO.getInstance();
		//조회수 증가
		dao.updateViewsCount(item_num);
		
		ItemVO item = dao.getItem(item_num);
		
		//내용 줄바꿈 처리
		item.setItem_content(StringUtil.useBrHtml(item.getItem_content()));
		
		//리뷰 개수
		int reviewCount = dao.getReviewCount(item_num);
		
		
		request.setAttribute("item", item);
		request.setAttribute("reviewCount", reviewCount);
		
		return "/WEB-INF/views/item/user_detail.jsp";
	}

}
