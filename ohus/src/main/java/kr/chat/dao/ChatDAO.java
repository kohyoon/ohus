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
	// 채팅방 개수 조회 (작성자가 채팅하기 버튼을 누를 경우)
	public int getChatroomCount(int seller_num, int market_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM chatroom c JOIN market m ON c.market_num = m.market_num WHERE c.market_num=? AND c.seller_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, market_num);
			pstmt.setInt(2, seller_num);
			
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
			sql = "SELECT * FROM chatroom c JOIN market m ON c.market_num = m.market_num WHERE c.market_num=? AND c.seller_num=?";
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
				chatroom.setMarket_title(rs.getString("market_title"));
				list.add(chatroom);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	// 채팅방 목록 조회 (구매자가 채팅하기 누를 경우 생성할지 말지 결정)
	public ChatroomVO getChatroomByBuyer(int market_num, int buyer_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ChatroomVO chatroom = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM chatroom WHERE market_num = ? AND buyer_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, market_num);
			pstmt.setInt(2, buyer_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				chatroom = new ChatroomVO();
				chatroom.setChatroom_num(rs.getInt("chatroom_num"));
				chatroom.setMarket_num(rs.getInt("market_num"));
				chatroom.setBuyer_num(rs.getInt("buyer_num"));
				chatroom.setSeller_num(rs.getInt("seller_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return chatroom;
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
			sql = "SELECT * FROM chat c LEFT JOIN omember o ON c.mem_num = o.mem_num WHERE chatroom_num = ? ORDER BY chat_num ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroom_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				chat = new ChatVO();
				chat.setChat_num(rs.getInt("chat_num"));
				chat.setChatroom_num(rs.getInt("chatroom_num"));
				chat.setMem_num(rs.getInt("mem_num"));
				chat.setMessage(rs.getString("message"));
				chat.setReg_date(rs.getString("reg_date"));
				chat.setRead_check(rs.getInt("read_check"));
				chat.setId(rs.getString("id"));
				list.add(chat);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	// 채팅방 상세 정보 조회 (채팅 화면에 채팅방에 대한 정보를 뿌리기 위함, 채팅방이 생성된 게시물의 title, 상대방의 id가 필요)
	// 구매자 입장에서는 -> seller의 ID를 헤더에 띄우고, chatroom테이블의 marketNum을 이용해 title을 가져옴
	// 판매자 입장에서는 -> buyer의 ID를 헤더에 띄우고, chatroom 테이블의 marketNum을 이용해 title을 가져옴
	// 조인 방법 SELECT * FROM chatroom c LEFT JOIN market m ON c.market_num=m.market_num LEFT JOIN omember o ON c.buyer_num = o.mem_num WHERE chatroom_num = 21;
	
}
