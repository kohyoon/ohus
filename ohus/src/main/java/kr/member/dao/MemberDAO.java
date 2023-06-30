package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	//싱글턴 패턴
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	
	
	//==========================================================
	//[회원]
	//회원가입 

	public void insertMember(MemberVO member) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int num = 0; //시퀀스 번호 저장
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//회원번호(mem_num) 구하기
			sql = "SELECT omember_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			//omember 테이블에 데이터 저장
			sql = "INSERT INTO omember (mem_num,id) "
				+ "VALUES (?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);//시퀀스
			pstmt2.setString(2, member.getId());//id
			pstmt2.executeUpdate();
			
			//omember_detail 테이블에 데이터 저장
			sql = "INSERT INTO omember_detail (mem_num,"
				+ "name,password,phone,email,zipcode,"
				+ "address1,address2) VALUES (?,?,?,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, member.getName());
			pstmt3.setString(3, member.getPassword());
			pstmt3.setString(4, member.getPhone());
			pstmt3.setString(5, member.getEmail());
			pstmt3.setString(6, member.getZipcode());
			pstmt3.setString(7, member.getAddress1());
			pstmt3.setString(8, member.getAddress2());
			pstmt3.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
	}
	//==========================================================
	//ID 중복 체크 및 로그인 처리
	public MemberVO checkMember(String id) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM omember m LEFT OUTER JOIN "
				+ "omember_detail d ON m.mem_num = d.mem_num "
				+ "WHERE m.id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setPassword(rs.getString("password"));
				member.setPhoto(rs.getString("photo"));
				member.setEmail(rs.getString("email"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}	
		return member;
	}
	//==========================================================
	//회원상세 정보
	public MemberVO getMember(int mem_num)  throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM omember m JOIN "
				+ "omember_detail d ON "
				+ "m.mem_num=d.mem_num WHERE m.mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setInt(1, mem_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setEmail(rs.getString("email"));
				member.setZipcode(rs.getString("zipcode"));
				member.setAddress1(rs.getString("address1"));
				member.setAddress2(rs.getString("address2"));
				member.setPhoto(rs.getString("photo"));
				member.setReg_date(rs.getDate("reg_date"));
				member.setModify_date(rs.getDate("modify_date"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	//==========================================================
	//회원정보 수정
	public void updateMember(MemberVO member)
	                       throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE omember_detail SET name=?,"
				+ "phone=?,email=?,zipcode=?,address1=?,"
				+ "address2=?,modify_date=SYSDATE "
				+ "WHERE mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getZipcode());
			pstmt.setString(5, member.getAddress1());
			pstmt.setString(6, member.getAddress2());
			pstmt.setInt(7, member.getMem_num());

			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//==========================================================
	//비밀번호 수정
	public void updatePassword(String password, int mem_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE omember_detail SET password=? WHERE mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password ); //새비밀번호
			pstmt.setInt(2, mem_num);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//신고 횟수 누적시키기
	public void updateReportsMember(int mem_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;

	    try {
	        conn = DBUtil.getConnection();
	        sql = "UPDATE omember_detail SET reports = reports + 1 WHERE mem_num=? IN(SELECT mem_num FROM cboard_report)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, mem_num);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(null, pstmt, conn);
	    }

	}
	
	
	//==========================================================
	//프로필 사진 수정
	public void updateMyPhoto(String photo, int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE omember_detail SET photo=? "
				+ "WHERE mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, photo);
			pstmt.setInt(2, mem_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//==========================================================
	//회원탈퇴(회원정보 삭제)
	public void deleteMember(int mem_num) throws Exception{
		
		//omember은 update고 omember_detail은 삭제하는 작업을 해야하므로 sql 문이 두 개 필요하다
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; //sql문 두개 생성하기 위함 -- 자동 커밋 해제
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			
			//첫 번째 sql - update zmembr
			sql = "UPDATE omember SET auth=0 WHERE mem_num=?";	
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.executeUpdate();
			
			//두 번째 sql - delete omember_detail(개인정보 삭제)
			sql = "DELETE FROM omember_detail WHERE mem_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, mem_num);
			pstmt2.executeUpdate();
			
			//두 sql문이 모두 성공하면 커밋!
			conn.commit();
			//두 sql문 중 하나라도 실패하면 catch --rollback
		
		} catch(Exception e) {
			//rollback을 먼저 해주고 그 다음에 throw 해주기
			conn.rollback();
			throw new Exception(e);
			
		} finally {
			//주의!!! pstmt가 2개이기 때문에 두 번 자원 정리를 해준다
			//생성 순서의 역순으로 
			//conn은 처음엔 자원정리 안 하고 마지막에 정리해주면 됨!
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
			
		}
		
		
	}
	//==========================================================
	//[관리자] 처리 - auth=9
	//전체글 개수, 검색글 개수
	public int getMemberCountByAdmin(String keyfield, String keyword) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		String sub_sql = "";
		int count = 0; //글개수 반환
		
		try {
			
			conn = DBUtil.getConnection();
			
			//조건체크
			if(keyfield != null && !"".equals(keyword)) {
				
				if(keyfield.equals("1")) {
					sub_sql += "WHERE id LIKE ?";
				}
				else if(keyfield.equals("2")) {
					sub_sql += "WHERE name LIKE ?";
				}
				else if(keyfield.equals("3")) {
					sub_sql += "WHERE email LIKE ?";
				}
			}
			
			sql = "SELECT COUNT(*) FROM omember m LEFT JOIN omember_detail d USING(mem_num)" + sub_sql;
			pstmt = conn.prepareStatement(sql);
	
			if(keyword !=null && !"".equals(keyword)) {
				pstmt.setString(1, "%"+keyword + "%");
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return count;
	}
	
	
	//==========================================================
	//목록,검색 글 목록
	public List<MemberVO> getListMemberByAdmin(int start, int end, String keyfield, String keyword) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		List<MemberVO> list = null;
		
		String sub_sql = "";
		
		int cnt = 0; //?바인딩 할 때 필요
		
		try {
			
			conn = DBUtil.getConnection();
			
			if(keyfield != null && !"".equals(keyword)) {
				
				if(keyfield.equals("1")) {
					sub_sql += "WHERE id LIKE ?";
				}
				else if(keyfield.equals("2")) {
					sub_sql += "WHERE name LIKE ?";
				}
				else if(keyfield.equals("3")) {
					sub_sql += "WHERE email LIKE ?";
				}
			}
			
			//NULLS LAST - null값은 마지막으로 정렬되게끔 처리
			sql = "SELECT * FROM (SELECT a.*, "
					+ "rownum rnum FROM (SELECT * "
					+ "FROM omember m LEFT OUTER JOIN omember_detail d "
					+ "USING(mem_num) " + sub_sql + " ORDER BY d.reg_date DESC NULLS LAST)a) "
					+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			
			//sub_sql이 적용되는 조건
			if(keyword !=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword + "%");
			}
			
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			//arraylist 생성
			list = new ArrayList<MemberVO>(); 
			
			while(rs.next()) {
				//자바빈에 저장하기 위해 객체 생성
				MemberVO member = new MemberVO();
				
				member.setMem_num(rs.getInt("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setEmail(rs.getString("email"));
				member.setZipcode(rs.getString("zipcode"));
				member.setAddress1(rs.getString("address1"));
				member.setAddress2(rs.getString("address2"));
				member.setPhoto(rs.getString("photo"));
				member.setReg_date(rs.getDate("reg_date"));
				member.setModify_date(rs.getDate("modify_date"));
				member.setReports(rs.getInt("reports"));
			
				list.add(member);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return list;
	}
	
	//==========================================================
	//회원정보 수정
	public void updateMemberByAdmin(int auth, int mem_num) throws Exception{
		

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql  =null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE omember SET auth=? WHERE mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, auth);
			pstmt.setInt(2, mem_num);
			
			pstmt.executeUpdate();
			
		}
		catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
		
	}
	
}//end DAO






