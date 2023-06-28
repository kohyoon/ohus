/*package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemQnaVO;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.StringUtil;

public class DetailQnaAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//상품문의번호
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		ItemDAO dao = ItemDAO.getInstance();
		ItemQnaVO qna = dao.getItemQna(qna_num);
		
		//HTML 태그를 허용하지 않음
		qna.setQna_title(StringUtil.useNoHtml(qna.getQna_title()));
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		qna.setQna_content(StringUtil.useBrNoHtml(qna.getQna_content()));
		
		request.setAttribute("qna", qna);
		
		return "/WEB-INF/views/qna/detailQna.jsp";
	}
	
}*/
