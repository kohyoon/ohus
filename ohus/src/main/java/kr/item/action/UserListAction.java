package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class UserListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//신규 상품 데이터 처리
		ItemDAO itemDao = ItemDAO.getInstance();
		List<ItemVO> itemList = itemDao.getListItem(1, 16, null, null, 1);//맨 뒤 1 : status(표시 상품)
		request.setAttribute("itemList", itemList);
		
		return "/WEB-INF/views/item/user_list.jsp";
	}

}
