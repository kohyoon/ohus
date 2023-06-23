package kr.order.action;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;
import kr.cart.vo.CartVO;
import kr.cart.dao.CartDAO;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class UserOrderAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "redirect:/item/itemList.do";
		}
		
		request.setCharacterEncoding("utf-8");
		
		CartDAO dao = CartDAO.getInstance();
		int all_total = dao.getTotalByMem_num(user_num);
		
		if(all_total <= 0) {
			request.setAttribute("notice_msg", "정상적인 주문이 아니거나 상품의 수량이 부족합니다.");
			request.setAttribute("notice_url", request.getContextPath() + "/item/itemList.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		List<CartVO> cartList = dao.getListCart(user_num);
	
		// 주문 상품의 대표 상품명 생성
		String item_name;
		
		if(cartList.size() == 1) { // 상품이 하나인 경우
			item_name = cartList.get(0).getItemVO().getItem_name();
		} else { // 상품이 여러 개인 경우
			item_name = cartList.get(0).getItemVO().getItem_name() + "외 " + (cartList.size()-1) + "건";
		}
		
		// 개별 상품 정보 담기
		List<OrderDetailVO> orderDetailList = new ArrayList<OrderDetailVO>();
		ItemDAO itemDao = ItemDAO.getInstance();
		
		for(CartVO cart : cartList) {
			// 주문하는 상품 정보 읽기
			ItemVO item = itemDao.getItem(cart.getItem_num());
			if(item.getItem_status() == 1) {
				// 상품 미표시
				request.setAttribute("notice_msg", "[" + item.getItem_name() + "]상품판매중지");
				request.setAttribute("notice_url", request.getContextPath() + "/cart/list.do");
				
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
			
			if(item.getItem_stock() < cart.getOrder_quantity()) {
				// 상품 재고 수량 부족
				request.setAttribute("notice_msg", "[" + item.getItem_name() + "]재고수량 부족으로 주문 불가");
				request.setAttribute("notice_url", request.getContextPath() + "/cart/list.do");
			}
			OrderDetailVO orderDetail = new OrderDetailVO();
			orderDetail.setItem_num(cart.getItem_num());
			orderDetail.setItem_name(cart.getItemVO().getItem_name());
			orderDetail.setItem_price(cart.getItemVO().getItem_price());
			orderDetail.setOrder_quantity(cart.getOrder_quantity());
			orderDetail.setItem_total(cart.getSub_total());
			
			orderDetailList.add(orderDetail);
			
		}
		
		
		// 구매 정보 담기
		OrderVO order = new OrderVO();
		order.setItem_name(item_name);
		order.setOrder_total(all_total);
		order.setOrder_payment(Integer.parseInt(request.getParameter("order_payment")));
		order.setOrder_name(request.getParameter("order_name"));
		order.setOrder_zipcode(request.getParameter("order_zipcode"));
		order.setOrder_address1(request.getParameter("order_address1"));
		order.setOrder_address2(request.getParameter("order_address2"));
		order.setMem_phone(request.getParameter("mem_phone"));
		order.setOrder_notice(request.getParameter("order_notice"));
		order.setOrder_quantity(Integer.parseInt(request.getParameter("order_quantity")));
		order.setOrder_status(1);
		order.setMem_num(user_num);
		OrderDAO orderDao = OrderDAO.getInstance();
		orderDao.insertOrder(order, orderDetailList);
		
		// refresh 정보를 응답 헤더에 추가
		response.addHeader("Refresh", "2;url=../main/main.do");
		request.setAttribute("accessMsg", "주문이 완료되었습니다.");
		request.setAttribute("accessUrl", request.getContextPath() + "/main/main.do");
	
	return "/WEB-INF/views/common/notice.jsp";
	}

}


























