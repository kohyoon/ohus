package writeQnaForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
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
		MultipartRequest multi = FileUtil.createFile(request);
		//자바빈 생성
		QnaVO qna = new QnaVO();
		qna.setQna_title(multi.getParameter("qna_title"));
		qna.setQna_content(multi.getParameter("qna_content"));
		qna.setQna_ip(request.getRemoteAddr());
		qna.setQna_filename(multi.getFilesystemName("filename"));
		qna.setMem_num(user_num);
		qna.setDetail_num(Integer.parseInt(multi.getParameter("detail_num")));
		
		QnaDAO dao = QnaDAO.getInstance();
		dao.insertQna(qna);
		
		return "/WEB-INF/views/qna/writeQna.jsp";
	}

}
