package kr.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.chat.vo.ChatroomVO;
import kr.util.DBUtil;

public class ChatDAO {
	// 싱글턴 패턴 
	private static ChatDAO instance = new ChatDAO();
	public static ChatDAO getInstance() {
		return instance;
	}
	private ChatDAO() {}
	
	// 채팅방 생성
	public void insertChatroom(ChatroomVO chatroom) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			
			conn = DBUtil.getConnection();
			sql = "INSERT INTO chatroom (chatroom_num,market_num,seller_num,buyer_num) VALUES (chatroom_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroom.getMarket_num());
			pstmt.setInt(2, chatroom.getSeller_num());
			pstmt.setInt(3, chatroom.getBuyer_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}
	// 채팅방 목록 조회
	
	
}
