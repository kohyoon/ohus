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
import kr.util.PageUtil;

public class MyPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = 
			(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";			
		}
		
		
		//로그인이 된 경우
		MemberDAO dao = MemberDAO.getInstance();
		//회원정보
		MemberVO member = dao.getMember(user_num);
		
		/*
		 * BoardDAO boardDao = BoardDAO.getInstance(); //게시판 글을 마이 페이지에서 보여주기
		 * List<BoardVO> boardList = boardDao.getListBoardFav(1,5, user_num);
		 * 
		 * request.setAttribute("member", member); 
		 * request.setAttribute("boardList", boardList);
		 */
		
		//상품 스크랩
		ItemDAO itemDao = ItemDAO.getInstance();
		List<ItemVO> itemList = itemDao.getListItemFav(1, 5, user_num);
		
		// 주문 정보
		OrderDAO orderDao = OrderDAO.getInstance();
		List<OrderVO> orderList = orderDao.getListOrderByMem_num(1, 5, null, null, user_num);
						
		
		//분리한 마이페이지 처리
		String page = request.getParameter("page");

				
				
			    // 페이지 정보를 request 객체에 추가
		request.setAttribute("page", page);
		request.setAttribute("member", member);
		request.setAttribute("itemList", itemList);
		request.setAttribute("orderList", orderList);
		
		
		
		

	
		//JSP 경로 반환
		return "/WEB-INF/views/member/myPage.jsp";
	}

}
