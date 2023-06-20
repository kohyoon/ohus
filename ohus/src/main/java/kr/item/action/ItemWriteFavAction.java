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

public class ItemWriteFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//미로그인
			mapAjax.put("result", "logout");
		}else {//로그인
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			int item_num = Integer.parseInt(request.getParameter("item_num"));
			ItemDAO dao = ItemDAO.getInstance();
			
			ItemFavVO favVO = new ItemFavVO();
			favVO.setItem_num(item_num);
			favVO.setMem_num(user_num);
			
			//좋아요 등록 여부 확인
			ItemFavVO db_fav = dao.selectFav(favVO);
			if(db_fav != null) {//좋아요 등록 되어 있음
				//좋아요 삭제 처리
				dao.deleteFav(db_fav.getFav_num());
				mapAjax.put("result", "success");
				mapAjax.put("status", "noFav");
				mapAjax.put("count", dao.selectFavCount(item_num));
			}else {//좋아요 등록X
				//좋아요 등록 처리
				dao.insertFav(favVO);
				mapAjax.put("result", "success");
				mapAjax.put("status", "yesFav");
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
