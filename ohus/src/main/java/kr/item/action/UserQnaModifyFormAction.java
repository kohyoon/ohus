package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemQnaVO;
import kr.util.StringUtil;

public class UserQnaModifyFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		ItemDAO dao = ItemDAO.getInstance();
		ItemQnaVO qna = dao.getItemQna(qna_num);
		
		//로그인 한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != qna.getMem_num()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		qna.setQna_title(StringUtil.parseQuot(qna.getQna_title()));
		
		request.setAttribute("qna", qna);
		
		return "/WEB-INF/views/item/alert_singleView.jsp";
	}

}
