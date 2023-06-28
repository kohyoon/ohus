/*package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.FileUtil;

public class ModifyQnaAction implements Action {

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
		int qna_num = Integer.parseInt(multi.getParameter("qna_num"));
		String filename = multi.getFilesystemName("qna_filename");
		QnaDAO dao = QnaDAO.getInstance();
		
		//수정 전 데이터 반환
		QnaVO db_qna = dao.getQna(qna_num);
		
		//로그인 한 회원번호와 작성자 회원번호 일치 여부 확인
		if(user_num != db_qna.getMem_num()) {
			//불일치
			FileUtil.removeFile(request, filename);
			return "/WEB-INF/views/common/notice.jsp";
		}
		//일치
		QnaVO qna = new QnaVO();
		qna.setQna_num(qna_num);
		qna.setQna_title(multi.getParameter("qna_title"));
		qna.setQna_content(multi.getParameter("qna_content"));
		qna.setQna_ip(request.getRemoteAddr());
		qna.setQna_filename(filename);
		qna.setOrder_num(Integer.parseInt(multi.getParameter("order_num")));
		
		dao.updateQna(qna);
		
		//새 파일로 교체할 때 원래 파일 제거
		if(filename != null) {
			FileUtil.removeFile(request, db_qna.getQna_filename());
		}
		
		return "redirect:/qna/detailQna.do?qna_num=" + qna_num;
	}

}*/
