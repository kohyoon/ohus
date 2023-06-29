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

public class WriteAnswerAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 안된 경우
			mapAjax.put("result", "logout");
		} else {
			Integer user_auth = (Integer)session.getAttribute("user_auth");
			if(user_auth < 9) { //관리자 아닌 경우
				mapAjax.put("result", "wrongAccess");
			} else {
				//전송된 데이터 인코딩 처리
				request.setCharacterEncoding("utf-8");

				ItemAnswerVO answer = new ItemAnswerVO();
				answer.setMem_num(user_num); //답변 작성자 회원번호
				answer.setA_content(request.getParameter("a_content"));
				answer.setQna_num(Integer.parseInt(request.getParameter("qna_num")));

				ItemQnaDAO dao = ItemQnaDAO.getInstance();
				dao.insertQnaAnswer(answer);
				
				if(dao.getAnswerCount(Integer.parseInt(request.getParameter("qna_num"))) > 0){
					dao.setStatusDone(Integer.parseInt(request.getParameter("qna_num")));
				} else {
					dao.setStatusNone(Integer.parseInt(request.getParameter("qna_num")));
				}

				mapAjax.put("result", "success");
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
