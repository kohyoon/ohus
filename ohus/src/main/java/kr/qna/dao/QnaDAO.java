package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.qna.vo.QnaVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class QnaDAO {
	//싱글턴패턴
	private static QnaDAO instance = new QnaDAO();
	public static QnaDAO getInstance() {
		return instance;
	}
	private QnaDAO() {}
	
	//상품문의 등록
	public void insertQna(QnaVO qna) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO qna (qna_num,qna_title,qna_content,qna_filename,qna_ip,mem_num,order_num) "
				+ "VALUES(qna_seq.nextval, ?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getQna_title());
			pstmt.setString(2, qna.getQna_content());
			pstmt.setString(3, qna.getQna_filename());
			pstmt.setString(4, qna.getQna_ip());
			pstmt.setInt(5, qna.getMem_num());
			pstmt.setInt(6, qna.getOrder_num());
			
			
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
			
			sql = "SELECT count(*) FROM qna q JOIN omember m USING(mem_num) " + sub_sql;
			
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
	public List<QnaVO> getListQna (int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaVO> list = null;
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
				+ "FROM (SELECT * FROM qna q JOIN omember m USING(mem_num) " + sub_sql
				+ "ORDER BY q.qna_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword +  "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<QnaVO>();
			while(rs.next()) {
				QnaVO qna = new QnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setQna_title(StringUtil.useNoHtml(rs.getString("qna_title")));
				qna.setQna_regdate(rs.getDate("qna_regdate"));
				qna.setId(rs.getString("id"));
				
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
	public QnaVO getQna(int qna_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		QnaVO qna = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM qna q JOIN omember USING(mem_num) "
				+ "LEFT OUTER JOIN omember_detail d USING(mem_num) "
				+ "WHERE q.qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				qna = new QnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setQna_title(rs.getString("qna_title"));
				qna.setQna_content(rs.getString("qna_content"));
				qna.setQna_regdate(rs.getDate("qna_regdate"));
				qna.setMdate(rs.getDate("qna_mdate"));
				qna.setQna_status(rs.getInt("qna_status"));
				qna.setQna_filename(rs.getString("filename"));
				qna.setMem_num(rs.getInt("mem_num"));
				qna.setId(rs.getString("id"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return qna;
	}
	
	
	//파일 삭제
	//상품문의 수정
	//상품문의 삭제
	
	//답변 등록
	//답변 갯수
	//답변 목록
	//답변 상세 - 수정, 삭세 시 작성자 회원번호 체크 용도로 사용
	//답변 수정
	//답변 삭제
	
}
