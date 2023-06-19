package kr.community.vo;

public class CommunityFavVO {
	private int fav_num; // 좋아요 번호
	private String photo; // 게시글 사진
	private String title; // 게시글 제목
	private int mem_num; // 회원 번호
	private int board_num; // 게시글 번호
	
	public CommunityFavVO() {}
	
	public CommunityFavVO(int mem_num, int board_num) {
		this.mem_num = mem_num;
		this.board_num = board_num;
	}

	public int getFav_num() {
		return fav_num;
	}

	public void setFav_num(int fav_num) {
		this.fav_num = fav_num;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public int getBoard_num() {
		return board_num;
	}

	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	
	
}
