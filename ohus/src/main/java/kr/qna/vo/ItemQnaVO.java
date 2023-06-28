package kr.qna.vo;

import java.sql.Date;

public class ItemQnaVO {
	private int qna_num;
	private String qna_title;
	private String qna_content;
	private int qna_category; // 1:상품|2:배송|3:반품|4:교환|5:환불|6:기타
	private Date qna_regdate;
	private Date qna_mdate;
	private String qna_ip;
	private int qna_status;
	private int mem_num; //작성자
	private String id; //테이블에 없는 요소
	private int item_num;
	private String item_name; //테이블에 없는 요소
	
	public int getQna_num() {
		return qna_num;
	}
	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
	}
	public String getQna_title() {
		return qna_title;
	}
	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	public String getQna_content() {
		return qna_content;
	}
	public void setQna_content(String qna_content) {
		this.qna_content = qna_content;
	}
	public int getQna_category() {
		return qna_category;
	}
	public void setQna_category(int qna_category) {
		this.qna_category = qna_category;
	}
	public Date getQna_regdate() {
		return qna_regdate;
	}
	public void setQna_regdate(Date qna_regdate) {
		this.qna_regdate = qna_regdate;
	}
	public Date getQna_mdate() {
		return qna_mdate;
	}
	public void setQna_mdate(Date qna_mdate) {
		this.qna_mdate = qna_mdate;
	}
	public String getQna_ip() {
		return qna_ip;
	}
	public void setQna_ip(String qna_ip) {
		this.qna_ip = qna_ip;
	}
	public int getQna_status() {
		return qna_status;
	}
	public void setQna_status(int qna_status) {
		this.qna_status = qna_status;
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
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
}
