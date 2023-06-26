package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class UserModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		// POST 방식 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {// 방식에 따라 접근을 허용
			return "redirect:/item/itemList.do";
		}
		
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		int order_num = Integer.parseInt(request.getParameter("order_num"));
		
		// 주문 수정 전 배송 상태를 한 번 더 체크
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order_num);
		
		if(db_order.getOrder_status() > 1) {
			// 배송준비중 이상으로, 관리자가 변경한 상품을 주문자가 변경할 수 없음
			request.setAttribute("notice_msg", "배송상태가 변경되어 주문자가 주문 정보 변경 불가");
			request.setAttribute("notice_url", request.getContextPath() + "/order/orderList.do");
		}
		
		// 배송상태가 배송대기일 경우
		OrderVO order = new OrderVO();
		order.setOrder_num(order_num); // 미리 뽑았기 떄문에 변수에 담겨있는 걸 바로 가져올 수 있음
		order.setOrder_status(Integer.parseInt(request.getParameter("order_status")));
		order.setOrder_name(request.getParameter("order_name"));
		order.setOrder_zipcode(request.getParameter("order_zipcode"));
		order.setOrder_address1(request.getParameter("order_address1"));
		order.setOrder_address2(request.getParameter("order_address2"));
		order.setMem_phone(request.getParameter("mem_phone"));
		order.setOrder_notice(request.getParameter("order_notice"));
		
		// 배송관련 주문 정보 수정(재고 수량 변경 문제?)
		dao.updateOrder(order);
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath() + "/order/orderModifyForm.do?order_num=" + order_num);
		
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}

