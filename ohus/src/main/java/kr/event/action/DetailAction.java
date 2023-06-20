package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventVO;
import kr.util.StringUtil;
//글 상세 보기 - 로그인 상관 없음! 제목 누르면 볼 수 있는 것 
//조회수 증가 처리
public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//글 번호를 가져옴
		int event_num = Integer.parseInt(request.getParameter("event_num"));
		
		EventDAO dao = EventDAO.getInstance();
		
		//조회수(hit) 증가 -DAO-updatereadcount 메서드 호출!
		dao.updateReadcount(event_num);
		
		EventVO event = dao.getEvent(event_num);
		
		//StringUtil 사용--------------
		
		//글을 작성 시 제목에 HTML 태그를 허용하지 않음
		event.setEvent_title(StringUtil.useNoHtml(event.getEvent_title()));
		
		//내용에 HTML 태그를 허용하지 않으면서 줄바꿈이 되도록
		event.setEvent_content(StringUtil.useBrNoHtml(event.getEvent_content()));
		
		
		
		//StringUtil 사용 끝-------------
		
		
		//값 저장
		request.setAttribute("event", event);
		
		// JSP 반환
		return "/WEB-INF/views/event/detail.jsp";
	}

}
