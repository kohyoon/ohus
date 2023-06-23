package kr.community.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class CommunityWriteAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = 
				      request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute(
						            "user_num");
		if(user_num==null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		MultipartRequest multi = 
				FileUtil.createFile(request);
		//자바빈(VO) 생성
		CommunityVO board = new CommunityVO();
		board.setCboard_category(Integer.parseInt(
				multi.getParameter("cboard_category")));
		board.setCboard_title(
				multi.getParameter("cboard_title"));
		board.setCboard_content(
			  multi.getParameter("cboard_content"));
		board.setCboard_photo1(
				multi.getFilesystemName(
						        "cboard_photo1"));
		board.setCboard_photo2(
				multi.getFilesystemName(
						        "cboard_photo2"));
		board.setCboard_ip(request.getRemoteAddr());
		board.setMem_num(user_num);//작성자(회원번호)
		
		CommunityDAO dao = CommunityDAO.getInstance();
		dao.insertBoard(board);
		//JSP 경로 반환
		return "/WEB-INF/views/community/write.jsp";
	}

}
