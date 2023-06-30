package kr.event.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.event.vo.EventReplyVO;
import kr.event.vo.EventVO;
import kr.event.vo.EventWinnerVO;
import kr.market.vo.MarketVO;
import kr.member.vo.MemberVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class EventDAO {
	
	private static EventDAO instance = new EventDAO();

	public static EventDAO getInstance() {
		return instance; 
	}
 
	private EventDAO() {} 
	
	//----------------------------------
	//이벤트 등록(관리자)
	public void insertEvent(EventVO event) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {

			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO oevent(event_num, event_title, event_photo, "
					+ "event_content, event_start, event_end, winner_count, event_status, mem_num) "
					+ "VALUES(oevent_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, event.getEvent_title());
			pstmt.setString(2, event.getEvent_photo());
			pstmt.setString(3, event.getEvent_content());
			pstmt.setString(4, event.getEvent_start());
			pstmt.setString(5, event.getEvent_end());
			pstmt.setInt(6, event.getWinner_count());
			pstmt.setInt(7, event.getEvent_status());
			pstmt.setInt(8, event.getMem_num());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		

		
	}
	//~~총 레코드 수(검색 레코드 수)
	public int getEventCount(String keyfield, String keyword) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		String sub_sql = ""; //가변 데이터 처리
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword !=null && !"".equals(keyword)) {
				//제목, 내용으로 검색할 수 있도록 처리
				if(keyfield.equals("1")) sub_sql += "WHERE event_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE event_content LIKE ?";
			
			}
			//관리자만 이벤트를 등록할 수 있으므로 omember 테이블과 조인할 필요 x
			sql = "SELECT COUNT(*) FROM oevent " + sub_sql;
			pstmt = conn.prepareStatement(sql);
			
			if(keyword !=null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword + "%"); //sub_sql의 ?에 값을 바인딩
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				count = rs.getInt(1);
				
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	

	//----------------------------------
	//이벤트 글목록(검색 글 목록) 
	public List<EventVO> getListEvent(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EventVO> list = null;
		String sql = null;
		String sub_sql = "";  
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE event_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE event_content LIKE ?";
			}
			//진행 중인 이벤트 최신순으로 정렬하기
			sql = "SELECT * FROM (SELECT a.*,"
				+ "rownum rnum FROM (SELECT * "
				+ "FROM oevent " + sub_sql + " ORDER BY "
				+ "event_status DESC, event_end ASC)a) "
				+ "WHERE rnum>=? AND rnum<=?";

			pstmt = conn.prepareStatement(sql);

			if(keyword != null  && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<EventVO>();
			while(rs.next()) {
				EventVO event = new EventVO();
				//글번호, 이벤트 제목, 내용, 사진, 
				event.setEvent_num(rs.getInt("event_num"));
				event.setEvent_photo(rs.getString("event_photo"));
				event.setEvent_title(StringUtil.useBrNoHtml(rs.getString("event_title")));
				event.setEvent_content(StringUtil.useBrNoHtml(rs.getString("event_content")));
				event.setEvent_start(rs.getString("event_start"));
				event.setEvent_end(rs.getString("event_end"));
				event.setEvent_status(rs.getInt("event_status"));
				event.setEvent_check(rs.getInt("event_check"));
				event.setWinner_count(rs.getInt("winner_count"));
				
				list.add(event);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	//~~~~~이벤트 상세(관리자+사용자)  
	public EventVO getEvent(int event_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		EventVO event = null;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM oevent WHERE event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			
			rs = pstmt.executeQuery();
			
			event = new EventVO();

			if (rs.next()) {
				event = new EventVO();
				event.setEvent_num(rs.getInt("event_num"));
				event.setEvent_title(rs.getString("event_title"));
				event.setEvent_photo(rs.getString("event_photo"));
				event.setEvent_content(rs.getString("event_content"));
				event.setEvent_start(rs.getString("event_start"));
				event.setEvent_end(rs.getString("event_end"));
				event.setEvent_regdate(rs.getString("event_regdate"));
				event.setWinner_count(rs.getInt("winner_count"));
				event.setEvent_status(rs.getInt("event_status"));
				event.setEvent_hit(rs.getInt("event_hit"));
				event.setEvent_check(rs.getInt("event_check"));
			}
			
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return event;
	}
	
	//----------------------------------
	//조회수 증가시키기
	public void updateReadcount(int event_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE oevent SET event_hit=event_hit+1 WHERE event_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
		

	//----------------------------------
	/*
	 ㅁ 사진 파일 삭제 --덮어씌우기 작업!
	delete가 아닌 update 처리를 해서 값을 갱신해준다
	지워주는 것은 deleteFileaction에서
	실제 업도르 경로에서 삭제하는 처리를 해줘야함
	(쓰레기 값으로 남지 않기 때문에 DB에서 삭제를 해주는 것)
	 */	
	public void deleteFile(int event_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE oevent SET event_photo='' WHERE event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  event_num);
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
			
		}
	}
	//----------------------------------
	//이벤트 글수정(관리자) --사진처리
	public void updateEvent(EventVO event) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = ""; 
		int cnt = 0;
                              
		try { 
			conn = DBUtil.getConnection();
			
			if(event.getEvent_photo() !=null) {
				sub_sql = ", event_photo=?";
			}
			

			sql = "UPDATE oevent SET event_title=?, event_content=?, event_photo=?, "
					+ "event_start=?, event_end=?, winner_count=?, event_status=?, event_modifydate=SYSDATE " 
					+ "WHERE event_num=?";
		
			pstmt = conn.prepareStatement(sql);
			    
			pstmt.setString(++cnt, event.getEvent_title()); 
			pstmt.setString(++cnt, event.getEvent_content());
			
			if(event.getEvent_photo() !=null) {
				pstmt.setString(++cnt, event.getEvent_photo());
			}
			
			pstmt.setString(++cnt, event.getEvent_start());
			pstmt.setString(++cnt, event.getEvent_end());
			pstmt.setInt(++cnt, event.getWinner_count()); 
			pstmt.setInt(++cnt, event.getEvent_status());
			pstmt.setInt(++cnt, event.getEvent_num());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}

	//----------------------------------
	//이벤트 삭제
	/*
	 ㅁ 글 삭제
	
	 댓글삭제
	  당첨삭제 
	 부모글삭제

	 */
	public void deleteEvent(int event_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			//pstmt가 2개 이상이므로 sql문이 2개 이상이 되기 때문에 autocommit 해제
			conn.setAutoCommit(false);

			//당첨 삭제----
			sql = "DELETE FROM oevent_winner WHERE event_num=?";
			pstmt1 = conn.prepareStatement(sql);
			pstmt1.setInt(1, event_num);
			pstmt1.executeQuery();
			//------댓글 삭제------
			sql = "DELETE FROM oevent_reply WHERE event_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, event_num);
			pstmt2.executeUpdate();
			
			//------부모글 삭제------
			sql = "DELETE FROM oevent WHERE event_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, event_num);
			pstmt3.executeUpdate();
			
			conn.commit();
			
		} catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
			
		} finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt1, conn);
		}
		
	}
	//----------------------------------[이벤트 추첨 시작]------------------------------------------
	
	//종료된 이벤트 목록 불러오기
	//이벤트 글목록(검색 글 목록) 
	public List<EventVO> getListEndEvent(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EventVO> list = null;
		String sql = null;
		String sub_sql = "";  
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE event_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE event_content LIKE ?";
			}
			sql = "SELECT * FROM (SELECT a.*,"
				+ "rownum rnum FROM (SELECT * "
				+ "FROM oevent " + sub_sql + " ORDER BY "
				+ "event_end DESC)a) "
				+ "WHERE rnum>=? AND rnum<=? AND event_status=1";

			pstmt = conn.prepareStatement(sql);

			if(keyword != null  && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<EventVO>();
			while(rs.next()) {
				EventVO event = new EventVO();
				 
				event.setEvent_num(rs.getInt("event_num"));
				event.setEvent_photo(rs.getString("event_photo"));
				event.setEvent_title(StringUtil.useBrNoHtml(rs.getString("event_title")));
				event.setEvent_content(StringUtil.useBrNoHtml(rs.getString("event_content")));
				event.setEvent_start(rs.getString("event_start"));
				event.setEvent_end(rs.getString("event_end"));
				event.setEvent_status(rs.getInt("event_status"));
				event.setWinner_count(rs.getInt("winner_count"));
				event.setEvent_check(rs.getInt("event_check"));
				
				list.add(event);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	
	//==========이벤트 추첨 시작==========
	//이벤트 별 댓글 리스트를 가져오자
	public List<EventReplyVO> getListEventReply(int event_num) throws Exception{
		
		Connection conn = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		List<EventReplyVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM oevent_reply WHERE event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<EventReplyVO>();
			
			while(rs.next()) {
				EventReplyVO event = new EventReplyVO();
				event.setEvent_num(rs.getInt("event_num")); //부모글번호
				event.setMem_num(rs.getInt("mem_num"));
				event.setRe_num(rs.getInt("re_num"));
				event.setRe_content(rs.getString("re_content"));
				event.setRe_date(rs.getString("re_date"));
				event.setRe_ip(rs.getString("re_ip"));
				
				list.add(event);
			}
			
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		//리스트에 하나하나 다 담아주기
		return list;
	}
	
	//댓글 단 가져옴 - 당첨자 추첨(랜덤) - 당첨된 사람을 받아와서 update해줌 - event_winner=1인 사람은 로그인 할 때 알림창
	//댓글 당첨자의 정보를 update해줌 -- event_winner=1(당첨)
	public void updateEventwinner(int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "";
			pstmt = conn.prepareStatement(sql);
			
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
		
	}
	
	//댓글 당첨자의 정보를 읽어오고 event_winner==1로 업데이트 해줌
	public EventReplyVO selectEventwinner(int event_num, int mem_num) throws Exception { 
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    PreparedStatement pstmt2 = null;
	    ResultSet rs = null;
	    ResultSet rs2 = null;
	    String sql = null;
	    EventReplyVO event = new EventReplyVO();
	    
	    try {
	        conn = DBUtil.getConnection();
	        // 댓글은 단 회원 한정
	        sql = "SELECT * FROM oevent_reply WHERE mem_num=? AND event_num=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, mem_num);
	        pstmt.setInt(2, event_num);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            event.setEvent_num(rs.getInt("event_num")); // 부모글번호
	            event.setMem_num(rs.getInt("mem_num"));
	            event.setRe_num(rs.getInt("re_num"));
	            event.setRe_content(rs.getString("re_content"));
	            event.setRe_date(rs.getString("re_date"));
	            event.setRe_modifydate(rs.getString("re_modifydate"));
	            event.setRe_ip(rs.getString("re_ip"));
	        }
	        
	        sql = "UPDATE oevent_reply SET event_winner=1 WHERE mem_num=? AND event_num=?";
	        pstmt2 = conn.prepareStatement(sql);
	        pstmt2.setInt(1, mem_num);
	        pstmt2.setInt(2, event_num);
	        int rowsAffected = pstmt2.executeUpdate(); // executeUpdate()로 변경
	        if (rowsAffected > 0) {
	            event.setEvent_winner(1); // 고정값 1로 설정
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs2, pstmt2, null);
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    
	    return event;
	}

	
	//당첨자 확인 메서드
	public boolean checkEventWinner(int mem_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    boolean isWinner = false;

	    try {
	        conn = DBUtil.getConnection();
	        sql = "SELECT * FROM omember "
	            + "JOIN oevent_reply ON omember.mem_num = oevent_reply.mem_num "
	            + "WHERE oevent_reply.event_winner = 1 AND omember.mem_num = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, mem_num);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            // event_winner 값이 1인 경우, 당첨자로 판단
	            isWinner = true;
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return isWinner; //당첨자면 true, 당첨자가 아니면 false;
	}
	
	//이벤트 추첨 버튼을 눌렀을 때 event_check를 1로 변경해줌
	public void updateCheckEvent(int event_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "UPDATE oevent SET event_check=1 WHERE event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			pstmt.executeUpdate();
			
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//-------------------[이벤트 당첨결과 저장 oevent_winner]-------------------
	//당첨결과 등록
	public void insertEventWinner(EventWinnerVO winner) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "INSERT INTO oevent_winner(win_num, re_num, event_num, mem_num) "
					+ "VALUES(oevent_winner_seq.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, winner.getRe_num());
			pstmt.setInt(2, winner.getEvent_num());
			pstmt.setInt(3, winner.getMem_num());
	
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//당첨 결과를 가져오는 메서드
	public List<EventWinnerVO> getListEventWin(int event_num) throws Exception{
		
		Connection conn = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		List<EventWinnerVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM oevent_winner WHERE event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<EventWinnerVO>();
			
			while(rs.next()) {
				EventWinnerVO winner = new EventWinnerVO();
				
				winner.setWin_num(rs.getInt("win_num"));
				winner.setRe_num(rs.getInt("re_num"));
				winner.setEvent_num(rs.getInt("event_num"));
				winner.setMem_num(rs.getInt("mem_num"));
				
				list.add(winner);
			}
			
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		//리스트에 하나하나 다 담아주기
		return list;
	}

	//이벤트 당첨 중복 체크
	public boolean checkEventWinner(int event_num, int re_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    boolean isExist = false;

	    try {
	        conn = DBUtil.getConnection();
	        sql = "SELECT * FROM oevent_winner WHERE event_num=? AND re_num=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, event_num);
	        pstmt.setInt(2, re_num);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            isExist = true;
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return isExist;
	}
	
	// 내가 댓글 단 이벤트 목록 --마이페이지
	public List<EventReplyVO> getMyEventReply(int start, int end,int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<EventReplyVO> list = new ArrayList<EventReplyVO>();
		
		try {
			conn = DBUtil.getConnection();
			sql =  "SELECT * FROM "
					+ "(SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM oevent_reply WHERE mem_num=? ORDER BY re_date DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EventReplyVO reply = new EventReplyVO();
				reply.setEvent_num(rs.getInt("event_num"));
				reply.setRe_date(rs.getString("re_date"));
				reply.setRe_content(rs.getString("re_content"));
				reply.setEvent_winner(rs.getInt("event_winner"));
				reply.setRe_num(rs.getInt("re_num"));
				reply.setRe_status(rs.getInt("re_status"));
				
				list.add(reply);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	// 내가 댓글 단 이벤트 --마이페이지
		public List<EventReplyVO> getMyEventReplyEnd(int mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			List<EventReplyVO> list = new ArrayList<EventReplyVO>();
			
			try {
				conn = DBUtil.getConnection();
				sql =  "select * from oevent_reply r join oevent o on r.event_num=o.event_num where event_status=1 and r.mem_num=?"; //종료된 목록만 가져옴
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, mem_num);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					EventReplyVO reply = new EventReplyVO();
					reply.setEvent_num(rs.getInt("event_num"));
					reply.setRe_date(rs.getString("re_date"));
					reply.setRe_content(rs.getString("re_content"));
					reply.setEvent_winner(rs.getInt("event_winner"));
					reply.setRe_num(rs.getInt("re_num"));
					reply.setRe_status(rs.getInt("re_status"));
					
					list.add(reply);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
	//===================================
	//댓글처리 시작 -ajax통신
	
	// 1. 댓글 등록
	public void insertReplyEvent(EventReplyVO eventReply) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
			conn = DBUtil.getConnection();
			//댓글을 달면 re_status를 1로 변경해줘서 댓글 중복 제거
			sql = "INSERT INTO oevent_reply(re_num, re_content, re_ip, mem_num, event_num, re_status, re_date) "
					+ "VALUES(oevent_reply_seq.nextval, ?, ?, ?, ?, 1, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, eventReply.getRe_content());
			pstmt.setString(2, eventReply.getRe_ip()); 
			pstmt.setInt(3, eventReply.getMem_num());
			pstmt.setInt(4, eventReply.getEvent_num());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
		
	// ================================================
	//2. 댓글 개수
	public int getReplyEventCount(int event_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM oevent_reply r JOIN omember m ON r.mem_num=m.mem_num "
					+ "WHERE r.event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1); //count가 집합함수이므로 하나의 레코드가 생성됨. index
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
		
	// ================================================
	// 3. 댓글 목록
	public List<EventReplyVO> getListReplyEvent(int start, int end, int event_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<EventReplyVO> list = null;
		
		try {
			
			conn = DBUtil.getConnection();
			
			sql ="SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM oevent_reply r JOIN omember m "
					+ "USING(mem_num) WHERE r.event_num=? ORDER BY r.re_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<EventReplyVO>();
			
			while(rs.next()) {
				//VO객체 생성
				EventReplyVO reply = new EventReplyVO();
				
				reply.setRe_num(rs.getInt("re_num"));
				
				
				//날짜 -> 1분 전, 2시간 전, 3일 전과 같이 보여주기
				//DurationFromNow util 넣어주기 (static 이므로 객체 생성 안해줘도 됨)
				reply.setRe_date(DurationFromNow.getTimeDiffLabel(rs.getString("re_date")));
				
				//수정일은 있는 경우에만 넣기
				if(rs.getString("re_modifydate")!=null) {
					reply.setRe_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("re_modifydate")));
				}
				
				//HTML을 인정하지 않고 줄바꿈을 하도록 -util에서 만들어 놓은 메서드 사용
				reply.setRe_content(StringUtil.useBrNoHtml(rs.getString("re_content")));
				
				reply.setEvent_num(rs.getInt("event_num"));
				reply.setMem_num(rs.getInt("mem_num"));
				reply.setId(rs.getString("id"));
				
				list.add(reply);
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
		
	// ================================================
	// 댓글 상세 - 댓글 수정 및 삭제 시 작성사 회원번호 체크 용도로 사용
	public EventReplyVO getReplyEvent(int re_num) throws Exception{

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql= null;
	EventReplyVO reply = null;
	
	try {
		conn = DBUtil.getConnection();
		sql = "SELECT * FROM oevent_reply WHERE re_num=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, re_num);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			reply = new EventReplyVO();
			
			reply.setRe_num(rs.getInt("re_num"));
			reply.setMem_num(rs.getInt("mem_num"));
		}
		
	} catch(Exception e) {
		throw new Exception(e);
	} finally {
		DBUtil.executeClose(rs, pstmt, conn);
	}
	return reply;
}

			
	// ================================================
	// 댓글 수정  
	public void updateReplyEvent(EventReplyVO reply) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "UPDATE oevent_reply SET re_content=?, re_modifydate=SYSDATE, "
					+ "re_ip=? WHERE re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getRe_content());
			pstmt.setString(2, reply.getRe_ip());
			pstmt.setInt(3, reply.getRe_num());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// ================================================
	// 댓글 삭제
	public void deleteReplyEvent(int re_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM oevent_reply WHERE re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_num);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//댓글 중복 확인하기 위한 메서드
	public EventReplyVO checkReply(int mem_num, int event_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		EventReplyVO eventreply = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM oevent_reply WHERE mem_num=? and event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, event_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				eventreply = new EventReplyVO();
				eventreply.setEvent_num(event_num);
			}
		
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return eventreply; //값이 없으면 null을 return --댓글을 달 수 있음
	
	}
} //end DAO
