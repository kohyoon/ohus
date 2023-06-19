package kr.event.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.event.vo.EventVO;
import kr.util.DBUtil;

public class EventDAO {
	// 싱글톤 패턴
	
	private static EventDAO instance = new EventDAO();

	public static EventDAO getInstance() {
		return instance;
	}

	private EventDAO() {}
	
	//----------------------------------
	//이벤트 등록
	public void insertEvent(EventVO event) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {

			conn = DBUtil.getConnection();

			sql = "INSERT INTO oevent (event_num, event_title, event_photo, event_content, "
					+ "event_start, event_end, event_hit, winner_count, mem_num) "
					+ "VALUES(oevent_seq.nextval, ?,?,?,?,?,?,?,?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, event.getEvent_title());
			pstmt.setString(2, event.getEvent_photo());
			pstmt.setString(3, event.getEvent_content());
			pstmt.setDate(4, event.getEvent_start());
			pstmt.setDate(5, event.getEvent_end());
			pstmt.setInt(6, event.getEvent_hit());
			pstmt.setInt(7, event.getWinner_count());
			pstmt.setInt(8, event.getMem_num());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
		
	}
	
	//----------------------------------
	//총 레코드수(검색 레코드 수) --join, boardDAO 참고
	
	//----------------------------------
	//이벤트 글목록
	
	//----------------------------------
	//이벤트 글상세--필요하나?
	
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
			sql = "UPDATE oevent SET filename='' WHERE event_num=?";
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
	//이벤트 글수정 (사진 파일이 필수이기 때문에 분리하지 않아도 됨)	
public void updateBoard(EventVO event) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
                              
		try {
			conn = DBUtil.getConnection();

			sql = "UPDATE oevent SET event_title=?, event_content=?, event_photo=? "
					+ "event_start=?, event_end=?, winner_count=?, event_modifydate=SYSDATE " 
					+ "WHERE event_num=?";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, event.getEvent_title());
			pstmt.setString(2, event.getEvent_content());
			pstmt.setString(3, event.getEvent_photo());
			pstmt.setDate(4, event.getEvent_start());
			pstmt.setDate(5, event.getEvent_end());
			pstmt.setInt(6, event.getWinner_count());
			pstmt.setInt(7, event.getEvent_num());
			
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
	public void deleteBoard(int event_num) throws Exception{
		
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
}
