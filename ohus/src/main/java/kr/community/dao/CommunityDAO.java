package kr.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.community.vo.CommunityFavVO;
import kr.community.vo.CommunityReplyVO;
import kr.community.vo.CommunityReportVO;
import kr.community.vo.CommunityVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;



public class CommunityDAO {
	//싱글턴 패턴
	private static CommunityDAO instance = new CommunityDAO();
	public static CommunityDAO getInstance() {return instance;}
	private CommunityDAO() {}
		
	//글 등록
	public void insertBoard(CommunityVO board)
	                        throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO cboard (cboard_num, cboard_category, cboard_title, cboard_content, cboard_photo1, cboard_photo2, mem_num, cboard_ip) "
			    + "VALUES (cboard_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터를 바인딩
			pstmt.setInt(1, board.getCboard_category());
			pstmt.setString(2, board.getCboard_title());
			pstmt.setString(3, board.getCboard_content());
			pstmt.setString(4, board.getCboard_photo1());
			pstmt.setString(5, board.getCboard_photo2());
			pstmt.setInt(6, board.getMem_num());
			pstmt.setString(7, board.getCboard_ip());
			// SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}	
	
	//총 레코드 수(검색 레코드 수)
	public int getBoardCount(String keyfield,
			                 String keyword, String cboard_category)
	                        throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(cboard_category != null && !"".equals(cboard_category)) {
				sub_sql += "WHERE cboard_category = ?";
			}
			
			if (keyword != null && !"".equals(keyword)) {
				  if (keyfield.equals("1")) sub_sql += "WHERE b.cboard_title LIKE ?";
				  else if (keyfield.equals("2")) sub_sql += "WHERE m.id LIKE ?";
				  else if (keyfield.equals("3")) sub_sql += "WHERE b.cboard_content LIKE ?";
				}

			// SQL문 작성
			sql = "SELECT COUNT(*) FROM cboard b "
				+ "JOIN omember m ON b.mem_num = m.mem_num " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			if(cboard_category != null && !"".equals(cboard_category)) {
				pstmt.setInt(++cnt, Integer.parseInt(cboard_category));
			}
			
			if(keyword != null 
					      && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			
			//SQL 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}	
		return count;
	}
	
	// 글 목록(검색글 목록)
	public List<CommunityVO> getListBoard(int start, int end, String keyfield, String keyword, String cboard_category) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<CommunityVO> list = null;
	    String sql = null;
	    String sub_sql = "";
	    int cnt = 0;

