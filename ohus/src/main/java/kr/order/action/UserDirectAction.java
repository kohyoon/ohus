package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class UserDirectAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "redirect/item/itemList.do";
		}
		
		request.setCharacterEncoding("utf-8");
		
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO item = new ItemVO();
		
		
		
		return null;
	}

}
