package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class UserDirectOrderFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		int order_quantity = Integer.parseInt(request.getParameter("order_quantity"));
		
		ItemDAO itemDao = ItemDAO.getInstance();
		ItemVO item = itemDao.getItem(item_num);
		
		int totalPrice = order_quantity * item.getItem_price();
		
		request.setAttribute("item", item);
		request.setAttribute("order_quantity",order_quantity);
		request.setAttribute("totalPrice",totalPrice);
		
		return "/WEB-INF/views/order/user_directOrderForm.jsp";
	}

}
