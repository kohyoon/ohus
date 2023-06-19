package kr.inquiry.vo;

import java.sql.Date;

public class InquiryVO {
	private int inq_num;
	private String inq_title;
	private int inq_category;
	private String inq_content;
	private Date inq_regdate;
	private String inq_ip;
	private int inq_status;
	private int mem_num;
	private String id; //회원아이디
	
	public int getInq_num() {
		return inq_num;
	}
	public void setInq_num(int inq_num) {
		this.inq_num = inq_num;
	}
	public String getInq_title() {
		return inq_title;
	}
	public void setInq_title(String inq_title) {
		this.inq_title = inq_title;
	}
	public int getInq_category() {
		return inq_category;
	}
	public void setInq_category(int inq_category) {
		this.inq_category = inq_category;
	}
	public String getInq_content() {
		return inq_content;
	}
	public void setInq_content(String inq_content) {
		this.inq_content = inq_content;
	}
	public Date getInq_regdate() {
		return inq_regdate;
	}
	public void setInq_regdate(Date inq_regdate) {
		this.inq_regdate = inq_regdate;
	}
	public String getInq_ip() {
		return inq_ip;
	}
	public void setInq_ip(String inq_ip) {
		this.inq_ip = inq_ip;
	}
	public int getInq_status() {
		return inq_status;
	}
	public void setInq_status(int inq_status) {
		this.inq_status = inq_status;
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
