package kr.item.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemFavVO;

public class ItemGetFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//전송된 데이터 반환
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		ItemDAO dao = ItemDAO.getInstance();
		if(user_num == null) {//미로그인
			mapAjax.put("status", "noFav");
			mapAjax.put("count", dao.selectFavCount(item_num));
		}else {//로그인된 경우
			ItemFavVO itemFav = dao.selectFav(item_num, user_num);
			if(itemFav != null) {
				//로그인한 회원이 해당 상품을 스크랩했던 경우
				mapAjax.put("status", "yesFav");
				mapAjax.put("count", dao.selectFavCount(item_num));
			}else {
				//로그인한 회원이 해당 상품을 스크랩하지 않은 경우
				 mapAjax.put("status", "noFav");
	             mapAjax.put("count", dao.selectFavCount(item_num));
			}
		}
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
					
		request.setAttribute("ajaxData", ajaxData);
				
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
		
	}

}
