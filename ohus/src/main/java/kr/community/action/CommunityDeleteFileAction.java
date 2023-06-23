package kr.community.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;
import kr.util.FileUtil;


public class CommunityDeleteFileAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax =
				new HashMap<String,String>();
		
		HttpSession session = 
				       request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute(
						            "user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			int cboard_num = 
					Integer.parseInt(
						request.getParameter(
								 "cboard_num"));
			CommunityDAO dao = CommunityDAO.getInstance();
			CommunityVO db_board = 
					dao.getBoard(cboard_num);
			//로그인한 회원번호와 작성자 회원번호
			//일치 여부 체크
			if(user_num!=db_board.getMem_num()) {
				//로그인한 회원번호와 작성자 회원번호 불일치
				mapAjax.put("result", "wrongAccess");
			}else {
				//파일 정보 삭제
				dao.deleteFile(cboard_num);
				//파일 삭제
				FileUtil.removeFile(request, 
						    db_board.getCboard_photo1());
				FileUtil.removeFile(request, 
					    db_board.getCboard_photo2());
				mapAjax.put("result", "success");
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = 
				mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
