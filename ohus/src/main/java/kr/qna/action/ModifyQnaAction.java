package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemQnaVO;

public class ModifyQnaAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		//로그인 된 경우
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		ItemQnaDAO dao = ItemQnaDAO.getInstance();
		
		//수정 전 데이터 반환
		ItemQnaVO db_qna = dao.getQna(qna_num);
		
		//로그인 한 회원번호와 작성자 회원번호 일치 여부 확인
		if(user_num != db_qna.getMem_num()) {
			//불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		//일치
		ItemQnaVO qna = new ItemQnaVO();
		qna.setQna_num(qna_num);
		qna.setQna_title(request.getParameter("qna_title"));
		qna.setQna_content(request.getParameter("qna_content"));
		qna.setQna_category(Integer.parseInt(request.getParameter("qna_category")));
		qna.setQna_ip(request.getRemoteAddr());
		qna.setItem_num(Integer.parseInt(request.getParameter("item_num")));
		
		dao.updateQna(qna);
		
		return "redirect:/qna/detailQna.do?qna_num=" + qna_num;
	}

}
