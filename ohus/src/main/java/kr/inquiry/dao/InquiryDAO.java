package kr.inquiry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.inquiry.vo.InquiryVO;
import kr.util.DBUtil;

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
				+ "inq_ip, mem_num) VALUES(inq_seq.nextval, ?,?,?,?,?)";
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
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM inquiry i JOIN omember m USING (mem_num) "
				+ sub_sql + "ORDER BY i.inq_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
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
				inquiry.setInq_ip(rs.getString("inq_ip"));
				inquiry.setInq_status(rs.getInt("inq_status"));
				inquiry.setMem_num(rs.getInt("mem_num"));
				
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
			sql = "SELECT * FROM inquiry i JOIN omember o USING (mem_num) WHERE i.mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inq_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				inquiry = new InquiryVO();
				inquiry.setInq_num(rs.getInt("inq_num"));
				inquiry.setInq_title(rs.getString("inq_title"));
				inquiry.setInq_content(rs.getString("inq_content"));
				inquiry.setInq_regdate(rs.getDate("inq_regdate"));
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
	
	
	//문의 글 삭제
	
}
