package kr.chat.vo;

import java.sql.Date;

public class ChatVO {
	private int chat_num;      // 채팅 메시지 번호
	private int chatroom_num;  // 채팅방 번호
	private int mem_num;       // 작성자 번호
	private String message;    // 채팅 메시지
	private Date reg_date;     // 채팅 메시지 발송 날짜
	private int read_check;    // 읽기 여부
	
	private String id;		   // 메시지 보낸 사람의 아이디
	
	public int getChat_num() {
		return chat_num;
	}
	public void setChat_num(int chat_num) {
		this.chat_num = chat_num;
	}
	public int getChatroom_num() {
		return chatroom_num;
	}
	public void setChatroom_num(int chatroom_num) {
		this.chatroom_num = chatroom_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getRead_check() {
		return read_check;
	}
	public void setRead_check(int read_check) {
		this.read_check = read_check;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
