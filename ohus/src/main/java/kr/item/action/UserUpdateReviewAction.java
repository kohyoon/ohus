package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemReviewVO;
import kr.util.FileUtil;

public class UserUpdateReviewAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//미로그인
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int item_num = Integer.parseInt(multi.getParameter("item_num"));
		
		int review_num = Integer.parseInt(multi.getParameter("review_num"));
		
		
		ItemDAO dao = ItemDAO.getInstance();
		ItemReviewVO review = new ItemReviewVO();
		review.setReview_num(review_num);
		review.setMem_num(user_num);
		review.setItem_num(item_num);
		review.setItem_score(Integer.parseInt(multi.getParameter("item_score")));
		review.setReview_content(multi.getParameter("review_content"));
		review.setReview_photo(multi.getFilesystemName("review_photo"));
		
		dao.updateReview(review);
		
		request.setAttribute("notice_msg", "후기 수정을 성공했습니다.");
		request.setAttribute("notice_url", request.getContextPath() + "/item/detail.do?item_num=" + item_num);
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
