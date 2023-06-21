package kr.notice.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.FileUtil;

public class DeleteFileAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		} else { //로그인 된 경우
			int notice_num = Integer.parseInt(request.getParameter("notice_num"));
			NoticeDAO dao = NoticeDAO.getInstance();
			NoticeVO db_notice = dao.getNotice(notice_num);
			
			Integer user_auth = (Integer)session.getAttribute("user_auth");
			if(user_auth < 9) { //관리자가 아닌 경우
				mapAjax.put("result", "wrongAccess");
			}
			
			//로그인 한 회원번호와 작성자 회원번호 일치 여부 체크
			if(user_num != db_notice.getMem_num()) {
				//불일치
				mapAjax.put("result", "wrongAccess");
			} else { //일치
				//db에서 파일 정보 삭제
				dao.deleteNotice(notice_num);
				//파일 삭제
				FileUtil.removeFile(request, db_notice.getNotice_filename());
				mapAjax.put("result", "success");
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
