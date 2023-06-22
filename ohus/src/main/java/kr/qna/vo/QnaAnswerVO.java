package kr.qna.vo;

import java.sql.Date;

public class QnaAnswerVO {
	private int qans_num;
	private String qans_content;
	private Date qans_date;
	private Date qans_mdate;
	private int qna_num;
	private int mem_num; //답변 작성자 회원번호
	private String id; //답변 작성자 아이디
	
	public int getQans_num() {
		return qans_num;
	}
	public void setQans_num(int qans_num) {
		this.qans_num = qans_num;
	}
	public String getQans_content() {
		return qans_content;
	}
	public void setQans_content(String qans_content) {
		this.qans_content = qans_content;
	}
	public Date getQans_date() {
		return qans_date;
	}
	public void setQans_date(Date qans_date) {
		this.qans_date = qans_date;
	}
	public Date getQans_mdate() {
		return qans_mdate;
	}
	public void setQans_mdate(Date qans_mdate) {
		this.qans_mdate = qans_mdate;
	}
	public int getQna_num() {
		return qna_num;
	}
	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
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
