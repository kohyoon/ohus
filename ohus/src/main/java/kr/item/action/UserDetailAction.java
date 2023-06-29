package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemReviewVO;
import kr.item.vo.ItemVO;
import kr.qna.dao.ItemQnaDAO;
import kr.qna.vo.ItemQnaVO;
import kr.util.PageUtil;
import kr.util.StringUtil;

public class UserDetailAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      //상품 번호 반환
      int item_num = Integer.parseInt(request.getParameter("item_num"));
      
      ItemDAO dao = ItemDAO.getInstance();
      
      //상품 정보 가져오기
      ItemVO item = dao.getItem(item_num);
      //내용 줄바꿈 처리
      item.setItem_content(StringUtil.useBrHtml(item.getItem_content()));
      
      //조회수 증가
      dao.updateViewsCount(item_num);
      
      //리뷰 개수
      int reviewCount = dao.getReviewCount(item_num);
      
      double avgscore = dao.avgReview(item_num);
      
      //문의 개수
      ItemQnaDAO qnaDao = ItemQnaDAO.getInstance();
      int qnaCount = qnaDao.getQnaCountByItem_num(item_num);
      List<ItemQnaVO> qnaList = qnaDao.getListQna(1, 5, null, null);
      
      request.setAttribute("avgscore", avgscore);
      request.setAttribute("reviewCount", reviewCount);
      request.setAttribute("item", item);
      
      request.setAttribute("qnaCount", qnaCount);
      request.setAttribute("qnaList", qnaList);
      
      return "/WEB-INF/views/item/user_detail.jsp";
   }

}