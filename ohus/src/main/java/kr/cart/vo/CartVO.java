package kr.cart.vo;

import java.sql.Date;

import kr.item.vo.ItemVO;

public class CartVO {
	private int cart_num; // 장바구니 번호
	private int order_quantity; // 주문수량
	private Date cart_regdate; // 장바구니에 넣은 날짜
	private int item_num; // 상품번호
	private int mem_num; // 회원번호
	private int sub_total; //같은 상품의 총구매 금액
	
	private ItemVO itemVO;
	
	
	public int getCart_num() {
		return cart_num;
	}
	public void setCart_num(int cart_num) {
		this.cart_num = cart_num;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	public Date getCart_regdate() {
		return cart_regdate;
	}
	public void setCart_regdate(Date cart_regdate) {
		this.cart_regdate = cart_regdate;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getSub_total() {
		return sub_total;
	}
	public void setSub_total(int sub_total) {
		this.sub_total = sub_total;
	}
	public ItemVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(ItemVO itemVO) {
		this.itemVO = itemVO;
	}
}
