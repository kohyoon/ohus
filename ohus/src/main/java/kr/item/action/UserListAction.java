package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.PageUtil;

public class UserListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//아이템카테고리
		String item_category = request.getParameter("item_category");
		if(item_category==null) item_category = "";
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
				
		String keyfield = "1";
		String keyword = request.getParameter("keyword");
		
		//전체 상품 데이터 처리
		ItemDAO itemDao = ItemDAO.getInstance();
		//Status 0이면 미표시(1), 표시(2) 모든 개수 체크
		int count = itemDao.getItemCount(keyfield, keyword, 1, item_category);
		
		//페이지 처리
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 5, 10, "userList.do", "&item_category="+item_category);
		
		List<ItemVO> itemList = null;
		if(count > 0) {
			itemList = itemDao.getListItem(page.getStartRow(), page.getEndRow(), keyfield, keyword, 1, item_category);//맨 뒤 1 : status(표시 상품)
		}
		//List<ItemVO> //
		request.setAttribute("itemList", itemList);
		request.setAttribute("count", count);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/item/user_list.jsp";
	}

}
