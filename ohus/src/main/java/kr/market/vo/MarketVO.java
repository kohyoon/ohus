package kr.market.vo;

import java.sql.Date;

public class MarketVO {
	private int market_num;         // 상추 게시글 번호
	private String market_title;    // 상추 게시글 제목 
	private String market_content;  // 상추 게시글 내용
	private int market_hit;         // 상추 게시글 조회수
	private Date market_regdate;    // 상추 게시글 등록일
	private Date market_modifydate; // 상추 게시글 수정일
	private int market_status;      // 거래 상태
	private String market_photo1;   // 상추 게시글 첨부파일1
	private String market_photo2;   // 상추 게시글 첨부파일2
	private int mem_num;            // 작성자 번호
	
	private String id; 			    // 작성자 아이디
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMarket_num() {
		return market_num;
	}
	public void setMarket_num(int market_num) {
		this.market_num = market_num;
	}
	public String getMarket_title() {
		return market_title;
	}
	public void setMarket_title(String market_title) {
		this.market_title = market_title;
	}
	public String getMarket_content() {
		return market_content;
	}
	public void setMarket_content(String market_content) {
		this.market_content = market_content;
	}
	public int getMarket_hit() {
		return market_hit;
	}
	public void setMarket_hit(int market_hit) {
		this.market_hit = market_hit;
	}
	public Date getMarket_regdate() {
		return market_regdate;
	}
	public void setMarket_regdate(Date market_regdate) {
		this.market_regdate = market_regdate;
	}
	public Date getMarket_modifydate() {
		return market_modifydate;
	}
	public void setMarket_modifydate(Date market_modifydate) {
		this.market_modifydate = market_modifydate;
	}
	public int getMarket_status() {
		return market_status;
	}
	public void setMarket_status(int market_status) {
		this.market_status = market_status;
	}
	public String getMarket_photo1() {
		return market_photo1;
	}
	public void setMarket_photo1(String market_photo1) {
		this.market_photo1 = market_photo1;
	}
	public String getMarket_photo2() {
		return market_photo2;
	}
	public void setMarket_photo2(String market_photo2) {
		this.market_photo2 = market_photo2;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	
}
