package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemQnaVO;

public class UserQnaWriteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		ItemQnaVO qna = new ItemQnaVO();
		qna.setQna_title(request.getParameter("qna_title"));
		qna.setQna_content(request.getParameter("qna_content"));
		qna.setQna_category(Integer.parseInt(request.getParameter("qna_category")));
		qna.setQna_ip(request.getRemoteAddr());
		qna.setMem_num(user_num);
		qna.setItem_num(item_num);
		
		ItemDAO dao = ItemDAO.getInstance();
		dao.insertItemQna(qna);
		
		request.setAttribute("notice_msg", "문의가 등록되었습니다.");
		request.setAttribute("notice_url", request.getContextPath() + "/item/detail.do?item_num=" + item_num);
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}
	
}
