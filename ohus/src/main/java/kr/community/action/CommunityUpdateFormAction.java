package kr.community.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;
import kr.util.StringUtil;


public class CommunityUpdateFormAction implements Action{
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = 
				request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute(
				                    "user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int cboard_num = 
		     Integer.parseInt(
				  request.getParameter("cboard_num"));
		CommunityDAO dao = CommunityDAO.getInstance();
		CommunityVO board = dao.getBoard(cboard_num);
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//큰 따옴표 처리
		//(수정폼의 input태그에서 오동작)
		board.setCboard_title(
				StringUtil.parseQuot(
						  board.getCboard_title()));		
		
		//로그인이 되어 있고 로그인한 회원번호와
		//작성자 회원번호 일치
		request.setAttribute("board", board);
		
		return "/WEB-INF/views/community/updateForm.jsp";
	}

}

