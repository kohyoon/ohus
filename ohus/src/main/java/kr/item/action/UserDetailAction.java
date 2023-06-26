package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemQnaVO;
import kr.item.vo.ItemVO;
import kr.util.PageUtil;
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
		
		//리뷰 개수
		int reviewCount = dao.getReviewCount(item_num);
		
		//문의 개수
		int qnaCount = dao.getQnaCount(item_num);
		
		PageUtil page = new PageUtil(1, qnaCount, 10);
		
		List<ItemQnaVO> list = null;
		if(qnaCount > 0) {
			list = dao.getListQna(1, 10, item_num);
		}
		
		request.setAttribute("reviewCount", reviewCount);
		request.setAttribute("item", item);
		request.setAttribute("qnaCount", qnaCount);
		request.setAttribute("list", list);
		
		return "/WEB-INF/views/item/user_detail.jsp";
	}

}
