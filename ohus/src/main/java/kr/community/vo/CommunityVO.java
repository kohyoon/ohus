package kr.community.vo;

import java.sql.Date;


public class CommunityVO {
	private int cboard_num; //글번호
	private String cboard_title; //제목
	private int cboard_hit; //조회수
	private int cboard_category; //카테고리
	private String cboard_content; //내용
	private String cboard_photo1; //사진1
	private String cboard_photo2; //사진2
	private Date cboard_regdate; //등록일
	private Date cboard_modifydate; //수정일
	private String cboard_ip; //ip
	private int order_num; //주문번호
	private int mem_num; //회원번호
	private int cboard_fav; //좋아요
	
	private String id;//회원 아이디
	private String photo; //회원 프로필 사진 파일명
	
	public int getCboard_num() {
		return cboard_num;
	}
	public void setCboard_num(int cboard_num) {
		this.cboard_num = cboard_num;
	}
	public String getCboard_title() {
		return cboard_title;
	}
	public void setCboard_title(String cboard_title) {
		this.cboard_title = cboard_title;
	}
	public int getCboard_hit() {
		return cboard_hit;
	}
	public void setCboard_hit(int cboard_hit) {
		this.cboard_hit = cboard_hit;
	}
	public int getCboard_category() {
		return cboard_category;
	}
	public void setCboard_category(int cboard_category) {
		this.cboard_category = cboard_category;
	}
	public String getCboard_content() {
		return cboard_content;
	}
	public void setCboard_content(String cboard_content) {
		this.cboard_content = cboard_content;
	}
	public String getCboard_photo1() {
		return cboard_photo1;
	}
	public void setCboard_photo1(String cboard_photo1) {
		this.cboard_photo1 = cboard_photo1;
	}
	public String getCboard_photo2() {
		return cboard_photo2;
	}
	public void setCboard_photo2(String cboard_photo2) {
		this.cboard_photo2 = cboard_photo2;
	}
	public Date getCboard_regdate() {
		return cboard_regdate;
	}
	public void setCboard_regdate(Date cboard_regdate) {
		this.cboard_regdate = cboard_regdate;
	}
	public Date getCboard_modifydate() {
		return cboard_modifydate;
	}
	public void setCboard_modifydate(Date cboard_modifydate) {
		this.cboard_modifydate = cboard_modifydate;
	}
	public String getCboard_ip() {
		return cboard_ip;
	}
	public void setCboard_ip(String cboard_ip) {
		this.cboard_ip = cboard_ip;
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
