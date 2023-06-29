
package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.market.dao.MarketDAO;
import kr.market.vo.MarketVO;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
//[프로필]
//프로필 사진 수정 -- member
//커뮤니티에 올린 사진+스크랩한 글 --commnunity
//상추글 --market
//문의내용 --inquiry
public class MyPageAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//로그인 여부 + 회원 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";			
		}
		if(user_auth !=2) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		
		//---------------------------------------------------------
		//회원 프로필 사진 수정 
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(user_num); //회원 상세정보
		
		
		//---------------------------------------------------------
		//내가 커뮤니티에 올린 사진 + 좋아요 누른 글
		
		 CommunityDAO commDao = CommunityDAO.getInstance();
		 
		 //좋아요 누른 글
		 List<CommunityVO> commList = commDao.getListBoardFav(1,5,user_num);
		
		//---------------------------------------------------------
		//내가 작성한 상추글
		MarketDAO marketDao = MarketDAO.getInstance();
		
		List<MarketVO> marketList = marketDao.getListMyMarket(1,5,user_num);
		 
		 
		//---------------------------------------------------------
		//내가 작성한 문의내용
		InquiryDAO inquiryDao = InquiryDAO.getInstance();
		List<InquiryVO> inquiryList = inquiryDao.getListInquiryByMem_Num(1, 5, user_num);
		

				
		//---------------------------------------------------------		
		// 페이지 정보 request 객체에 추가
		//request.setAttribute("page", page);
		 request.setAttribute("member", member); 
		 request.setAttribute("commList", commList);
		 request.setAttribute("marketList", marketList);
		 request.setAttribute("inquiryList", inquiryList);
		 
		
		
		
		
		//JSP 경로 반환
		return "/WEB-INF/views/member/myPage.jsp";
	}

}
