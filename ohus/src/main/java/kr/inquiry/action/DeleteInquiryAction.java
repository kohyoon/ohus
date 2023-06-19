package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;

public class DeleteInquiryAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 하지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		int inq_num = Integer.parseInt(request.getParameter("inq_num"));
		InquiryDAO dao = InquiryDAO.getInstance();
		InquiryVO db_inquiry = dao.getInquiryDetail(inq_num);
		//로그인 한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != db_inquiry.getMem_num()) {
			//불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		//일치
		dao.deleteInquiry(inq_num); //글 삭제
				
		return "redirect:/inquiry/listInquiry.do";
	}

}
