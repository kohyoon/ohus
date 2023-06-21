package kr.event.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import kr.event.vo.EventReplyVO;
import kr.event.vo.EventVO;

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
					+ "event_content, event_start, event_end, winner_count, mem_num) "
					+ "VALUES(oevent_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, event.getEvent_title());
			pstmt.setString(2, event.getEvent_photo());
			pstmt.setString(3, event.getEvent_content());
			pstmt.setString(4, event.getEvent_start());
			pstmt.setString(5, event.getEvent_end());
			pstmt.setInt(6, event.getWinner_count());
			pstmt.setInt(7, event.getMem_num());

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
	
	/*
	 * //---------------------------------- //~~~~~~~`전체 상품목록/검색 상품목록(관리자+사용자)
	 * public List<EventVO> getListEvent(int start, int end, String keyfield, String
	 * keyword, int event_status) throws Exception{
	 * 
	 * Connection conn = null; PreparedStatement pstmt = null; ResultSet rs = null;
	 * String sql = null;
	 * 
	 * List<EventVO> list = null; String sub_sql = ""; int cnt = 0;
	 * 
	 * try {
	 * 
	 * conn = DBUtil.getConnection(); if(keyword !=null && !"".equals(keyword)) {
	 * if(keyfield.equals("1")) sub_sql += "AND event_title LIKE ?"; else
	 * if(keyfield.equals("2")) sub_sql += "AND event_content LIKE ?";
	 * 
	 * } //관리자의 status 0 -- 미표시(1), 표시(2) 상품 볼 수 있고 //사용자 status 1 -- 1보다 큰 표시(2)
	 * 상품만 볼 수 있다 sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM " +
	 * "(SELECT * FROM oevent WHERE status >? " + sub_sql +
	 * " ORDER BY event_num DESC)a) WHERE rnum >= ? AND rnum<= ?";
	 * 
	 * pstmt = conn.prepareStatement(sql);
	 * 
	 * //?에 값 바인딩 pstmt.setInt(++cnt, event_status);
	 * 
	 * 
	 * if(keyword !=null && !"".equals(keyword)) { pstmt.setString(++cnt,
	 * "%"+keyword +"%"); }
	 * 
	 * pstmt.setInt(++cnt, start); pstmt.setInt(++cnt, end);
	 * 
	 * rs = pstmt.executeQuery();
	 * 
	 * list = new ArrayList<EventVO>(); //리스트에 값을 담아준다 while(rs.next()) { EventVO
	 * event = new EventVO(); event.setEvent_num(rs.getInt("event_num"));
	 * event.setEvent_title(rs.getString("event_title"));
	 * event.setEvent_photo(rs.getString("event_photo"));
	 * event.setEvent_content(rs.getString("event_content"));
	 * event.setEvent_start(rs.getString("event_start"));
	 * event.setEvent_end(rs.getString("event_end"));
	 * event.setEvent_regdate(rs.getString("event_regdate"));
	 * event.setWinner_count(rs.getInt("winner_count"));
	 * event.setEvent_status(rs.getInt("event_status"));
	 * 
	 * list.add(event); }
	 * 
	 * } catch(Exception e) { throw new Exception(e); } finally {
	 * DBUtil.executeClose(rs, pstmt, conn); }
	 * 
	 * return list; }
	 */
	
	//----------------------------------
	//이벤트 글목록(검색 글 목록) --member와 조인을 해야되나...? 관리자만 볼 수 있는 건데
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
				+ "event_start DESC)a) "
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
				event.setEvent_title(StringUtil.useBrNoHtml(rs.getString("event_content")));
				event.setEvent_content(StringUtil.useBrNoHtml(rs.getString("event_content")));
				event.setEvent_start(rs.getString("event_start"));
				event.setEvent_end(rs.getString("event_end"));
				event.setEvent_status(rs.getInt("event_status"));
				
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
					+ "event_start=?, event_end=?, winner_count=?, event_modifydate=SYSDATE " 
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
	1.댓글 삭제 delete oevent_reply
	2.부모글 삭제 delete oevent
	
	댓글이 달리면 부모글에 자식이 생기기 때문에 먼저 제거해줘야함
	
	즉, 글 삭제 처리는 총 2개의 sql이 실행된다

	 */
	public void deleteEvent(int event_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			//pstmt가 2개 이상이므로 sql문이 2개 이상이 되기 때문에 autocommit 해제
			conn.setAutoCommit(false);

			
			//------댓글 삭제------
			sql = "DELETE FROM oevent_reply WHERE event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			pstmt.executeUpdate();
			
			//------부모글 삭제------
			sql = "DELETE FROM oevent WHERE event_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, event_num);
			pstmt2.executeUpdate();
			
			conn.commit();
			
		} catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
			
		} finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	//----------------------------------
	//이벤트 당첨자 추첨
	
	//--------------------	
	//이벤트 날짜처리 
	/*
	 * public int isCheckedEventDate(String event_end) { //String으로 받아야함
	 * 
	 * 
	 * return int; }
	 */

	
	//===================================
	//댓글처리 시작 -ajax통신
	
	// 1. 댓글 등록
		public void insertReplyEvent(EventReplyVO eventReply) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				
				conn = DBUtil.getConnection();
				sql = "INSERT INTO oevent_reply(re_num, re_content, re_ip, mem_num, event_num) "
						+ "VALUES(oevent_reply_seq.nextval, ?, ?, ?, ?)";
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
