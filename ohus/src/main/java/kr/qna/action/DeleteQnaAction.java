package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemQnaVO;

public class DeleteQnaAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 안된 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		ItemQnaDAO dao = ItemQnaDAO.getInstance();
		ItemQnaVO db_qna = dao.getQna(qna_num);
		//로그인 한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != db_qna.getMem_num()) {
			//불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		//일치
		dao.deleteQna(qna_num); //삭제
		
		return "redirect:/qna/qnaList.do";
	}

}
