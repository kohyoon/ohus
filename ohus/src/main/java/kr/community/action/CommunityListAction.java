package kr.community.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class CommunityListAction implements Action {
    @Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = 
				request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
	    String keyfield = request.getParameter("keyfield");
	    String keyword = request.getParameter("keyword");
	    String sort = request.getParameter("sort");

	    CommunityDAO dao = CommunityDAO.getInstance();
	    int count = dao.getBoardCount(keyfield, keyword);
	    // keyfield,keyword,currentPage,count,
	    // rowCount,pageCount,요청URL
	    PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "list.do");

	    List<CommunityVO> list = null;
	    if (count > 0) {
	        list = dao.getListBoard(page.getStartRow(), page.getEndRow(), keyfield, keyword, sort);
	    }

	    request.setAttribute("count", count);
	    request.setAttribute("list", list);
	    request.setAttribute("page", page.getPage());

	    // JSP 경로 반환
	    return "/WEB-INF/views/community/list.jsp";
	}

}

