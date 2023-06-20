package kr.notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.notice.vo.NoticeVO;
import kr.util.DBUtil;

public class NoticeDAO {
	//싱글턴 패턴
	private static NoticeDAO instance = new NoticeDAO();
	public static NoticeDAO getInstance() {
		return instance;
	}
	private NoticeDAO() {}
	
	//글 등록
		
	//총 레코드 수 | 검색 레코드 수
	public int getNoticeCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) { //keyword가 null이 아니고 빈 문자열이 아닌 경우
				if(keyfield.equals("1")) sub_sql += "WHERE n.notice_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql +="WHERE n.notice_content LIKE ?";
			}
			
			sql = "SELEECT count(*) FROM notice n JOIN omember m USING(mem_num) " + sub_sql;
			
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
	
	//글 목록 | 검색 글 목록
	public List<NoticeVO> getListNotice(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NoticeVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) { //keyword가 null이 아니고 빈 문자열이 아닌 경우
				if(keyfield.equals("1")) sub_sql += "WHERE n.notice_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql +="WHERE n.notice_content LIKE ?";
			}
						
			sql = "SELECT * FROM (SELECT a.* rownum rnum "
				+ "FROM (SELECT * FROM notice n JOIN omember m "
				+ "USING(mem_num) " + sub_sql 
				+ "ORDER BY n.notice_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<NoticeVO>();
			while(rs.next()) {
				NoticeVO notice = new NoticeVO();
				notice.setNotice_num(rs.getInt("notice_num"));
				notice.setNotice_title(rs.getString("notice_title"));
				notice.setNotice_regdate(rs.getDate("notice_regdate"));
				
				list.add(notice);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//글 상세
	//조회수 증가
	//글 수정
	//글 삭제
}
