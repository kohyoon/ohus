package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemQnaVO;

public class UserQnaModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirecet:/member/loginForm.do";
		}
		
		//전송받은 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		ItemDAO dao = ItemDAO.getInstance();
		ItemQnaVO db_qna = dao.getItemQna(qna_num);
		
		//문의글 작성자와 로그인 한 회원이 일치하지 않는 경우
		if(user_num != db_qna.getMem_num()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//일치
		ItemQnaVO qna = new ItemQnaVO();
		qna.setQna_title(request.getParameter("qna_title"));
		qna.setQna_content(request.getParameter("qna_content"));
		qna.setQna_category(Integer.parseInt(request.getParameter("qna_category")));
		qna.setQna_ip(request.getRemoteAddr());
		
		dao.updateItemQna(qna);
		
		request.setAttribute("notice_msg", "문의가 수정었습니다.");
		request.setAttribute("notice_url", request.getContextPath() + "/item/detail.do?item_num=" + item_num);
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
