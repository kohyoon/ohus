package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemFavVO;
import kr.item.vo.ItemVO;
import kr.util.StringUtil;

public class UserDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//상품 번호 반환
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		ItemDAO dao = ItemDAO.getInstance();
		
		//상품 정보 가져오기
		ItemVO item = dao.getItem(item_num);
		//내용 줄바꿈 처리
		item.setItem_content(StringUtil.useBrHtml(item.getItem_content()));
		
		//조회수 증가
		dao.updateViewsCount(item_num);
		
		//상품 스크랩(로그인한 사용자가 스크랩을 했는지 안했는지 확인용)
		//HttpSession session = request.getSession();
		//Integer user_num = (Integer)session.getAttribute("user_num");
		//ItemFavVO fav = dao.selectFav(item_num, user_num);
		
		//리뷰 개수
		int reviewCount = dao.getReviewCount(item_num);
		
		//request.setAttribute("user_num", user_num);
		request.setAttribute("reviewCount", reviewCount);
		request.setAttribute("item", item);
		//request.setAttribute("fav", fav);
		
		
		return "/WEB-INF/views/item/user_detail.jsp";
	}

}
