package kr.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.chat.vo.ChatVO;
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
	// 채팅방 목록 조회 (작성자가 채팅하기 버튼을 누를 경우)
	public List<ChatroomVO> getChatroomList(int seller_num, int market_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<ChatroomVO> list = new ArrayList<ChatroomVO>();
		ChatroomVO chatroom = null;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM chatroom WHERE market_num=? AND seller_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, market_num);
			pstmt.setInt(2, seller_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				chatroom = new ChatroomVO();
				chatroom.setChatroom_num(rs.getInt("chatroom_num"));
				chatroom.setBuyer_num(rs.getInt("buyer_num"));
				chatroom.setSeller_num(rs.getInt("seller_num"));
				chatroom.setMarket_num(rs.getInt("market_num"));
				list.add(chatroom);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	// 채팅 메시지 작성
	public void insertChatMessage(ChatVO chat) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO chat (chat_num,chatroom_num,mem_num,message,reg_date,read_check) "
					+ "VALUES (chat_seq.nextval,?,?,?,SYSDATE,1)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat.getChatroom_num());
			pstmt.setInt(2, chat.getMem_num());
			pstmt.setString(3, chat.getMessage());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 채팅 메시지 조회
	public List<ChatVO> getChatMessageList(int chatroom_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<ChatVO> list = new ArrayList<ChatVO>();
		ChatVO chat = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM chat WHERE chatroom_num = ? ORDER BY chat_num ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroom_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				chat = new ChatVO();
				chat.setChat_num(rs.getInt("chat_num"));
				chat.setChatroom_num(rs.getInt("chatroom_num"));
				chat.setMem_num(rs.getInt("mem_num"));
				chat.setMessage(rs.getString("message"));
				chat.setReg_date(rs.getDate("reg_date"));
				chat.setRead_check(rs.getInt("read_check"));
				list.add(chat);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
}
