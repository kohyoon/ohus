package kr.inquiry.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryAnswerVO;

public class ModifyAnswerAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		int ans_num = Integer.parseInt(request.getParameter("ans_num"));
		
		InquiryDAO dao = InquiryDAO.getInstance();
		InquiryAnswerVO db_answer = dao.getAnswer(ans_num);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if(user_num == null) {
			mapAjax.put("result", "logout");
		} else if(user_num != null && user_num == db_answer.getMem_num()) {
			//로그인 한 회원번호와 작성자 회원번호가 일치 - 자바빈을 생성하고 전송된 데이터를 저장
			InquiryAnswerVO answer = new InquiryAnswerVO();
			answer.setAns_num(ans_num);
			answer.setAns_content(request.getParameter("ans_content"));
			dao.updateAnswer(answer);
			
			mapAjax.put("result", "success");
		} else {
			//불일치
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
