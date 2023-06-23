package kr.community.vo;

public class CommunityReportVO {
	private int dec_num; // 신고 번호
	private int dec_category; // 신고 카테고리
	private String dec_regdate; // 신고 날짜
	private int mem_num; // 회원 번호
	private int re_num; // 댓글 번호
	
	
	public int getDec_num() {
		return dec_num;
	}
	
	public void setDec_num(int dec_num) {
		this.dec_num = dec_num;
	}
	
	public int getDec_category() {
		return dec_category;
	}
	
	public void setDec_category(int dec_category) {
		this.dec_category = dec_category;
	}
	
	public String getDec_regdate() {
		return dec_regdate;
	}
	
	public void setDec_regdate(String dec_regdate) {
		this.dec_regdate = dec_regdate;
	}
	
	public int getMem_num() {
		return mem_num;
	}
	
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	public int getRe_num() {
		return re_num;
	}
	
	public void setRe_num(int re_num) {
		this.re_num = re_num;
	}
	
}
