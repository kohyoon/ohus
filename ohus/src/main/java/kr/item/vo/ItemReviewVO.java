package kr.item.vo;

import java.sql.Date;

public class ItemReviewVO {
	private int review_num;//상품 리뷰번호
	private int mem_num;//리뷰 작성 회원번호
	private int item_num;//리뷰 상품번호
	private int item_score;//상품 별점
	private String review_content;//리뷰 내용
	private String review_photo;//리뷰 사진
	private Date review_regdate;//리뷰 등록일
	private String id;//id
	
	public int getReview_num() {
		return review_num;
	}
	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public int getItem_score() {
		return item_score;
	}
	public void setItem_score(int item_score) {
		this.item_score = item_score;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public String getReview_photo() {
		return review_photo;
	}
	public void setReview_photo(String review_photo) {
		this.review_photo = review_photo;
	}
	public Date getReview_regdate() {
		return review_regdate;
	}
	public void setReview_regdate(Date review_regdate) {
		this.review_regdate = review_regdate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
