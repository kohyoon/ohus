package kr.qna.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemAnswerVO;

public class ModifyAnswerAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		int a_num = Integer.parseInt(request.getParameter("a_num"));
		
		ItemQnaDAO dao = ItemQnaDAO.getInstance();
		ItemAnswerVO db_answer = dao.getAnswer(a_num);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if(user_num == null) {
			mapAjax.put("result", "logout");
		} else if(user_num != null && user_num == db_answer.getMem_num()) {
			ItemAnswerVO answer = new ItemAnswerVO();
			answer.setA_num(a_num);
			answer.setA_content(request.getParameter("a_content"));
			dao.updateAnswer(answer);
			
			mapAjax.put("result", "success");
		}else {
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper =  new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
