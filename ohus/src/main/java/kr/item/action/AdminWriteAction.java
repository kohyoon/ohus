package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.FileUtil;

public class AdminWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//미로그인
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) {//관리자 계정 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//관리자인 경우
		MultipartRequest multi = FileUtil.createFile(request);
				
		ItemVO item = new ItemVO();
		item.setItem_name(multi.getParameter("item_name"));
		item.setItem_category(Integer.parseInt(multi.getParameter("item_category")));
		item.setItem_price(Integer.parseInt(multi.getParameter("item_price")));
		item.setItem_content(multi.getParameter("item_content"));
		item.setItem_stock(Integer.parseInt(multi.getParameter("item_stock")));
		item.setItem_origin(multi.getParameter("item_origin"));
		item.setItem_photo1(multi.getFilesystemName("item_photo1"));
		item.setItem_photo2(multi.getFilesystemName("item_photo2"));
		item.setItem_photo3(multi.getFilesystemName("item_photo3"));
		item.setItem_status(Integer.parseInt(multi.getParameter("item_status")));
		
		ItemDAO dao = ItemDAO.getInstance();
		dao.insertItem(item);
				
		//refresh 정보를 응답헤더에 추가
		response.addHeader("Refresh", "2;url=list.do");
		request.setAttribute("accessMsg", "등록 완료");
		request.setAttribute("accessUrl", "list.do");
				
		//JSP 경로 반환
		return "/WEB-INF/views/common/notice.jsp";
	}

}
