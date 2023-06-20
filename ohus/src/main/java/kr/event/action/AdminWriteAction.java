package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;


import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.util.FileUtil;

public class AdminWriteAction implements Action{
	
	//글쓰기-로그인 + 관리자의 경우 가능하다
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		Integer user_num = (Integer)session.getAttribute("user_num"); 
		
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		if(user_auth !=9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		 
		//FileUtil에 인코딩 처리가 되어있기 때문에 따로 처리하지 않음
		
		MultipartRequest multi = FileUtil.createFile(request);

		EventVO event = new EventVO();
		event.setEvent_title(multi.getParameter("event_title"));
		event.setEvent_photo(multi.getFilesystemName("event_photo")); //파일 이름은 getFilesystemName 메서드로 가져온다
		event.setEvent_content(multi.getParameter("event_content"));
		event.setEvent_start(multi.getParameter("event_start")); 
		event.setEvent_end(multi.getParameter("event_end"));
		event.setWinner_count(Integer.parseInt(multi.getParameter("winner_count")));
		event.setMem_num(user_num); 
		
		EventDAO dao = EventDAO.getInstance();
		dao.insertEvent(event);
		
		return "/WEB-INF/views/event/admin_write.jsp";
	}

}
