package kr.qna.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class WriteQnaFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		ItemDAO itemDao = ItemDAO.getInstance();
		int count = itemDao.getItemCount(null, null, 0, null);
		List<ItemVO> list = itemDao.getListItem(1, count, null, null, 0, null);
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		request.setAttribute("list", list);
		request.setAttribute("item_num", item_num);
				
		return "/WEB-INF/views/qna/writeQnaForm.jsp";
	}

}