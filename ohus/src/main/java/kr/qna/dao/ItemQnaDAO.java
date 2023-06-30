package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.qna.vo.ItemAnswerVO;
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
				+ "VALUES(item_qna_seq.nextval, ?,?,?,?,?,?)";
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
	
	//상품번호별 상품문의 개수
	public int getQnaCountByItem_num(int item_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) FROM item_qna WHERE item_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, item_num);
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
				+ "FROM (SELECT * FROM item_qna q JOIN omember m USING(mem_num) JOIN item i USING(item_num) " + sub_sql
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
				qna.setItem_num(rs.getInt("item_num"));
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
	
	//==============내가 남긴 상품문의 목록==========================//
	public List<ItemQnaVO> getListQnaByMem_num (int start, int end, int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ItemQnaVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT a.*,rownum rnum "
				+ "FROM (SELECT * FROM item_qna q JOIN omember m USING(mem_num) JOIN item i "
				+ "USING(item_num) ORDER BY q.qna_num DESC)a) "
				+ "WHERE rnum >= ? AND rnum <= ? AND mem_num=?";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			pstmt.setInt(3, mem_num);
			
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
				qna.setItem_num(rs.getInt("item_num"));
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
				+ "JOIN item i USING(item_num) WHERE q.qna_num=?";
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
				qna.setMem_num(rs.getInt("mem_num"));
				qna.setId(rs.getString("id"));
				qna.setItem_num(rs.getInt("item_num"));
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
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE item_qna SET qna_title=?, qna_content=?, qna_category=?, " 
				+ "qna_mdate=SYSDATE, qna_ip=?, item_num=? WHERE qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getQna_title());
			pstmt.setString(2, qna.getQna_content());
			pstmt.setInt(3, qna.getQna_category());
			pstmt.setString(4, qna.getQna_ip());
			pstmt.setInt(5, qna.getItem_num());
			pstmt.setInt(6, qna.getQna_num());
			
			pstmt.executeUpdate();
			
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
	public void insertQnaAnswer(ItemAnswerVO answer) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO item_answer (a_num, a_content, qna_num, mem_num) "
				+ "VALUES(item_ans_seq.nextval, ?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, answer.getA_content());
			pstmt.setInt(2, answer.getQna_num());
			pstmt.setInt(3, answer.getMem_num());
			
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//답변 갯수
	public int getAnswerCount(int qna_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT count(*) FROM item_answer a JOIN omember m "
				+ "ON a.mem_num=m.mem_num WHERE a.qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
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
	
	//답변 목록
	public List<ItemAnswerVO> getListAnswer(int start, int end, int qna_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ItemAnswerVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
				+ "FROM (SELECT * FROM item_answer a JOIN omember m USING(mem_num) "
				+ "WHERE a.qna_num=? ORDER BY a.a_num DESC)a) "
				+ "WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ItemAnswerVO>();
			while(rs.next()) {
				ItemAnswerVO answer = new ItemAnswerVO();
				answer.setA_num(rs.getInt("a_num"));
				answer.setA_regdate(rs.getString("a_regdate"));
				if(rs.getString("a_mdate") != null) {
					answer.setA_mdate(rs.getString("a_mdate"));
				}
				answer.setA_content(StringUtil.useBrNoHtml(rs.getString("a_content")));
				answer.setQna_num(rs.getInt("qna_num"));
				answer.setMem_num(rs.getInt("mem_num"));
				answer.setId(rs.getString("id"));
				
				list.add(answer);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//답변 상세 - 수정, 삭세 시 작성자 회원번호 체크 용도로 사용
	public ItemAnswerVO getAnswer(int a_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemAnswerVO answer = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM item_answer WHERE a_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, a_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				answer = new ItemAnswerVO();
				answer.setA_num(rs.getInt("a_num"));
				answer.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return answer;
	}
	
	//답변 수정
	public void updateAnswer(ItemAnswerVO answer) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE item_answer SET a_content=?, a_mdate=SYSDATE WHERE a_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, answer.getA_content());
			pstmt.setInt(2, answer.getA_num());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//답변 삭제
	public void deleteAnswer(int a_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "DELETE FROM item_answer WHERE a_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, a_num);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//문의처리완료
	public void setStatusDone(int qna_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE item_qna SET qna_status=2 WHERE qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//문의 답변 삭제 -> 문의 처리 전으로 돌아가기
	public void setStatusNone(int qna_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE item_qna SET qna_status=1 WHERE qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
