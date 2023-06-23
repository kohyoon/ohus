package kr.qna.vo;

import java.sql.Date;

public class QnaVO {
	private int qna_num;
	private String qna_title;
	private String qna_content;
	private Date qna_regdate;
	private Date qna_mdate;
	private String qna_ip;
	private int qna_status;
	private String qna_filename;
	private int mem_num;
	private String id; //작성자 아이디
	private int detail_num; //주문 상세 번호
	private int order_num; //주문 번호
	private int item_num; //상품번호
	
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
	public String getQna_filename() {
		return qna_filename;
	}
	public void setQna_filename(String qna_filename) {
		this.qna_filename = qna_filename;
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
	public int getDetail_num() {
		return detail_num;
	}
	public void setDetail_num(int detail_num) {
		this.detail_num = detail_num;
	}
	public int getOrder_num() {
		return order_num; 
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
}
