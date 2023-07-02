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
import kr.event.vo.EventWinnerVO;

//이벤트 실행!
public class AdminPlayEventAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 관리자 확인
        HttpSession session = request.getSession();
        Integer user_num = (Integer) session.getAttribute("user_num");
        Integer user_auth = (Integer) session.getAttribute("user_auth");

        if (user_num == null) {
            return "redirect:/member/loginForm.do";
        }
        if (user_auth != 9) {
            return "/WEB-INF/views/common/notice.jsp";
        }

        // 이벤트 번호 저장
        int event_num = Integer.parseInt(request.getParameter("event_num"));

        EventDAO dao = EventDAO.getInstance();
        EventVO event = dao.getEvent(event_num);
        EventReplyVO event_re = new EventReplyVO();

        List<EventReplyVO> list = dao.getListEventReply(event_num);
        int[] memberList = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            event_re = list.get(i);
            memberList[i] = event_re.getMem_num();
        }

        int[] winner = new int[event.getWinner_count()];
        Random r = new Random();
      
        
        for (int i = 0; i < winner.length; i++) {
            int number = r.nextInt(list.size());
            winner[i] = number;

            for (int j = 0; j < i; j++) {
                if (winner[i] == winner[j]) {
                    i--;
                    break;
                }
            }
        }

     // 기존의 중복 제거 로직을 제거하고 새로운 추첨 로직을 적용
        //
        //
        List<EventReplyVO> replyList = new ArrayList<EventReplyVO>();

        for (int i = 0; i < winner.length; i++) {
        	//댓글 당첨자의 정보를 읽어오고 event_winner==1로 업데이트 해줌
            EventReplyVO reply = dao.selectEventwinner(event_num, memberList[winner[i]]);
            
            // 중복 확인 로직 추가
            boolean isWinnerExist = dao.checkEventWinner(event_num, reply.getRe_num());

            if (!isWinnerExist) {
                // 중복되지 않은 경우에만 당첨자로 추가
                EventWinnerVO win = new EventWinnerVO();
                win.setRe_num(reply.getRe_num());
                win.setEvent_num(reply.getEvent_num());
                win.setMem_num(reply.getMem_num());
                dao.insertEventWinner(win);
            }

            replyList.add(reply);
        }

        dao.updateCheckEvent(event_num); //추첨이 실행되면 event_check = 1 로 변경해주어 추첨 여부 확인
        
        //추첨결과
        List<EventWinnerVO> plz = dao.getListEventWin(event_num);
        
        request.setAttribute("plz", plz);
        request.setAttribute("replyList", replyList);
        request.setAttribute("event", event);

        return "/WEB-INF/views/event/playEvent.jsp";
    }
}
