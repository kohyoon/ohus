package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.PageUtil;
//회원 관리
public class AdminPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		if(user_auth <9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		String pageNum = request.getParameter("pageNum");
		
		//검색 처리
		if(pageNum == null) { 
			pageNum = "1"; 
		}
		
		String keyfield =request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		 // count 읽어오기
        MemberDAO dao = MemberDAO.getInstance();
        int count = dao.getMemberCountByAdmin(keyfield, keyword);
        PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "memberList.do");

        List<MemberVO> list = null;

        if (count > 0) {
            list = dao.getListMemberByAdmin(page.getStartRow(), page.getEndRow(), keyfield, keyword);
            for (MemberVO member : list) {
                int mem_num = member.getMem_num();
                MemberVO reportsMember = dao.countReportsMember(mem_num);
                member.setReports(reportsMember.getReports());
            }
        }
        
        // JSP에 회원 정보 전달
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		//JSP 경로반환
		return "/WEB-INF/views/member/adminPage.jsp";
		
		
	}

}
