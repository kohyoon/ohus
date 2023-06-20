package kr.notice.vo;

import java.sql.Date;

public class NoticeVO {
	private int notice_num;
	private String notice_title;
	private String notice_content;
	private Date notice_regdate;
	private Date notice_mdate;
	private int notice_hit;
	private String notice_filename;
	private int mem_num; //공지사항 작성한 회원 번호
	private String id; //공지사항 작성한 회원 아이디
	
	public int getNotice_num() {
		return notice_num;
	}
	public void setNotice_num(int notice_num) {
		this.notice_num = notice_num;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public String getNotice_content() {
		return notice_content;
	}
	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}
	public Date getNotice_regdate() {
		return notice_regdate;
	}
	public void setNotice_regdate(Date notice_regdate) {
		this.notice_regdate = notice_regdate;
	}
	public Date getNotice_mdate() {
		return notice_mdate;
	}
	public void setNotice_mdate(Date notice_mdate) {
		this.notice_mdate = notice_mdate;
	}
	public int getNotice_hit() {
		return notice_hit;
	}
	public void setNotice_hit(int notice_hit) {
		this.notice_hit = notice_hit;
	}
	public String getNotice_filename() {
		return notice_filename;
	}
	public void setNotice_filename(String notice_filename) {
		this.notice_filename = notice_filename;
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
