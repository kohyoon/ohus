package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class AdminModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) { // 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_auth < 9) { // 관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp"; // forward 방식
		}
		
		// if로 진입하지 않은 경우 = 관리자로 로그인한 경우
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8"); // post 방식일 경우 반드시 명시
		
		OrderVO order = new OrderVO();
		order.setOrder_num(Integer.parseInt(request.getParameter("order_num")));
		order.setOrder_status(Integer.parseInt(request.getParameter("order_status")));
		order.setOrder_name(request.getParameter("order_name"));
		order.setOrder_zipcode(request.getParameter("order_zipcode"));
		order.setOrder_address1(request.getParameter("order_address1"));
		order.setOrder_address2(request.getParameter("order_address2"));
		order.setMem_phone(request.getParameter("mem_phone"));
		order.setOrder_notice(request.getParameter("order_notice"));
		
		OrderDAO orderDao = OrderDAO.getInstance();
		orderDao.updateOrder(order);
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");// alert_singliView.do
		request.setAttribute("notice_url", request.getContextPath() 
				+ "/order/modifyForm.do?order_num=" + order.getOrder_num());
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}