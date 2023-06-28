package kr.market.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.market.vo.MarketVO;
import kr.util.DBUtil;

public class MarketDAO {

	// 싱글턴 패턴 생성
	private static MarketDAO instance = new MarketDAO();
	public static MarketDAO getInstance() {
		return instance;
	}
	private MarketDAO() {}
	
	// 거래글 전체 목록/ 검색 목록 개수
	public int getListMarketCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !keyword.equals("")) {
				sub_sql += "WHERE market_title LIKE ?"; // 제목으로만 검색
			}
			sql = "SELECT COUNT(*) FROM market " + sub_sql;
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !keyword.equals("")) {
				pstmt.setString(1,"%" + keyword + "%");
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	// 거래글 전체 목록/ 검색 목록 조회 (페이징 처리)
	public List<MarketVO> getListMarket(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		List<MarketVO> list = null;
		int cnt = 0;
		
		try {
			
			conn = DBUtil.getConnection();
			// 검색 내용이 존재 할 경우 keyfield에 따른 sub_sql 정의
			if(keyword != null && !keyword.equals("")) {
				sub_sql += "WHERE market_title LIKE ? ";
			}
			
			sql = "SELECT * FROM "
					+ "(SELECT a.*, rownum rnum FROM (SELECT * FROM market m LEFT JOIN omember o ON m.mem_num = o.mem_num " 
					+ sub_sql 
					+ "ORDER BY market_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !keyword.equals("")) {
				pstmt.setString(++cnt,"%" + keyword + "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<MarketVO>();
			
			while(rs.next()) {
				MarketVO market = new MarketVO();
				market.setMarket_num(rs.getInt("market_num"));
				market.setMarket_title(rs.getString("market_title"));
				market.setMarket_content(rs.getString("market_content"));
				market.setMarket_hit(rs.getInt("market_hit"));
				market.setMarket_regdate(rs.getDate("market_regdate"));
				market.setMarket_status(rs.getInt("market_status"));
				market.setMarket_photo1(rs.getString("market_photo1"));
				market.setMarket_photo2(rs.getString("market_photo2"));
				market.setMem_num(rs.getInt("mem_num"));
				market.setId(rs.getString("id"));
				list.add(market);
			}
		}catch(Exception e) {	
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	// 거래글 상세
	public MarketVO getDetailMarket(int market_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MarketVO market = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM market m LEFT JOIN omember o ON m.mem_num = o.mem_num WHERE market_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, market_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				market = new MarketVO();
				
				market.setMarket_num(rs.getInt("market_num"));
				market.setMarket_title(rs.getString("market_title"));
				market.setMarket_content(rs.getString("market_content"));
				market.setMarket_hit(rs.getInt("market_hit"));
				market.setMarket_regdate(rs.getDate("market_regdate"));
				market.setMarket_modifydate(rs.getDate("market_modifydate"));
				market.setMarket_status(rs.getInt("market_status"));
				market.setMarket_photo1(rs.getString("market_photo1"));
				market.setMarket_photo2(rs.getString("market_photo2"));
				market.setMem_num(rs.getInt("mem_num"));
				market.setId(rs.getString("id"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return market;
	}
	
	// 거래글 작성
	public void insertMarket(MarketVO market) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "INSERT INTO market "
					+ "(market_num,market_title,market_content,market_hit,market_regdate,market_status,market_photo1,market_photo2,mem_num)"
					+ "VALUES (market_seq.nextval,?,?,?,SYSDATE,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, market.getMarket_title());
			pstmt.setString(2, market.getMarket_content());
			pstmt.setInt(3, market.getMarket_hit());
			pstmt.setInt(4, market.getMarket_status());
			pstmt.setString(5, market.getMarket_photo1());
			pstmt.setString(6, market.getMarket_photo2());
			pstmt.setInt(7, market.getMem_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 거래글 수정
	public void updateMarket(MarketVO market) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			if(market.getMarket_photo1()!=null && market.getMarket_photo2()==null) {
				sub_sql = ",market_photo1=?";
			}else if(market.getMarket_photo1()==null && market.getMarket_photo2()!=null) {
				sub_sql = ",market_photo2=?";
			}else if(market.getMarket_photo1()!=null && market.getMarket_photo2()!=null){
				sub_sql = ",market_photo1=?,market_photo2=?";
			}
			sql = "UPDATE market SET "
					+ "market_title=?,market_content=?,market_modifydate=SYSDATE,market_status=?"
					+ sub_sql
					+ " WHERE market_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, market.getMarket_title());
			pstmt.setString(++cnt, market.getMarket_content());
			pstmt.setInt(++cnt, market.getMarket_status());
			if(market.getMarket_photo1()!=null && market.getMarket_photo2()==null) {
				pstmt.setString(++cnt, market.getMarket_photo1());
			}else if(market.getMarket_photo1()==null && market.getMarket_photo2()!=null) {
				pstmt.setString(++cnt, market.getMarket_photo2());
			}else if(market.getMarket_photo1()!=null && market.getMarket_photo2()!=null){
				pstmt.setString(++cnt, market.getMarket_photo1());
				pstmt.setString(++cnt, market.getMarket_photo2());
			}
			pstmt.setInt(++cnt, market.getMarket_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 거래글 삭제 , 게시글 삭제시 -> 채팅 내용도 삭제 되도록 해야 되나
	public void deleteMarket(int market_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			// market_num과 관련한 채팅방들의 채팅 내용 삭제
			sql = "DELETE FROM chat WHERE chatroom_num IN (SELECT chatroom_num FROM chatroom WHERE market_num=?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, market_num);
			pstmt.executeUpdate();
			
			// chatroom 삭제
			sql = "DELETE FROM chatroom WHERE market_num = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, market_num);
			pstmt2.executeUpdate();
			
			// market 삭제
			sql = "DELETE FROM market WHERE market_num = ?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, market_num);
			pstmt3.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 조회수 증가
	public void updateMarketHit(int market_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE market SET market_hit=marKet_hit+1 WHERE market_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, market_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 내가 작성한 거래글 개수 조회
		public int getMyMarketCount(int mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT COUNT(*) FROM market WHERE mem_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mem_num);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					count = rs.getInt(1);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return count;
		}
		// 내가 작성한 거래글 목록 조회 (페이지 처리)
		public List<MarketVO> getListMyMarket(int start, int end, int mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			List<MarketVO> list = new ArrayList<MarketVO>();
			MarketVO market = null;
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM "
						+ "(SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM market WHERE mem_num=? ORDER BY market_regdate DESC)a) "
						+ "WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mem_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					market = new MarketVO();
					market.setMarket_num(rs.getInt("market_num"));
					market.setMarket_content(rs.getString("market_content"));
					market.setMarket_hit(rs.getInt("market_hit"));
					market.setMarket_photo1(rs.getString("market_photo1"));
					market.setMarket_regdate(rs.getDate("market_regdate"));
					market.setMarket_status(rs.getInt("market_status"));
					market.setMarket_title(rs.getString("market_title"));
					market.setMem_num(rs.getInt("mem_num"));
					list.add(market);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
	
	// 추가될 수 있는 작업 : 파일 삭제 기능 (사진 2개 이상을 등록하도록 할 경우 -> 2개까지는 필수, 3개 이상 등록 가능 시 한 개까지의 사진 삭제 기능이 추가됨)
}
