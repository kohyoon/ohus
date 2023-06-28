package kr.qna.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemQnaVO;
import kr.util.StringUtil;

public class ModifyQnaFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		ItemQnaDAO dao = ItemQnaDAO.getInstance();
		ItemQnaVO qna = dao.getQna(qna_num);
		
		ItemDAO itemDao = ItemDAO.getInstance();
		int count = itemDao.getItemCount(null, null, 0, null);
		List<ItemVO> list = itemDao.getListItem(1, count, null, null, 0, null);
		
		request.setAttribute("list", list);
		
		
		//로그인 한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != qna.getMem_num()) {
			//불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//큰 따옴표 처리(수정폼의 input태그에서 오동작)
		qna.setQna_title(StringUtil.parseQuot(qna.getQna_title()));
		
		//로그인 되어 있고 로그인 한 회원번호와 작성자 회원번호가 일치
		request.setAttribute("qna", qna);
		
		return "/WEB-INF/views/qna/modifyQnaForm.jsp";
	}

}
