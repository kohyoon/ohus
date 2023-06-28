package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;
//[나의 쇼핑]
//주문내역 조회, 찜한 상품 -- order, item
//공지사항, 고객센터 --inquiry
public class MyPageAction1 implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 + 회원 체크
			HttpSession session = request.getSession();
			Integer user_num = (Integer)session.getAttribute("user_num");
			Integer user_auth = (Integer)session.getAttribute("user_auth");
			
			if(user_num==null) {//로그인이 되지 않은 경우
				return "redirect:/member/loginForm.do";			
			}
			if(user_auth !=2) {
				return "/WEB-INF/views/common/notice.jsp";
			}
			
			//---------------------------------------------------------
			//나의 주문내역 조회
			OrderDAO orderDao = OrderDAO.getInstance();
			List<OrderVO> orderList = orderDao.getListOrderByMem_num(1, 5, null, null, user_num);
			
			//---------------------------------------------------------
			//내가 찜한 상품
			
			ItemDAO itemDao = ItemDAO.getInstance();
			List<ItemVO> itemList = itemDao.getListItemFav(1, 5, user_num);
			
			
			//---------------------------------------------------------
			//공지사항 및 고객센터
			
			
			
			//---------------------------------------------------------		
			// 페이지 정보 request 객체에 추가
			request.setAttribute("itemList", itemList);
			request.setAttribute("orderList", orderList);
			
	
		return "/WEB-INF/views/member/myPage1.jsp";
	}

}
