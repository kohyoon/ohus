package kr.event.vo;

public class EventWinnerVO {
	private int win_num; //당첨 식별 번호
	private int re_num; //당첨된 댓글 번호, 외래키
	private int event_num; //당첨 진행한 이벤트 번호, 외래키
	
	public int getWin_num() {
		return win_num;
	}
	public void setWin_num(int win_num) {
		this.win_num = win_num;
	}
	public int getRe_num() {
		return re_num;
	}
	public void setRe_num(int re_num) {
		this.re_num = re_num;
	}
	public int getEvent_num() {
		return event_num;
	}
	public void setEvent_num(int event_num) {
		this.event_num = event_num;
	}

}