	    try {
	        // 커넥션 풀로부터 커넥션을 할당
	        conn = DBUtil.getConnection();

	        if (cboard_category != null && !"".equals(cboard_category)) {
	            sub_sql += "AND cboard_category = ?";
	        }

	        if (keyword != null && !"".equals(keyword)) {
	            if (keyfield.equals("1")) sub_sql += "AND b.cboard_title LIKE ?";
	            else if (keyfield.equals("2")) sub_sql += "AND m.id LIKE ?";
	            else if (keyfield.equals("3")) sub_sql += "AND b.cboard_content LIKE ?";
	        }
	            	
	        sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT b.*, m.id, md.photo FROM cboard b JOIN omember m ON b.mem_num = m.mem_num JOIN omember_detail md ON m.mem_num = md.mem_num WHERE 1=1 " + sub_sql + " ORDER BY b.cboard_regdate DESC)a) WHERE rnum>=? AND rnum<=?";

	        // PreparedStatement 객체 생성
	        pstmt = conn.prepareStatement(sql);
	        if (cboard_category != null && !"".equals(cboard_category)) {
	            pstmt.setInt(++cnt, Integer.parseInt(cboard_category));
	        }
	        // 데이터 바인딩
	        if (keyword != null && !"".equals(keyword)) {
	            pstmt.setString(++cnt, "%" + keyword + "%");
	        }
	        pstmt.setInt(++cnt, start);
	        pstmt.setInt(++cnt, end);

	        // SQL문 실행
	        rs = pstmt.executeQuery();
	        list = new ArrayList<CommunityVO>();
	        while (rs.next()) {
	            CommunityVO board = new CommunityVO();
	            board.setCboard_num(rs.getInt("cboard_num"));
	            board.setCboard_title(rs.getString("cboard_title"));
	            board.setCboard_hit(rs.getInt("cboard_hit"));
	            board.setCboard_regdate(rs.getDate("cboard_regdate"));
	            board.setId(rs.getString("id"));
	            board.setCboard_photo1(rs.getString("cboard_photo1"));
	            board.setCboard_fav(rs.getInt("cboard_fav"));
	            board.setPhoto(rs.getString("photo"));
	            
	            // 좋아요 개수 조회
	            int cboard_num = rs.getInt("cboard_num");
	            int favCount = selectFavCount(cboard_num);
	            board.setFavCount(favCount);
	            
	            // 자바빈을 ArrayList에 저장
	            list.add(board);
	        }
	    } catch(Exception e) {
	        throw new Exception(e);
	    } finally {
	        // 자원정리
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}


	//글 상세
	public CommunityVO getBoard(int cboard_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    CommunityVO board = null;
	    String sql = null;

	    try {
	        // 커넥션 풀로부터 커넥션을 할당
	        conn = DBUtil.getConnection();
	        // SQL 작성
	        sql = "SELECT * FROM cboard b " +
	              "JOIN omember m ON b.mem_num = m.mem_num " +
	              "LEFT OUTER JOIN omember_detail d ON m.mem_num = d.mem_num " +
	              "WHERE b.cboard_num=?";
	        // PreparedStatement 객체 생성
	        pstmt = conn.prepareStatement(sql);
	        // ?에 데이터를 바인딩
	        pstmt.setInt(1, cboard_num);
	        // SQL문을 실행해서 결과행을 ResultSet에 담음
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            board = new CommunityVO();
	            board.setCboard_num(rs.getInt("cboard_num"));
	            board.setCboard_title(rs.getString("cboard_title"));
	            board.setCboard_photo1(rs.getString("cboard_photo1"));
	            board.setCboard_photo2(rs.getString("cboard_photo2"));
	            board.setCboard_content(rs.getString("cboard_content"));
	            board.setCboard_hit(rs.getInt("cboard_hit"));
	            board.setCboard_fav(rs.getInt("cboard_fav"));
	            board.setCboard_regdate(rs.getDate("cboard_regdate"));
	            board.setCboard_modifydate(rs.getDate("cboard_modifydate"));
	            board.setCboard_ip(rs.getString("cboard_ip"));
	            board.setOrder_num(rs.getInt("order_num"));
	            board.setMem_num(rs.getInt("mem_num"));
	            board.setId(rs.getString("id"));
	            board.setPhoto(rs.getString("photo"));
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        // 자원정리
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return board;
	}
	
	//조회수 증가
	public void updateReadcount(int cboard_num)
	                        throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE cboard SET cboard_hit=cboard_hit+1 "
				+ "WHERE cboard_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setInt(1, cboard_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}	
	
	//파일 삭제
	public void deleteFile(int cboard_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;
	    try {
	        // 커넥션 풀로부터 커넥션 할당
	        conn = DBUtil.getConnection();
	        
	        // SQL문 작성
	        sql = "UPDATE cboard SET cboard_photo1 = '', cboard_photo2 = '' WHERE cboard_num=?";
	        
	        // PreparedStatement 객체 생성
	        pstmt = conn.prepareStatement(sql);
	        // ?에 데이터 바인딩
	        pstmt.setInt(1, cboard_num);
	        // SQL문 실행
	        pstmt.executeUpdate();
	    } catch(Exception e) {
	        throw new Exception(e);
	    } finally {
	        // 자원정리
	        DBUtil.executeClose(null, pstmt, conn);
	    }
	}
	
	// 글 수정
	public void updateBoard(CommunityVO board) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;
	    String sub_sql = "";
	    int cnt = 0;

	    try {
	        // 커넥션풀로부터 커넥션을 할당
	        conn = DBUtil.getConnection();

	        if (board.getCboard_photo1() != null) {
	            // photo1을 업로드한 경우
	            sub_sql += ", cboard_photo1=?";
	        }

	        if (board.getCboard_photo2() != null) {
	            // photo2를 업로드한 경우
	            sub_sql += ", cboard_photo2=?";
	        }

	        sql = "UPDATE cboard SET cboard_title=?, cboard_content=?, cboard_modifydate=SYSDATE"
	                + sub_sql + ", cboard_ip=? WHERE cboard_num=?";
	        pstmt = conn.prepareStatement(sql);
	        // ?에 데이터를 바인딩
	        pstmt.setString(++cnt, board.getCboard_title());
	        pstmt.setString(++cnt, board.getCboard_content());
	        if (board.getCboard_photo1() != null) {
	            pstmt.setString(++cnt, board.getCboard_photo1());
	        }
	        if (board.getCboard_photo2() != null) {
	            pstmt.setString(++cnt, board.getCboard_photo2());
	        }
	        pstmt.setString(++cnt, board.getCboard_ip());
	        pstmt.setInt(++cnt, board.getCboard_num());

	        // SQL문 실행
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        // 자원정리
	        DBUtil.executeClose(null, pstmt, conn);
	    }
	}

	// 글 삭제
	public void deleteBoard(int cboard_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt1 = null;
	    PreparedStatement pstmt2 = null;
	    PreparedStatement pstmt3 = null;
	    PreparedStatement pstmt4 = null;
	    String sql1 = null;
	    String sql2 = null;
	    String sql3 = null;
	    String sql4 = null;
	    
	    try {
	        conn = DBUtil.getConnection();
	        conn.setAutoCommit(false);

	        // 댓글 신고 정보 삭제
	        sql1 = "DELETE FROM cboard_report WHERE re_num IN (SELECT re_num FROM cboard_reply WHERE cboard_num=?)";
	        pstmt1 = conn.prepareStatement(sql1);
	        pstmt1.setInt(1, cboard_num);
	        pstmt1.executeUpdate();

	        // 댓글 삭제
	        sql2 = "DELETE FROM cboard_reply WHERE cboard_num=?";
	        pstmt2 = conn.prepareStatement(sql2);
	        pstmt2.setInt(1, cboard_num);
	        pstmt2.executeUpdate();

	        // 커뮤니티 스크랩 정보 삭제
	        sql3 = "DELETE FROM cboard_fav WHERE cboard_num=?";
	        pstmt3 = conn.prepareStatement(sql3);
	        pstmt3.setInt(1, cboard_num);
	        pstmt3.executeUpdate();

	        // 부모글 삭제
	        sql4 = "DELETE FROM cboard WHERE cboard_num=?";
	        pstmt4 = conn.prepareStatement(sql4);
	        pstmt4.setInt(1, cboard_num);
	        pstmt4.executeUpdate();

	        conn.commit();
	    } catch (Exception e) {
	        conn.rollback();
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(null, pstmt4, null);
	        DBUtil.executeClose(null, pstmt3, null);
	        DBUtil.executeClose(null, pstmt2, null);
	        DBUtil.executeClose(null, pstmt1, conn);
	    }
	}

	//좋아요 등록
	public void insertFav(CommunityFavVO favVO)
	                      throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO cboard_fav ("
				+ "fav_num,cboard_num,mem_num) "
				+ "VALUES (cboard_fav_seq.nextval,"
				+ "?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, favVO.getCboard_num());
			pstmt.setInt(2, favVO.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//좋아요 개수
	public int selectFavCount(int cboard_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM cboard_fav "
				+ "WHERE cboard_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cboard_num);
			//SQL문 실행
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
	
	//내가 받은 좋아요 개수
	
	
	//회원번호와 게시물 번호를 이용한 좋아요 정보
	//(회원이 게시물을 호출할 때 좋아요 선택 여부 표시)
	public CommunityFavVO selectFav(
			CommunityFavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CommunityFavVO fav = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM cboard_fav "
				+ "WHERE cboard_num=? AND mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setInt(1, favVO.getCboard_num());
			pstmt.setInt(2, favVO.getMem_num());
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fav = new CommunityFavVO();
				fav.setFav_num(rs.getInt("fav_num"));
				fav.setCboard_num(rs.getInt("cboard_num"));
				fav.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return fav;
	}
	
	//좋아요 삭제
	public void deleteFav(int fav_num)
	                        throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM cboard_fav WHERE fav_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, fav_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//내가 선택한 좋아요 목록
	public List<CommunityVO> getListBoardFav(int start, int end, int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommunityVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM cboard b JOIN "
					+ "omember m USING(mem_num) JOIN "
					+ "cboard_fav f USING(cboard_num) WHERE "
					+ "f.mem_num=? ORDER BY cboard_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			
			
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<CommunityVO>();
			while(rs.next()) {
				CommunityVO board = new CommunityVO();
				board.setCboard_num(
						rs.getInt("cboard_num"));
				board.setCboard_title(StringUtil.useNoHtml(
						     rs.getString("cboard_title")));
				board.setCboard_regdate(rs.getDate("cboard_regdate"));
				board.setId(rs.getString("id"));
				
				list.add(board);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}
	
	//댓글 등록
	public void insertReplyBoard(
			      CommunityReplyVO boardReply)
	                       throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO cboard_reply "
				+ "(re_num,re_content,re_ip,"
				+ "mem_num,cboard_num) VALUES ("
				+ "cboard_reply_seq.nextval,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, 
					boardReply.getRe_content());
			pstmt.setString(2, 
					      boardReply.getRe_ip());
			pstmt.setInt(3, boardReply.getMem_num());
			pstmt.setInt(4, boardReply.getCboard_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//댓글 개수
	public int getReplyBoardCount(
			    int cboard_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM cboard_reply b "
				+ "JOIN omember m ON b.mem_num=m.mem_num "
				+ "WHERE b.cboard_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cboard_num);
			//SQL문 실행
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
	
	//댓글 목록
	public List<CommunityReplyVO> 
	               getListReplyBoard(
	          int start,int end,int cboard_num)
	                       throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommunityReplyVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*,"
				+ "rownum rnum FROM (SELECT * "
				+ "FROM cboard_reply b JOIN "
				+ "omember m USING(mem_num) "
				+ "WHERE b.cboard_num=? ORDER BY "
				+ "b.re_num DESC)a) "
				+ "WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cboard_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<CommunityReplyVO>();
			while(rs.next()) {
				CommunityReplyVO reply = 
						      new CommunityReplyVO();
				reply.setRe_num(rs.getInt("re_num"));
				//날짜 -> 1분전, 1시간전, 1일전 형식의
				//문자열로 변환
				reply.setRe_date(
						DurationFromNow.getTimeDiffLabel(
						        rs.getString("re_date")));
				if(rs.getString("re_modifydate")!=null) {
					reply.setRe_modifydate(
						DurationFromNow.getTimeDiffLabel(
							rs.getString("re_modifyDate")));
				}
				reply.setRe_content(
						StringUtil.useBrNoHtml(
						     rs.getString("re_content")));
				reply.setCboard_num(rs.getInt("cboard_num"));
				reply.setMem_num(rs.getInt("mem_num"));
				reply.setId(rs.getString("id"));
				
				list.add(reply);			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}
	
	//댓글 상세(댓글 수정,삭제시 작성자 회원번호 체크 용도로 사용)
	public CommunityReplyVO getReplyBoard(
			                    int re_num)
	                       throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CommunityReplyVO reply = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM cboard_reply "
				+ "WHERE re_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setInt(1, re_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new CommunityReplyVO();
				reply.setRe_num(rs.getInt("re_num"));
				reply.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return reply;
	}
	
	//댓글 수정
	public void updateReplyBoard(
			CommunityReplyVO reply)
	                       throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE cboard_reply SET "
				+ "re_content=?,"
				+ "re_modifydate=SYSDATE,"
				+ "re_ip=? WHERE re_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, reply.getRe_content());
			pstmt.setString(2, reply.getRe_ip());
			pstmt.setInt(3, reply.getRe_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//댓글 삭제
	public void deleteReplyBoard(int re_num)
	                       throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM cboard_reply "
				+ "WHERE re_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setInt(1, re_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 신고하기
	public void reportReply(CommunityReportVO report) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "INSERT INTO cboard_report (dec_num, dec_category, dec_regdate, mem_num, re_num) " +
                    "VALUES (cboard_report_seq.nextval, ?, SYSDATE, ?, ?)";
 
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, report.getDec_category());
            pstmt.setInt(2, report.getMem_num());
            pstmt.setInt(3, report.getRe_num());

            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
	

	
}