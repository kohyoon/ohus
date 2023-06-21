package kr.event.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;


import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.util.FileUtil;

//관리자 처리이므로 로그인 + 관리자를 확인해야한다
//로그인이 안되면 로그아웃 - 로그인 폼 이동
//로그인이 됐지만 관리자가 아니라면 wrongaccess


public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		//1. 로그인 x
		if(user_num==null) {
			mapAjax.put("result", "logout");
		}
		
		//2. 관리자 확인
		if(user_auth<9) {
			mapAjax.put("result", "wrongAccess");
		}
		
		//작업 시작
		int event_num = Integer.parseInt(request.getParameter("event_num"));
		EventDAO dao = EventDAO.getInstance();
		EventVO db_event = dao.getEvent(event_num);
		
		//1. 파일 정보를 삭제
		dao.deleteFile(event_num);
		//2. 파일 삭제
		FileUtil.removeFile(request, db_event.getEvent_photo());
		
		mapAjax.put("result","success");

		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
			
	}

}
