package kr.community.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;
import kr.util.FileUtil;


public class CommunityUpdateAction implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("user_num");
		if (user_num == null) { // 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		// 로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		int cboard_num = Integer.parseInt(multi.getParameter("cboard_num"));
		String photo1 = multi.getFilesystemName("cboard_photo1");
		String photo2 = multi.getFilesystemName("cboard_photo2");

		CommunityDAO dao = CommunityDAO.getInstance();
		// 수정 전 데이터 반환
		CommunityVO db_board = dao.getBoard(cboard_num);
		// 로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if (user_num != db_board.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			FileUtil.removeFile(request, photo1);
			FileUtil.removeFile(request, photo2);
			return "/WEB-INF/views/common/notice.jsp";
		}

		// 로그인한 회원번호와 작성자 회원번호가 일치
		CommunityVO board = new CommunityVO();
		board.setCboard_num(cboard_num);
		board.setCboard_title(multi.getParameter("cboard_title"));
		board.setCboard_content(multi.getParameter("cboard_content"));
		board.setCboard_ip(request.getRemoteAddr());
		board.setCboard_photo1(photo1);
		board.setCboard_photo2(photo2);

		dao.updateBoard(board);

		// 새 파일로 교체할 때 원래 파일 제거
		if (photo1 != null && !photo1.equals(db_board.getCboard_photo1())) {
			FileUtil.removeFile(request, db_board.getCboard_photo1());
		}
		if (photo2 != null && !photo2.equals(db_board.getCboard_photo2())) {
			FileUtil.removeFile(request, db_board.getCboard_photo2());
		}

		return "redirect:/community/detail.do?cboard_num=" + cboard_num;
	}
}
