package kr.event.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.event.dao.EventDAO;
import kr.event.vo.EventReplyVO;
import kr.event.vo.EventVO;

//관리자가 종료된 이벤트에 대해 추첨을 해준다 - 이벤트 목록에 버튼있음
//난수 발생
public class AdminPlayEventAction implements Action{   

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//관리자 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth !=9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//=================
		//이벤트 번호 저장
		int event_num = Integer.parseInt(request.getParameter("event_num"));
		
		EventDAO dao = EventDAO.getInstance();
		EventVO event = dao.getEvent(event_num); 
		EventReplyVO event_re = new EventReplyVO(); //이벤트 댓글
		
		//dao메서드를 통해 이벤트 별로 댓글을 단 회원 리스트를 받아온다
		List<EventReplyVO> list = dao.getListEventReply(event_num);
		int[] memberList = new int[list.size()];
		
		//회원번호를 받아와서 배열에 넣어주자
		for(int i =0; i<list.size(); i++) {
			event_re = list.get(i);
			memberList[i] = event_re.getMem_num();
		}
		//**memberList 제대로 불러옴!!
		//당첨자 배열 생성
		//지정한 당첨자 수만큼 크기 설정
		int[] winner = new int[event.getWinner_count()]; 
		
		Random r = new Random(); 
		
		for(int i=0; i<winner.length; i++) {  
			//난수 생성
			int number = r.nextInt(list.size()); 
			winner[i] = number;
		
			//중복체크
			for(int j=0; j<i; j++) {
				if(winner[i]==winner[j]) //추첨한 값이 같은 경우
				{
					i--;
					break;
				}
			} 
		}
		//**winner값 제대로 들어감!
		
		//여기서 문제
		//numberformatException : 숫자를 문자형으로 받아옴... 
		List<EventReplyVO> replyList = new ArrayList<EventReplyVO>();
		for(int i = 0; i<winner.length; i++) {
			 //winner에는 인덱스 값이 들어가있고 member의 인덱스에 해당하는 값을 넣어준다
			//event_winner에 당첨된 회원을 1로 update 해주는 메서드
			replyList.add(dao.updateEventwinner(memberList[winner[i]]));
		}
		
		
		request.setAttribute("replyList", replyList);
		
		
		 return "/WEB-INF/views/event/playEvent.jsp";
	}

}
