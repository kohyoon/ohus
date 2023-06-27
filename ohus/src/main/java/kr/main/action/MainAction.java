package kr.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
public class MainAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 당첨자 알림창을 띄우기 위해 속성 설정
        Boolean isWinner = (Boolean) request.getSession().getAttribute("isWinner");

        // 이벤트 당첨자인 경우 알림창을 띄웁니다.
        if (isWinner != null && isWinner) {
            request.setAttribute("showWinnerAlert", true);
            // 당첨자 알림창을 한 번만 띄우고 세션에서 삭제합니다.
            request.getSession().removeAttribute("isWinner");
        }

        // JSP 경로 반환
        return "/WEB-INF/views/main/main.jsp";
    }
}

