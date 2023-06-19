package kr.event.vo;

import java.sql.Date;

public class EventVO {

	private int event_num;
	private String event_title;
	private String event_photo;
	private String event_content;
	private Date event_start; //이벤트 시작일
	private Date event_end; //이벤트 종료일
	private int event_hit; //이벤트 조회수
	private Date event_modifydate; //이벤트 게시글 수정일
	private int winner_count; //이벤트 당첨자 수
	private int mem_num;
	
	
	public Date getEvent_modifydate() {
		return event_modifydate;
	}
	public void setEvent_modifydate(Date event_modifydate) {
		this.event_modifydate = event_modifydate;
	}
	public String getEvent_title() {
		return event_title;
	}
	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}
	public int getEvent_hit() {
		return event_hit;
	}
	public void setEvent_hit(int event_hit) {
		this.event_hit = event_hit;
	}
	public int getEvent_num() {
		return event_num;
	}
	public void setEvent_num(int event_num) {
		this.event_num = event_num;
	}
	public String getEvent_photo() {
		return event_photo;
	}
	public void setEvent_photo(String event_photo) {
		this.event_photo = event_photo;
	}
	public String getEvent_content() {
		return event_content;
	}
	public void setEvent_content(String event_content) {
		this.event_content = event_content;
	}

	public Date getEvent_start() {
		return event_start;
	}
	public void setEvent_start(Date event_start) {
		this.event_start = event_start;
	}
	public Date getEvent_end() {
		return event_end;
	}
	public void setEvent_end(Date event_end) {
		this.event_end = event_end;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getWinner_count() {
		return winner_count;
	}
	public void setWinner_count(int winner_count) {
		this.winner_count = winner_count;
	}
	
	
	
}
