package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.PageUtil;

public class AdminListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//미로그인
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) {//관리자 계정 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		//관리자로 로그인한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";//
				
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");//
		String item_category = request.getParameter("item_category");
		
		//목록 페이지 구현
		ItemDAO dao = ItemDAO.getInstance();//
		//Status 0이면 미표시(1), 표시(2) 모든 개수 체크
		int count = dao.getItemCount(keyfield, keyword, 0, item_category);//
				
		//페이지 처리
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 5, 10, "list.do");//
		
		//목록 데이터 호출
		List<ItemVO> list = null;//
		if(count > 0) {
			list = dao.getListItem(page.getStartRow(), page.getEndRow(), keyfield, keyword, 0, item_category);//0 : status
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);//
		request.setAttribute("page", page.getPage());
		//목록 페이지 구현
				
		return "/WEB-INF/views/item/admin_list.jsp";
	}

}
