package kr.event.vo;

import java.time.LocalDate;
import java.util.Calendar;

public class EventVO {

	private int event_num;
	private String event_title;
	private String event_photo;
	private String event_content;
	private String event_start; //이벤트 시작일
	private String event_end; //이벤트 종료일
	private int event_hit; //이벤트 조회수
	private String event_regdate; //이벤트 게시글 작성일
	private String event_modifydate; //이벤트 게시글 수정일
	private int winner_count; //이벤트 당첨자 수
	private int mem_num;
	private int event_status; //1:종료된 이벤트, 2:진행중인 이벤트
	
	public int getEvent_status() {
		String[] event_ends = event_start.split("-");
		LocalDate end_day = LocalDate.of(Integer.parseInt(event_ends[0]), Integer.parseInt(event_ends[1]), Integer.parseInt(event_ends[2]));
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int date = cal.get(Calendar.DATE);
		LocalDate now_day = LocalDate.of(year,month,date);
		//음수면 지난 날짜, 0 같은 날짜, 양수면 남은 남자
		int result = end_day.compareTo(now_day);
		if(result<0) {
			event_status = 1;
		}else {
			event_status = 2;
		}
		return event_status;
	}
	public void setEvent_status(int event_status) {
		this.event_status = event_status;
	}
	public String getEvent_start() {
		return event_start;
	}
	public void setEvent_start(String event_start) {
		this.event_start = event_start;
	}
	public String getEvent_end() {
		return event_end;
	}
	public void setEvent_end(String event_end) {
		this.event_end = event_end;
	}
	public String getEvent_regdate() {
		return event_regdate;
	}
	public void setEvent_regdate(String event_regdate) {
		this.event_regdate = event_regdate;
	}
	public String getEvent_modifydate() {
		return event_modifydate;
	}
	public void setEvent_modifydate(String event_modifydate) {
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
