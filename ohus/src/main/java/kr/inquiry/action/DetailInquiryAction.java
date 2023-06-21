package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.StringUtil;

public class DetailInquiryAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		request.setAttribute("user_auth", user_auth);
		
		//문의글 번호
		int inq_num = Integer.parseInt(request.getParameter("inq_num"));
		InquiryDAO dao = InquiryDAO.getInstance();
		
		InquiryVO inquiry = dao.getInquiryDetail(inq_num);
		
		//HTML 태그를 허용하지 않음
		inquiry.setInq_title(StringUtil.useNoHtml(inquiry.getInq_title()));
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		inquiry.setInq_content(StringUtil.useBrNoHtml(inquiry.getInq_content()));
		
		request.setAttribute("inquiry", inquiry);
		
		return "/WEB-INF/views/inquiry/detailInquiry.jsp";
	}

}
