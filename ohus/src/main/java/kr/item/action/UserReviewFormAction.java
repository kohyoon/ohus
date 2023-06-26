package kr.item.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.order.dao.OrderDAO;

public class UserReviewFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "redirect:/main/main.do";
		}
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		if(user_num != mem_num) {
			request.setAttribute("notice_msg", "정상적인 접근이 아닙니다!");
			request.setAttribute("notice_url", request.getContextPath() + "/main/main.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO item = dao.getItem(item_num);
		String item_name = item.getItem_name();
		String item_photo1 = item.getItem_photo1();
		
		request.setAttribute("item_num", item_num);
		request.setAttribute("item_name", item_name);
		request.setAttribute("item_photo1", item_photo1);
		
		
		return "/WEB-INF/views/item/user_reviewForm.jsp";
	}

}
