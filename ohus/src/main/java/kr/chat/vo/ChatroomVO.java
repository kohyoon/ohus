package kr.chat.vo;

public class ChatroomVO {
	private int chatroom;   // 채팅방 번호
	private int market_num; // 게시글 번호
	private int seller_num; // 판매자 회원번호
	private int buyer_num;  // 구매자 회원번호
	
	public int getChatroom() {
		return chatroom;
	}
	public void setChatroom(int chatroom) {
		this.chatroom = chatroom;
	}
	public int getMarket_num() {
		return market_num;
	}
	public void setMarket_num(int market_num) {
		this.market_num = market_num;
	}
	public int getSeller_num() {
		return seller_num;
	}
	public void setSeller_num(int seller_num) {
		this.seller_num = seller_num;
	}
	public int getBuyer_num() {
		return buyer_num;
	}
	public void setBuyer_num(int buyer_num) {
		this.buyer_num = buyer_num;
	}
	
	
}
