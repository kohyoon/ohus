package kr.cart.action;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		} else { // 로그인 된 경우
			request.setCharacterEncoding("utf-8");
			
			CartVO cart = new CartVO();
			cart.setItem_num(Integer.parseInt(request.getParameter("item_num")));
			cart.setOrder_quantity(Integer.parseInt(request.getParameter("order_quantity")));
			cart.setMem_num(user_num);
			
			CartDAO dao = CartDAO.getInstance();
			CartVO db_cart = dao.getCart(cart);
			if(db_cart == null) { // 동일상품이 없을 경우
				dao.insertCart(cart);
				mapAjax.put("result", "success");
			} else { // 동일 상품이 있을 경우
				// 재고수를 구하기 위해서 ItemDAO를 호출
				ItemDAO itemDao = ItemDAO.getInstance();
				ItemVO item = itemDao.getItem(db_cart.getItem_num());
				// 구매수량 합산(기존 방바구니에 저장된 구매수량 + 새로 입력한 구매 수량)
				int order_quantity = db_cart.getOrder_quantity() + cart.getOrder_quantity();
				
				if(item.getItem_stock() < order_quantity) {
					// 상품 재고 수량보다 장바구니에 다음 구매 수량이 더 많음
					mapAjax.put("result", "over_quantity");
				} else {
					cart.setOrder_quantity(order_quantity);
					dao.updateCartByItem_num(cart);
					mapAjax.put("result", "success");
				}
			}
		}
		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}















