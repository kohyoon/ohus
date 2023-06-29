package kr.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;

public class UserDirectOrderAction implements Action{

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
		
		// 상품 정보 담기
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		int order_quantity = Integer.parseInt(request.getParameter("order_quantity"));
		int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
		
		List<OrderDetailVO> orderDetailList = new ArrayList<OrderDetailVO>();
		ItemDAO itemDao = ItemDAO.getInstance();
		ItemVO item = itemDao.getItem(item_num);
		
		if(item.getItem_status() == 1) {
			// 상품 미표시
			request.setAttribute("notice_msg", "[" + item.getItem_name() + "]상품판매중지");
			request.setAttribute("notice_url", request.getContextPath() + "/item/list.do");
			
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		if(item.getItem_stock() < order_quantity) {
			// 상품 재고 수량 부족
			request.setAttribute("notice_msg", "[" + item.getItem_name() + "]재고수량 부족으로 주문 불가");
			request.setAttribute("notice_url", request.getContextPath() + "/item/list.do");
		}
		
		OrderDetailVO orderDetail = new OrderDetailVO();
		orderDetail.setItem_num(item.getItem_num());
		orderDetail.setItem_name(item.getItem_name());
		orderDetail.setItem_price(item.getItem_price());
		orderDetail.setOrder_quantity(order_quantity);
		orderDetail.setItem_total(totalPrice);
		orderDetail.setMem_num(user_num);
		orderDetailList.add(orderDetail);
		
		// 구매 정보 담기
		OrderVO order = new OrderVO();
		order.setItem_name(item.getItem_name());
		order.setOrder_total(totalPrice);
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
