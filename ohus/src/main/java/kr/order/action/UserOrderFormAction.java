package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class UserOrderFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		// POST 방식의 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "redirect:/item/itemList.do";
		}
		
		
		
		CartDAO dao = CartDAO.getInstance();
		int all_total = dao.getTotalByMem_num(user_num);
		if(all_total <= 0) {
			request.setAttribute("notice_msg", "정상적인 주문이 아니거나 상품의 수량이 부족합니다.");
			request.setAttribute("notice_url", request.getContextPath() + "/item/itemList.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		// 장바구니(목록)에 담겨있는 상품 정보 호출
		List<CartVO> cartList = dao.getListCart(user_num);
		ItemDAO itemDao = ItemDAO.getInstance();
		
		int order_quantity = 0;
		
		for(CartVO cart : cartList) {
			ItemVO item = itemDao.getItem(cart.getItem_num());
			if(item.getItem_status() == 1) {
				request.setAttribute("notice_msg", "[" + item.getItem_name() + "]상품 판매 중지");
				request.setAttribute("notice_url", request.getContextPath() + "/cart/list.do");
				
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
			
			if(item.getItem_stock() < cart.getOrder_quantity()) {
				request.setAttribute("notice_msg", "[" + item.getItem_name() + "]재고수량 부족으로 주문 불가");
				request.setAttribute("notice_url", request.getContextPath() + "/cart/list.do");
				
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
			order_quantity += cart.getOrder_quantity();
		}
		
		
		
		
		request.setAttribute("list", cartList);
		request.setAttribute("all_total", all_total);
		request.setAttribute("order_quantity", order_quantity);
		return "/WEB-INF/views/order/user_orderForm.jsp";
	}

}


















