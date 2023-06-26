package kr.event.vo;


public class EventReplyVO {
	private int re_num;
	private String re_content;
	private String re_date; // n분전으로 표시하기 위해 String으로 받는다
	private String re_modifydate;
	private String re_ip;
	private int event_num; // 부모글번호
	private int mem_num; // 회원번호
	private int event_winner; // 기본값 0(꽝), 1(당첨)
	private int re_status; // null 허용, 1이면 댓글 단 상태
	

	// 추가적으로 빈번하게 사용되는 변수를 넣어주자
	private String id;
	private int winner_count; // 이벤트 당첨자 수
	
	
	public int getRe_status() {
		return re_status;
	}

	public void setRe_status(int re_status) {
		this.re_status = re_status;
	}

	public int getWinner_count() {
		return winner_count;
	}

	public void setWinner_count(int winner_count) {
		this.winner_count = winner_count;
	}

	public int getEvent_winner() {
		return event_winner;
	}

	public void setEvent_winner(int event_winner) {
		this.event_winner = event_winner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRe_num() {
		return re_num;
	}

	public void setRe_num(int re_num) {
		this.re_num = re_num;
	}

	public String getRe_content() {
		return re_content;
	}

	public void setRe_content(String re_content) {
		this.re_content = re_content;
	}

	public String getRe_date() {
		return re_date;
	}

	public void setRe_date(String re_date) {
		this.re_date = re_date;
	}

	public String getRe_modifydate() {
		return re_modifydate;
	}

	public void setRe_modifydate(String re_modifydate) {
		this.re_modifydate = re_modifydate;
	}

	public String getRe_ip() {
		return re_ip;
	}

	public void setRe_ip(String re_ip) {
		this.re_ip = re_ip;
	}

	public int getEvent_num() {
		return event_num;
	}

	public void setEvent_num(int event_num) {
		this.event_num = event_num;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	@Override
	public String toString() {
		return "EventReplyVO [re_num=" + re_num + ", re_content=" + re_content + ", re_date=" + re_date
				+ ", re_modifydate=" + re_modifydate + ", re_ip=" + re_ip + ", event_num=" + event_num + ", mem_num="
				+ mem_num + ", event_winner=" + event_winner + ", re_status=" + re_status + ", id=" + id
				+ ", winner_count=" + winner_count + "]";
	}
}
