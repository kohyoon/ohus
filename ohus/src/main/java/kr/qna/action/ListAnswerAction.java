package kr.qna.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemAnswerVO;
import kr.util.PageUtil;

public class ListAnswerAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		ItemQnaDAO dao = ItemQnaDAO.getInstance();
		int count = dao.getAnswerCount(qna_num);
		
		int rowCount = 10;
		
		PageUtil page = new PageUtil(Integer.parseInt(pageNum), count, rowCount);
		
		List<ItemAnswerVO> list = null;
		if(count > 0) {
			list = dao.getListAnswer(page.getStartRow(), page.getEndRow(), qna_num);
		}else {
			list = Collections.emptyList();
		}
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		
		mapAjax.put("user_num", user_num);
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
