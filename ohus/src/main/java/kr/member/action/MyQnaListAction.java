package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemQnaVO;

public class MyQnaListAction implements Action {

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
		//내가 작성한 일반문의
		InquiryDAO inquiryDao = InquiryDAO.getInstance();
		List<InquiryVO> inquiryList = inquiryDao.getListInquiryByMem_Num(1, 5, user_num);
		
		//---------------------------------------------------------
		//내가 작성한 상품문의
		ItemQnaDAO qnaDao = ItemQnaDAO.getInstance();
		List<ItemQnaVO> qnaList = qnaDao.getListQnaByMem_num(1, 5, user_num);
		
		//---------------------------------------------------------		
		// 페이지 정보 request 객체에 추가
		 request.setAttribute("inquiryList", inquiryList);
		 request.setAttribute("qnaList", qnaList);
		
		//JSP 경로 반환
		return "/WEB-INF/views/member/myQnaList.jsp";
	}

}
