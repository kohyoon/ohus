package kr.item.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.item.vo.ItemVO;
import kr.util.DBUtil;

public class ItemDAO {
	//싱글턴 패턴
	private static ItemDAO instance = new ItemDAO();
		
	public static ItemDAO getInstance() {
		return instance;
	}
		
	private ItemDAO() {}
	
	//상품 등록(관리자 전용)
	public void insertItem(ItemVO item) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO item (item_num, item_name, item_category, item_price, item_content, "
				+ "item_stock, item_origin, item_photo1, item_photo2, item_photo3, item_status) VALUES "
				+ "(item_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_category());
			pstmt.setInt(3, item.getItem_price());
			pstmt.setString(4, item.getItem_content());
			pstmt.setInt(5, item.getItem_stock());
			pstmt.setString(6, item.getItem_origin());
			pstmt.setString(7, item.getItem_photo1());
			pstmt.setString(8, item.getItem_photo2());
			pstmt.setString(9, item.getItem_photo3());
			pstmt.setInt(10, item.getItem_status());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관리자 상품 수정
	
	//관리자 상품 삭제
	
	//상품 사진 삭제
	
	//전체 상품 개수/검색 상품 개수(관리자, 사용자)
	public int getItemCount(String keyfield, String keyword, int status) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "AND name LIKE ?";
				if(keyfield.equals("2")) sub_sql += "AND detail LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM item WHERE item_status > ? " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, "%" + keyword + "%");
			}
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);//1 : 컬럼 인덱스
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//전체 상품 목록/ 검색 상품 목록(관리자, 사용자)
	public List<ItemVO> getListItem(int start, int end, String keyfield, String keyword, int item_status) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ItemVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
				if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "AND name LIKE ?";
				if(keyfield.equals("2")) sub_sql += "AND detail LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM item WHERE item_status > ? " 
			+ sub_sql
			+ " ORDER BY item_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, item_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<ItemVO>();
			while(rs.next()) {
				ItemVO item = new ItemVO();
				item.setItem_num(rs.getInt("item_num"));
				item.setItem_name(rs.getString("item_name"));
				item.setItem_price(rs.getInt("item_price"));
				item.setItem_stock(rs.getInt("item_stock"));
				item.setItem_photo1(rs.getString("item_photo1"));
				item.setItem_photo2(rs.getString("item_photo2"));
				item.setItem_regdate(rs.getDate("item_regdate"));
				item.setItem_status(rs.getInt("item_status"));
					
				list.add(item);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//상품 상세 정보(관리자, 사용자)
	public ItemVO getItem(int item_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemVO item = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM item WHERE item_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			while(rs.next()) {
				item = new ItemVO();
				item.setItem_num(rs.getInt("item_num"));
				item.setItem_name(rs.getString("item_name"));
				item.setItem_category(rs.getInt("item_category"));
				item.setItem_price(rs.getInt("item_price"));
				item.setItem_content(rs.getString("item_content"));
				item.setItem_stock(rs.getInt("item_stock"));
				item.setItem_hit(rs.getInt("item_hit"));
				item.setItem_fav(rs.getInt("item_fav"));
				item.setItem_score(rs.getInt("item_score"));
				item.setItem_origin(rs.getString("item_origin"));
				item.setItem_photo1(rs.getString("item_photo1"));
				item.setItem_photo2(rs.getString("item_photo2"));
				item.setItem_photo3(rs.getString("item_photo3"));
				item.setItem_regdate(rs.getDate("item_regdate"));
				item.setItem_mdate(rs.getDate("item_mdate"));
				item.setItem_status(rs.getInt("item_status"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return item;
	}
	//상품 상세 정보 조회수 증가
	public void updateViewsCount(int item_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE item SET item_hit = item_hit + 1 WHERE item_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//상품 스크랩 등록
	
	//상품 스크랩 개수
	
	//회원번호와 상품번호를 이용한 스크랩 정보(회원이 상품을 호출할 때 스크랩 선택여부 표시)
	
	//상품 스크랩 삭제
	
	//내가 선택한 스크랩 목록
	
	//리뷰 등록
	
	//리뷰 개수(페이지 처리)
	
	//리뷰 목록
	
	//리뷰 상세(리뷰 수정, 삭제시 리뷰 작성자 회원번호 확인 용도로 사용)
	
	//리뷰 수정
	
	//리뷰 삭제
	
	//리뷰 사진 삭제
}