package kr.member.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventReplyVO;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemReviewVO;
import kr.item.vo.ItemVO;
//[나의 참여]
//내가 작성한 리뷰 - order
//응모내역 - event
public class MyPageAction2 implements Action{

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
		 request.setCharacterEncoding("utf-8");
		
		//---------------------------------------------------------	
		//내가 작성한 상품 리뷰
		ItemDAO itemDao = ItemDAO.getInstance();
		List<ItemReviewVO> itemList = itemDao.getListItemReview(1, 5, user_num);
		
		
		//---------------------------------------------------------	
		//내가 참여한 이벤트 응모 내역(댓글)
		EventDAO eventDao = EventDAO.getInstance();
        List<EventReplyVO> mylist = new ArrayList<EventReplyVO>();
        mylist = eventDao.getMyEventReply(1,5,user_num);

		//---------------------------------------------------------		
		// 페이지 정보 request 객체에 추가
        request.setAttribute("mylist", mylist);
        request.setAttribute("itemList", itemList);
		
		return "/WEB-INF/views/member/myPage2.jsp";
	}

}
