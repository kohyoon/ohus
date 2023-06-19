package kr.community.vo;

import java.sql.Date;

public class CommunityVO {
	 private int board_num; // 게시물 번호
	 private String title; // 게시물 제목
	 private int hit; // 게시물 조회수
	 private int category; // 커뮤니티 카테고리
	 private String content; // 게시물 내용
	 private String photo1; // 사진1
	 private String photo2; // 사진2
	 private Date reg_date; // 게시물 등록일
	 private Date modify_date; // 게시물 최종 수정일
	 private String ip; // ip 주소
	 private int order_num; //  주문 번호
	 private int mem_num; // 회원 번호
	 private int cboard_fav; // 게시물 좋아요 
	 
	 private String id; // 회원 아이디
	 private String photo; // 회원 프로필 사진 파일명
	
	 public int getBoard_num() {
		return board_num;
	}
	
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getHit() {
		return hit;
	}
	
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	public int getCategory() {
		return category;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPhoto1() {
		return photo1;
	}
	
	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}
	
	public String getPhoto2() {
		return photo2;
	}
	
	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	
	public Date getReg_date() {
		return reg_date;
	}
	
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	
	public Date getModify_date() {
		return modify_date;
	}
	
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getOrder_num() {
		return order_num;
	}
	
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	
	public int getMem_num() {
		return mem_num;
	}
	
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	public int getCboard_fav() {
		return cboard_fav;
	}
	
	public void setCboard_fav(int cboard_fav) {
		this.cboard_fav = cboard_fav;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
