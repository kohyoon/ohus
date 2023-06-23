package kr.community.vo;

public class CommunityReplyVO {
	private int re_num; // 댓글 번호
	private String re_content; // 댓글 내용
	private String re_date; // 댓글 작성일
	private String re_modifydate; // 댓글 수정일
	private String re_ip; // 댓글 ip
	private int re_fav; // 댓글 좋아요
	private int cboard_num; // 게시글 번호
	private int mem_num; // 회원 번호
	private String id; // 아이디
	
	
	
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
	
	public int getRe_fav() {
		return re_fav;
	}
	
	public void setRe_fav(int re_fav) {
		this.re_fav = re_fav;
	}
	
	public int getCboard_num() {
		return cboard_num;
	}
	
	public void setCboard_num(int cboard_num) {
		this.cboard_num = cboard_num;
	}
	
	public int getMem_num() {
		return mem_num;
	}
	
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	
}
