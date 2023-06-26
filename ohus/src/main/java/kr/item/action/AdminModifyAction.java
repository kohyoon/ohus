package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.FileUtil;

public class AdminModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//미로그인
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) {//관리자가 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		//관리자로 로그인한 경우
		MultipartRequest multi = FileUtil.createFile(request);
		
		int item_num = Integer.parseInt(multi.getParameter("item_num"));
		
		String item_photo1 = multi.getFilesystemName("item_photo1");
		String item_photo2 = multi.getFilesystemName("item_photo2");
		String item_photo3 = multi.getFilesystemName("item_photo3");
		
		//DB에 저장된 정보를 읽어옴
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO db_item = dao.getItem(item_num);
		
		//전송된 데이터를 자바빈에 저장
		ItemVO item = new ItemVO();
		item.setItem_num(item_num);
		item.setItem_name(multi.getParameter("item_name"));
		item.setItem_category(Integer.parseInt(multi.getParameter("item_category")));
		item.setItem_price(Integer.parseInt(multi.getParameter("item_price")));
		item.setItem_content(multi.getParameter("item_content"));
		item.setItem_stock(Integer.parseInt(multi.getParameter("item_stock")));
		item.setItem_origin(multi.getParameter("item_origin"));
		item.setItem_photo1(item_photo1);
		item.setItem_photo2(item_photo2);
		item.setItem_photo3(item_photo3);
		item.setItem_status(Integer.parseInt(multi.getParameter("item_status")));
		
		dao.updateItem(item);
		
		if(item_photo1 != null) FileUtil.removeFile(request, db_item.getItem_photo1());
		if(item_photo2 != null) FileUtil.removeFile(request, db_item.getItem_photo2());
		if(item_photo3 != null) FileUtil.removeFile(request, db_item.getItem_photo3());
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath() + "/item/modifyForm.do?item_num=" + item_num);
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
