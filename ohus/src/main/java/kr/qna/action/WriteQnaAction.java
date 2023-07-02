package kr.qna.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemQnaVO;
import kr.util.FileUtil;

public class WriteQnaAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		request.setCharacterEncoding("utf-8");
		//자바빈 생성
		ItemQnaVO qna = new ItemQnaVO();
		qna.setQna_title(request.getParameter("qna_title"));
		qna.setQna_content(request.getParameter("qna_content"));
		qna.setQna_category(Integer.parseInt(request.getParameter("qna_category")));
		qna.setQna_ip(request.getRemoteAddr());
		qna.setMem_num(user_num);
		qna.setItem_num(Integer.parseInt(request.getParameter("item_num")));
		
		ItemQnaDAO dao = ItemQnaDAO.getInstance();
		dao.insertQna(qna);
		
		return "/WEB-INF/views/qna/writeQna.jsp";
	}

}
