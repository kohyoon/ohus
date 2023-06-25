package kr.community.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;
import kr.util.StringUtil;


public class CommunityDetailAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글번호
		int cboard_num = Integer.parseInt(
						   request.getParameter(
								     "cboard_num"));
		CommunityDAO dao = CommunityDAO.getInstance();
		//조회수 증가
		dao.updateReadcount(cboard_num);
		
		CommunityVO board = dao.getBoard(cboard_num);
		
		//HTML 태그를 허용하지 않음
		board.setCboard_title(
				StringUtil.useNoHtml(
						   board.getCboard_title()));
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		board.setCboard_content(
				StringUtil.useBrNoHtml(
						  board.getCboard_content()));
		
		request.setAttribute("board", board);
		
		return "/WEB-INF/views/community/detail.jsp";
	}

}

