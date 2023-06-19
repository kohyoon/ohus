package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.StringUtil;

public class DetailInquiryAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
