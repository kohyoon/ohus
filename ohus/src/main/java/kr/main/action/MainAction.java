package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;
import kr.util.PageUtil;
public class MainAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	
    	
    	String cboard_category = request.getParameter("cboard_category");
        if (cboard_category == null)
            cboard_category = "";

        String pageNum = request.getParameter("pageNum");
        if (pageNum == null)
            pageNum = "1";

        String keyfield = "1";
        String keyword = request.getParameter("keyword");

        CommunityDAO dao = CommunityDAO.getInstance();
        int count = dao.getBoardCount(keyfield, keyword, cboard_category);

        PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 12, 10, "list.do",
                "&cboard_category=" + cboard_category);

        List<CommunityVO> list = null;
        if (count > 0) {
            list = dao.getListBoard(page.getStartRow(), page.getEndRow(), keyfield, keyword, cboard_category);
        }
        

        // 당첨자 알림창을 띄우기 위해 속성 설정
        Boolean isWinner = (Boolean) request.getSession().getAttribute("isWinner");

        // 이벤트 당첨자인 경우 알림창을 띄움
        if (isWinner != null && isWinner) {
            request.setAttribute("showWinnerAlert", true);
            // 당첨자 알림창을 한 번만 띄우고 세션에서 삭제
            request.getSession().removeAttribute("isWinner");
        }
        
    	
        request.setAttribute("count", count);
        request.setAttribute("list", list);
        request.setAttribute("page", page.getPage());
        request.setAttribute("isWinner", isWinner);

        // JSP 경로 반환
        return "/WEB-INF/views/main/main.jsp";
    }
}

