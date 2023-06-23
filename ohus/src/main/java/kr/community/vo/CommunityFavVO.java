package kr.community.vo;

public class CommunityFavVO {
	private int fav_num; // 좋아요 번호
	private String cboard_title; // 게시글 제목
	private int mem_num; // 회원 번호
	private int cboard_num; // 게시글 번호
	
	
	
	public CommunityFavVO() {}
	
	public CommunityFavVO(int cboard_num, 
			             int mem_num) {
		this.cboard_num = cboard_num;
		this.mem_num = mem_num;
	}
	
	public int getFav_num() {
		return fav_num;
	}
	public void setFav_num(int fav_num) {
		this.fav_num = fav_num;
	}
	public String getCboard_title() {
		return cboard_title;
	}
	public void setCboard_title(String cboard_title) {
		this.cboard_title = cboard_title;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getCboard_num() {
		return cboard_num;
	}
	public void setCboard_num(int cboard_num) {
		this.cboard_num = cboard_num;
	}
	
	
}