package kr.community.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityReportVO;
import kr.controller.Action;


public class CommunityReportReplyAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	Map<String,String> mapAjax = 
				new HashMap<String,String>();
    	
    	HttpSession session = request.getSession();
    	
    	Integer user_num = 
				(Integer)session.getAttribute(
						              "user_num");
		if(user_num==null) {//로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			CommunityReportVO report = 
			         new CommunityReportVO();
			
			report.setDec_category(Integer.parseInt(request.getParameter("dec_category")));
			report.setMem_num(user_num);
			report.setRe_num(Integer.parseInt(request.getParameter("re_num")));
   
			CommunityDAO dao = CommunityDAO.getInstance();
			dao.reportReply(report);
			
			mapAjax.put("result","success");
		}
		//JSON 데이터 생성
				ObjectMapper mapper = new ObjectMapper();
				String ajaxData = 
						mapper.writeValueAsString(mapAjax);
				
				request.setAttribute("ajaxData", ajaxData);
				//JSP 경로 반환
				return "/WEB-INF/views/common/ajax_view.jsp";
			}
}
