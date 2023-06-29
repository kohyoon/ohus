package kr.inquiry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.inquiry.vo.InquiryAnswerVO;
import kr.inquiry.vo.InquiryVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;

public class InquiryDAO {
	//싱글턴패턴
	private static InquiryDAO instance = new InquiryDAO();
	public static InquiryDAO getInstance() {
		return instance;
	}
	private InquiryDAO() {}
	
	//문의 등록
	public void insertInquiry(InquiryVO inquiry) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO inquiry (inq_num, inq_title, inq_category, inq_content, "
				+ "inq_ip, mem_num) VALUES(inquiry_seq.nextval, ?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inquiry.getInq_title());
			pstmt.setInt(2, inquiry.getInq_category());
			pstmt.setString(3, inquiry.getInq_content());
			pstmt.setString(4, inquiry.getInq_ip());
			pstmt.setInt(5, inquiry.getMem_num());
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//전체 문의글 총 갯수 / 검색 문의글 총 갯수
	public int getInquiryCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) { //keyword가 null이 아니고 빈문자열이 아닌 경우
				if(keyfield.equals("1")) sub_sql += "WHERE i.title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?";
			}
			
			sql = "SELECT count(*) FROM inquiry i JOIN omember m USING (mem_num) " + sub_sql;
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword + "%");
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
	
	//전체 문의 글 목록 / 검색 문의 글 목록
	public List<InquiryVO> getListInquiry(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InquiryVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {  //keyword가 null이 아니고 빈문자열이 아닌 경우
				if(keyfield.equals("1")) sub_sql += "WHERE i.title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
				+ "FROM (SELECT * FROM inquiry i JOIN omember m "
				+ "USING (mem_num) " + sub_sql + "ORDER BY i.inq_num DESC)a)"
				+ "WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<InquiryVO>();
			while(rs.next()) {
				InquiryVO inquiry = new InquiryVO();
				inquiry.setInq_num(rs.getInt("inq_num"));
				inquiry.setInq_title(rs.getString("inq_title"));
				inquiry.setInq_category(rs.getInt("inq_category"));
				inquiry.setInq_regdate(rs.getDate("inq_regdate"));
				inquiry.setInq_status(rs.getInt("inq_status"));
				inquiry.setMem_num(rs.getInt("mem_num"));
				inquiry.setId(rs.getString("id"));
				
				list.add(inquiry);				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//================= 내가 작성한 문의 글 =============================//
	
	public List<InquiryVO> getListInquiryByMem_Num(int start, int end, int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InquiryVO> list = null;
		String sql = null;
		
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
				+ "FROM (SELECT * FROM inquiry i JOIN omember m "
				+ "USING (mem_num) ORDER BY i.inq_num DESC)a)"
				+ "WHERE rnum >= ? AND rnum <= ? AND mem_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			pstmt.setInt(3, mem_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<InquiryVO>();
			while(rs.next()) {
				InquiryVO inquiry = new InquiryVO();
				inquiry.setInq_num(rs.getInt("inq_num"));
				inquiry.setInq_title(rs.getString("inq_title"));
				inquiry.setInq_category(rs.getInt("inq_category"));
				inquiry.setInq_regdate(rs.getDate("inq_regdate"));
				inquiry.setInq_status(rs.getInt("inq_status"));
				inquiry.setMem_num(rs.getInt("mem_num"));
				inquiry.setId(rs.getString("id"));
				
				list.add(inquiry);				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//문의 글 상세
	public InquiryVO getInquiryDetail(int inq_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InquiryVO inquiry = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM inquiry i JOIN omember o USING(mem_num) "
				+ "LEFT OUTER JOIN omember_detail d USING(mem_num) WHERE i.inq_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inq_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				inquiry = new InquiryVO();
				inquiry.setInq_num(rs.getInt("inq_num"));
				inquiry.setInq_title(rs.getString("inq_title"));
				inquiry.setInq_content(rs.getString("inq_content"));
				inquiry.setInq_category(rs.getInt("inq_category"));
				inquiry.setInq_regdate(rs.getDate("inq_regdate"));
				inquiry.setInq_modifydate(rs.getDate("inq_modifydate"));
				inquiry.setInq_status(rs.getInt("inq_status"));
				inquiry.setMem_num(rs.getInt("mem_num"));
				inquiry.setId(rs.getString("id"));			
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return inquiry;
	}
	
		
	//문의 글 수정
	public void updateInquiry(InquiryVO inquiry) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE inquiry SET inq_title=?,inq_category=?,"
				+ "inq_content=?,inq_modifydate=SYSDATE,inq_ip=? WHERE inq_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inquiry.getInq_title());
			pstmt.setInt(2, inquiry.getInq_category());
			pstmt.setString(3, inquiry.getInq_content());
			pstmt.setString(4, inquiry.getInq_ip());
			pstmt.setInt(5, inquiry.getInq_num());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//문의 글 삭제
	public void deleteInquiry(int inq_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false); //오토커밋 해제
			
			//답변(댓글) 삭제
			sql = "DELETE FROM inquiry_answer WHERE inq_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inq_num);
			pstmt.executeUpdate();
			
			//글 삭제
			sql = "DELETE FROM inquiry WHERE inq_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, inq_num);
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
	public void insertAnswer(InquiryAnswerVO inquiryAnswer) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO inquiry_answer (ans_num, ans_content, inq_num, mem_num) "
				+ "VALUES (inquiry_ans_seq.nextval, ?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inquiryAnswer.getAns_content());
			pstmt.setInt(2, inquiryAnswer.getInq_num());
			pstmt.setInt(3, inquiryAnswer.getMem_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//답변 갯수
	public int getAnswerCount(int inq_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT count(*) FROM inquiry_answer a JOIN omember m "
				+ "ON a.mem_num=m.mem_num WHERE a.inq_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inq_num);
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
	public List<InquiryAnswerVO> getListAnswer(int start, int end, int inq_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InquiryAnswerVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM "
				+ "inquiry_answer i JOIN omember m USING(mem_num) WHERE i.inq_num=? "
				+ "ORDER BY i.ans_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inq_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<InquiryAnswerVO>();
			while(rs.next()) {
				InquiryAnswerVO answer = new InquiryAnswerVO();
				answer.setAns_num(rs.getInt("ans_num"));
				answer.setAns_date(DurationFromNow.getTimeDiffLabel(rs.getString("ans_date")));
				if(rs.getString("ans_mdate") != null) {
					answer.setAns_mdate(DurationFromNow.getTimeDiffLabel(rs.getString("ans_mdate")));
				}
				answer.setAns_content(rs.getString("ans_content"));
				answer.setInq_num(rs.getInt("inq_num"));
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
	
	//답변 상세(답변 수정 또는 삭제시 작성자 회원번호 체크 용도로 사용)
	public InquiryAnswerVO getAnswer(int ans_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InquiryAnswerVO answer = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM inquiry_answer WHERE ans_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ans_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				answer = new InquiryAnswerVO();
				answer.setInq_num(rs.getInt("inq_num"));
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
	public void updateAnswer(InquiryAnswerVO answer) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE inquiry_answer SET ans_content=?, ans_mdate=SYSDATE WHERE ans_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, answer.getAns_content());
			pstmt.setInt(2, answer.getAns_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//답변 삭제
	public void deleteAnswer(int ans_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE inquiry_answer WHERE ans_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ans_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//문의처리완료
	public void updateStatus(int inq_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE inquiry SET inq_status=2 WHERE inq_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inq_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
}
