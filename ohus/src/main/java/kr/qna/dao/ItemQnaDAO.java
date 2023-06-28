package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.qna.vo.ItemQnaVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class ItemQnaDAO {
	//싱글턴패턴
	private static ItemQnaDAO instance = new ItemQnaDAO();
	public static ItemQnaDAO getInstance() {
		return instance;
	}
	private ItemQnaDAO() {}
	
	//상품문의 등록
	public void insertQna(ItemQnaVO qna) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try { 
			conn = DBUtil.getConnection();
					
			//상품문의 등록
			sql = "INSERT INTO item_qna (qna_num, qna_title, qna_content, qna_category, "
				+ "qna_ip, mem_num, item_num) "
				+ "VALUES(qna_seq.nextval, ?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getQna_title());
			pstmt.setString(2, qna.getQna_content());
			pstmt.setInt(3, qna.getQna_category());
			pstmt.setString(4, qna.getQna_ip());
			pstmt.setInt(5, qna.getMem_num());
			pstmt.setInt(6, qna.getItem_num());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/*
	//회원번호로 구매상세 갯수 구하기
	public int getDetailCountByMem_num (int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) FROM orders_detail WHERE mem_num=?";
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
	//회원번호로 구매상세리스트 구하기 
	public List<OrderDetailVO> getDetailOrderListByMem_num (int mem_num) throws Exception{ 
		Connection conn = null; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		List<OrderDetailVO> list = null; 
		String sql = null;
		
		try { 
			conn = DBUtil.getConnection();
		
			sql = "SELECT * FROM orders_detail WHERE mem_num=?";
		 
			pstmt = conn.prepareStatement(sql); pstmt.setInt(1, mem_num);
			rs = pstmt.executeQuery(); list = new ArrayList<OrderDetailVO>();
	 		while(rs.next()) { 
				OrderDetailVO detail = new OrderDetailVO();
	 			detail.setDetail_num(rs.getInt("detail_num"));
	 			detail.setMem_num(rs.getInt("mem_num"));
	 			detail.setItem_num(rs.getInt("item_num"));
				detail.setItem_name(rs.getString("item_name"));
				detail.setItem_price(rs.getInt("item_price"));
				detail.setOrder_quantity(rs.getInt("order_quantity"));
				detail.setItem_total(rs.getInt("item_total"));
				detail.setOrder_num(rs.getInt("order_num"));
	
				list.add(detail); 
			} 
		}catch(Exception e) {
			throw new Exception(e); 
		}finally {
	 		DBUtil.executeClose(rs, pstmt, conn); 
		} 
		return list; 
	}
	*/

	//총 레코드 수 | 검색 레코드 수
	public int getQnaCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE q.qna_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE q.qna_content LIKE ?";
			}
			
			sql = "SELECT count(*) FROM item_qna q JOIN omember m USING(mem_num) " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword +  "%");
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
	
	//글 목록 | 검색 글 목록
	public List<ItemQnaVO> getListQna (int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ItemQnaVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE q.qna_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE q.qna_content LIKE ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*,rownum rnum "
				+ "FROM (SELECT * FROM item_qna q JOIN omember m USING(mem_num) " + sub_sql
				+ "ORDER BY q.qna_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword +  "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ItemQnaVO>();
			while(rs.next()) {
				ItemQnaVO qna = new ItemQnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setQna_title(StringUtil.useNoHtml(rs.getString("qna_title")));
				qna.setQna_regdate(rs.getDate("qna_regdate"));
				qna.setQna_category(rs.getInt("qna_category"));
				qna.setQna_status(rs.getInt("qna_status"));
				qna.setId(rs.getString("id"));
				qna.setItem_name(rs.getString("item_name"));
				
				list.add(qna);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//상품문의 상세
	public ItemQnaVO getQna(int qna_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemQnaVO qna = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM item_qna q JOIN omember USING(mem_num) "
				+ "LEFT OUTER JOIN omember_detail d USING(mem_num) "
				+ "WHERE q.qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				qna = new ItemQnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setQna_title(rs.getString("qna_title"));
				qna.setQna_content(rs.getString("qna_content"));
				qna.setQna_category(rs.getInt("qna_category"));
				qna.setQna_regdate(rs.getDate("qna_regdate"));
				qna.setQna_mdate(rs.getDate("qna_mdate"));
				qna.setQna_status(rs.getInt("qna_status"));
				qna.setId(rs.getString("id"));
				qna.setItem_name(rs.getString("item_name"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return qna;
	}
	
	//상품문의 수정
	public void updateQna(ItemQnaVO qna) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int detail_num = 0;
		int item_num = 0;
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE item_qna SET qna_title=?, qna_content=?, qna_category=?, " 
				+ "qna_mdate=SYSDATE, qna_ip=? WHERE qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getQna_title());
			pstmt.setString(2, qna.getQna_content());
			pstmt.setInt(3, qna.getQna_category());
			pstmt.setString(4, qna.getQna_ip());
			pstmt.setInt(5, qna.getQna_num());
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//상품문의 삭제
	public void deleteQna(int qna_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			//답변 삭제
			//부모글 삭제
			sql = "DELETE FROM item_qna WHERE qna_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, qna_num);
			pstmt2.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//답변 등록
	//답변 갯수
	//답변 목록
	//답변 상세 - 수정, 삭세 시 작성자 회원번호 체크 용도로 사용
	//답변 수정
	//답변 삭제
	
}
