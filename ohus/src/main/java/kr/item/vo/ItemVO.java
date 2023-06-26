package kr.item.vo;

import java.sql.Date;

public class ItemVO {
	private int item_num;
	private String item_name;
	private int item_category;
	private int item_price;
	private String item_content;
	private int item_stock;
	private int item_hit;
	private int item_fav;
	private int item_score;
	private String item_origin;
	private String item_photo1;
	private String item_photo2;
	private String item_photo3;
	private Date item_regdate;
	private Date item_mdate;
	private int item_status;
	private int order_quantity;
	
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
	public int getItem_category() {
		return item_category;
	}
	public void setItem_category(int item_category) {
		this.item_category = item_category;
	}
	public int getItem_price() {
		return item_price;
	}
	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}
	public String getItem_content() {
		return item_content;
	}
	public void setItem_content(String item_content) {
		this.item_content = item_content;
	}
	public int getItem_stock() {
		return item_stock;
	}
	public void setItem_stock(int item_stock) {
		this.item_stock = item_stock;
	}
	public int getItem_hit() {
		return item_hit;
	}
	public void setItem_hit(int item_hit) {
		this.item_hit = item_hit;
	}
	public int getItem_fav() {
		return item_fav;
	}
	public void setItem_fav(int item_fav) {
		this.item_fav = item_fav;
	}
	public int getItem_score() {
		return item_score;
	}
	public void setItem_score(int item_score) {
		this.item_score = item_score;
	}
	public String getItem_origin() {
		return item_origin;
	}
	public void setItem_origin(String item_origin) {
		this.item_origin = item_origin;
	}
	public String getItem_photo1() {
		return item_photo1;
	}
	public void setItem_photo1(String item_photo1) {
		this.item_photo1 = item_photo1;
	}
	public String getItem_photo2() {
		return item_photo2;
	}
	public void setItem_photo2(String item_photo2) {
		this.item_photo2 = item_photo2;
	}
	public String getItem_photo3() {
		return item_photo3;
	}
	public void setItem_photo3(String item_photo3) {
		this.item_photo3 = item_photo3;
	}
	public Date getItem_regdate() {
		return item_regdate;
	}
	public void setItem_regdate(Date item_regdate) {
		this.item_regdate = item_regdate;
	}
	public Date getItem_mdate() {
		return item_mdate;
	}
	public void setItem_mdate(Date item_mdate) {
		this.item_mdate = item_mdate;
	}
	public int getItem_status() {
		return item_status;
	}
	public void setItem_status(int item_status) {
		this.item_status = item_status;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	
}
