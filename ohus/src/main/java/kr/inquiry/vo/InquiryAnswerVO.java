package kr.inquiry.vo;

import java.sql.Date;

public class InquiryAnswerVO {
	private int ans_num;
	private String ans_content;
	private String ans_date;
	private String ans_mdate;
	private int inq_num;
	private int mem_num; //답변한 관리자 회원번호
	private String id; //답변한 관리자 아이디
	
	public int getAns_num() {
		return ans_num;
	}
	public void setAns_num(int ans_num) {
		this.ans_num = ans_num;
	}
	public String getAns_content() {
		return ans_content;
	}
	public void setAns_content(String ans_content) {
		this.ans_content = ans_content;
	}
	public String getAns_date() {
		return ans_date;
	}
	public void setAns_date(String ans_date) {
		this.ans_date = ans_date;
	}
	public String getAns_mdate() {
		return ans_mdate;
	}
	public void setAns_mdate(String ans_mdate) {
		this.ans_mdate = ans_mdate;
	}
	public int getInq_num() {
		return inq_num;
	}
	public void setInq_num(int inq_num) {
		this.inq_num = inq_num;
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
