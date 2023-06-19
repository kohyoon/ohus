package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;

public class ModifyInquiryAction implements Action {

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
		
		//로그인 된 경우
		int inq_num = Integer.parseInt(request.getParameter("inq_num"));
		InquiryDAO dao = InquiryDAO.getInstance();
		
		//수정 전 데이터 반환
		InquiryVO db_inquiry = dao.getInquiryDetail(inq_num);
		//로그인 한 회원과 작성자 회원번호 일치 여부 확인
		if(user_num != db_inquiry.getMem_num()) {
			//불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		//일치
		InquiryVO inquiry = new InquiryVO();
		inquiry.setInq_num(inq_num);
		inquiry.setInq_title(request.getParameter("title"));
		inquiry.setInq_content(request.getParameter("content"));
		inquiry.setInq_category(Integer.parseInt(request.getParameter("category")));
		inquiry.setInq_ip(request.getRemoteAddr());
		
		dao.updateInquiry(inquiry);
		
		return "redirect:/inquiry/detailInquiry.do?inq_num=" + inq_num;
	}

}
