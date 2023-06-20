package kr.event.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventReplyVO;
import kr.util.PageUtil;

public class ListReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		
		if(pageNum==null) {
			pageNum="1";
		}
		
		int event_num = Integer.parseInt(request.getParameter("event_num"));
		EventDAO dao = EventDAO.getInstance();
		
		//count 읽어오기
		int count = dao.getReplyEventCount(event_num); //2. 댓글 개수 가져옴
		
		//rowCount 변수 설정하기--값은 본인이 설정~
		int rowCount = 10;
		
		//pageUtil 사용 - 연산만 하기 위해서 
		//ajax 방식으로 목록을 표시하기 때문에 PageUtil은 페이지 수를 표시할 목적이 아닌,
		//목록 데이터의 페이지 처리를 위해 rownum 번호를 구하는 목적(연산)으로 사용함
		PageUtil page = new PageUtil(Integer.parseInt(pageNum), count, 10); //인자 순서대로 currentpage, count, rowCount
		 
		List<EventReplyVO> list = null;
		
		if(count>0) { //댓글 개수가 0보다 크면 목록 함수 호출(DAO)
			list = dao.getListReplyEvent(page.getStartRow(), page.getEndRow(), event_num);
		}
		else {
			list = Collections.emptyList(); //리스트가 비어있게 됨. 즉, 댓글 개수가 0보다 작거나 같으면. ajax 방식이므로 null이 아닌 비어있게 처리하는 것
		}
		
		//세션 정보 가져오기!
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		
		//로그인 한 사람이 작성자인지 체크하기 위해 '전송'
		mapAjax.put("user_num", user_num); //위에서 가져온 user_num 정보
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		//데이터 넣어주기
		request.setAttribute("ajaxData", ajaxData);
		
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
