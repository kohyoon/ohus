package kr.item.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.item.vo.ItemFavVO;
import kr.item.vo.ItemReviewVO;
import kr.item.vo.ItemVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

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
	//상품 수정(관리자 전용)
	public void updateItem(ItemVO item) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			
			if(item.getItem_photo1() != null) {
				sub_sql += " item_photo1 = ?,";
			}
			if(item.getItem_photo2() != null) {
				sub_sql += " item_photo2 = ?,";
			}
			if(item.getItem_photo3() != null) {
				sub_sql += " item_photo3 = ?,";
			}
			//SQL문 작성
			sql = "UPDATE item SET item_name = ?, item_category = ?, item_price = ?, item_content = ?, "
				+ "item_stock = ?, item_origin = ?, " + sub_sql + " item_mdate = SYSDATE, "
				+ "item_status = ? WHERE item_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터바인딩
			pstmt.setString(++cnt, item.getItem_name());
			pstmt.setInt(++cnt, item.getItem_category());
			pstmt.setInt(++cnt, item.getItem_price());
			pstmt.setString(++cnt, item.getItem_content());
			pstmt.setInt(++cnt, item.getItem_stock());
			pstmt.setString(++cnt, item.getItem_origin());
			if(item.getItem_photo1() != null) {
				pstmt.setString(++cnt, item.getItem_photo1());
			}
			if(item.getItem_photo2() != null) {
				pstmt.setString(++cnt, item.getItem_photo2());
			}
			if(item.getItem_photo3() != null) {
				pstmt.setString(++cnt, item.getItem_photo3());
			}
			pstmt.setInt(++cnt, item.getItem_status());
			pstmt.setInt(++cnt, item.getItem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//전체 상품 개수/검색 상품 개수(관리자, 사용자)
	public int getItemCount(String keyfield, String keyword, int item_status, String item_category) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			if(item_category != null && !"".equals(item_category)) {
				sub_sql += " AND item_category = ?";
			}
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += " AND item_name LIKE ?";
				if(keyfield.equals("2")) sub_sql += " AND item_content LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM item WHERE item_status > ? " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, item_status);
			if(item_category != null && !"".equals(item_category)) {
				pstmt.setInt(++cnt, Integer.parseInt(item_category));
			}
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
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
	public List<ItemVO> getListItem(int start, int end, String keyfield, String keyword, int item_status, String item_category) throws Exception{
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
			if(item_category != null && !"".equals(item_category)) {
				sub_sql += " AND item_category = ?";
			}
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += " AND item_name LIKE ?";
				if(keyfield.equals("2")) sub_sql += " AND item_content LIKE ?";
			}
			
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM item WHERE item_status > ? " 
			+ sub_sql 
			+ " ORDER BY item_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, item_status);
			if(item_category != null && !"".equals(item_category)) {
				pstmt.setInt(++cnt, Integer.parseInt(item_category));
			}
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
	public void insertFav(ItemFavVO favVO) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO item_fav (fav_num, mem_num, item_num) "
				+ "VALUES (item_fav_seq.nextval, ?, ?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, favVO.getMem_num());
			pstmt.setInt(2, favVO.getItem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//상품 스크랩 개수
	public int selectFavCount(int item_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM item_fav WHERE item_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item_num);
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
	//회원번호와 상품번호를 이용한 스크랩 정보(회원이 상품을 호출할 때 스크랩 선택여부 표시)
	public ItemFavVO selectFav(int item_num, int mem_num) throws Exception{
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ItemFavVO fav = null;
	String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM item_fav WHERE item_num = ? AND mem_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item_num);
			pstmt.setInt(2, mem_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fav = new ItemFavVO();
				fav.setFav_num(rs.getInt("fav_num"));
				fav.setMem_num(rs.getInt("mem_num"));
				fav.setItem_num(rs.getInt("item_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return fav;
	}
	//상품 스크랩 삭제
	public void deleteFav(int fav_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try{
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM item_fav WHERE fav_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, fav_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//내가 선택한 스크랩 목록
	public List<ItemVO> getListItemFav(int start, int end, int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ItemVO> list = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM item_fav JOIN omember using(mem_num) JOIN item using(item_num) WHERE mem_num = ? "
				+ "ORDER BY item_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<ItemVO>();
			while(rs.next()) {
				ItemVO item = new ItemVO();
				item.setItem_num(rs.getInt("item_num"));
				item.setItem_name(StringUtil.useNoHtml(rs.getString("item_name")));
				item.setItem_photo1(rs.getString("item_photo1"));
				
				list.add(item);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//리뷰 등록
	public void insertReview(ItemReviewVO itemReview) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO item_review (review_num, mem_num, item_num, item_score, "
				+ "review_content, review_photo, review_regdate) VALUES (item_review_seq.nextval, ?, ?, ?, ?, ?, SYSDATE)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, itemReview.getMem_num());
			pstmt.setInt(2, itemReview.getItem_num());
			pstmt.setInt(3, itemReview.getItem_score());
			pstmt.setString(4, itemReview.getReview_content());
			pstmt.setString(5, itemReview.getReview_photo());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//리뷰 개수(페이지 처리)
	public int getReviewCount(int item_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM item_review r "
				+ "JOIN item i ON r.item_num = i.item_num "
				+ "WHERE r.item_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item_num);
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
	//리뷰 목록
	public List<ItemReviewVO> getReviewList(int start, int end, int item_num) throws Exception{
		Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<ItemReviewVO> list = null;
			String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM item_review r JOIN omember m USING(mem_num) "
				+ "WHERE r.item_num = ? ORDER BY r.review_num DESC)a) "
				+ "WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<ItemReviewVO>();
			while(rs.next()) {
				ItemReviewVO review = new ItemReviewVO();
				review.setReview_num(rs.getInt("review_num"));
				review.setMem_num(rs.getInt("mem_num"));
				review.setItem_num(rs.getInt("item_num"));
				review.setItem_score(rs.getInt("item_score"));
				review.setReview_content(rs.getString("review_content"));
				review.setReview_photo(rs.getString("review_photo"));
				review.setReview_regdate(rs.getDate("review_regdate"));
				review.setId(rs.getString("id"));
				
				list.add(review);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//리뷰 상세(리뷰 수정, 삭제시 리뷰 작성자 회원번호 확인 용도로 사용)
	public ItemReviewVO getReviewDetail(int review_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemReviewVO review = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM item_review WHERE review_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, review_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				review = new ItemReviewVO();
				review.setReview_num(rs.getInt("review_num"));
				review.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return review;
	}
	//리뷰 수정
	public void updateReview(ItemReviewVO review) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE item_review SET item_score = ?, review_photo = ?, review_content = ? "
				+ "review_mdate = SYSDATE WHERE review_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, review.getItem_score());
			pstmt.setString(2, review.getReview_photo());
			pstmt.setString(3, review.getReview_content());
			pstmt.setInt(4, review.getReview_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//리뷰 평균 점수
	public double avgReview(int item_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		double avgscore = 0.0;
		try {
			//커넥션 풀로부터 커넥션을 할당(JDBC 1,2단계)
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT AVG(item_score) AS average_score FROM item_review WHERE item_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				avgscore = rs.getDouble("average_score");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt, conn);
		}
		
		return avgscore;
	}

}
