package kr.order.action;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;

public class UserModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		// get방식으로 order_num을 넘겨받음
		int order_num = Integer.parseInt(request.getParameter("order_num"));
		
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO order = dao.getOrder(order_num);
		
		// 주문 정보, 주문상세정보를 읽어옴
		List<OrderDetailVO> detailList = dao.getListOrderDetail(order_num);
		
		ItemDAO itemDao = ItemDAO.getInstance();
		List<ItemVO> itemList = new ArrayList<ItemVO>();
		for(int i = 0; i < detailList.size(); i++) {
			ItemVO item = new ItemVO();
			item = itemDao.getItem(detailList.get(i).getItem_num());
			itemList.add(item);
		}
		
		
		request.setAttribute("order", order);
		request.setAttribute("detailList", detailList);
		request.setAttribute("itemList", itemList);
		
		return "/WEB-INF/views/order/user_modifyForm.jsp";
	}

}









