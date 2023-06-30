package kr.item.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemReviewVO;
import kr.util.FileUtil;

public class UserDeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//미로그인
			mapAjax.put("result", "logout");
		}else {//로그인
			int review_num = Integer.parseInt(request.getParameter("review_num"));
			ItemDAO dao = ItemDAO.getInstance();
			ItemReviewVO db_review = dao.getReviewDetail(review_num);
			if(user_num != db_review.getMem_num()) {
				mapAjax.put("result", "wrongAccess");
			}else{
				dao.deleteFile(review_num);
				//파일삭제
				FileUtil.removeFile(request, db_review.getReview_photo());
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
