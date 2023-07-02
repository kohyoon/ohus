package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemQnaVO;
import kr.util.StringUtil;

public class DetailQnaAction implements Action {

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
		
		//상품문의번호
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		ItemQnaDAO dao = ItemQnaDAO.getInstance();
		
		ItemQnaVO qna = dao.getQna(qna_num);
		
		//HTML 태그를 허용하지 않음
		qna.setQna_title(StringUtil.useNoHtml(qna.getQna_title()));
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		qna.setQna_content(StringUtil.useBrNoHtml(qna.getQna_content()));
		
		
		if(dao.getAnswerCount(Integer.parseInt(request.getParameter("qna_num"))) > 0){
			dao.setStatusDone(Integer.parseInt(request.getParameter("qna_num")));
		} else {
			dao.setStatusNone(Integer.parseInt(request.getParameter("qna_num")));
		}
		
		
		request.setAttribute("qna", qna);
		
		return "/WEB-INF/views/qna/detailQna.jsp";
	}
	
}
