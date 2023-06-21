package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;

public class WriteInquiryAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//자바빈 생성
		InquiryVO inquiry = new InquiryVO();
		inquiry.setInq_title(request.getParameter("title"));
		inquiry.setInq_content(request.getParameter("content"));
		inquiry.setInq_ip(request.getRemoteAddr());
		inquiry.setMem_num(user_num);
		
		InquiryDAO dao = InquiryDAO.getInstance();
		dao.insertInquiry(inquiry);
		
		//JSP 경로 반환
		return "/WEB-INF/views/inquiry/writeInquiry.jsp";
	}
	
}
