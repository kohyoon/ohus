package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemReviewVO;
import kr.item.vo.ItemVO;

public class UserUpdateReviewFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//미로그인
			return "redirect:/member/loginForm.do";
		}
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		
		ItemDAO dao = ItemDAO.getInstance();
		ItemReviewVO db_review = dao.getReviewDetail(review_num);
		ItemVO item = dao.getItem(db_review.getItem_num());
		
		
		if(user_num != db_review.getMem_num()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		request.setAttribute("db_review", db_review);
		request.setAttribute("item", item);
		
		return "/WEB-INF/views/item/user_updateForm.jsp";
	}

}
