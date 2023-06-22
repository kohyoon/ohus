package kr.order.vo;

import java.sql.Date;
  
public class OrderVO {
	private int order_num; // 주문번호
	private String item_name; // 상품명
	private String order_name; // 수령자 이름
	private String mem_phone; // 주문자(회원) 전화번호
	private String order_zipcode; // 수령자 우편번호
	private String order_address1; // 수령자 주소
	private String order_address2; // 수령자 상세주소
	private int order_quantity; // 주문 수량
	private int order_total; // 총 주문 가격
	private String order_notice; // 배송메시지
	private Date order_regdate; // 주문 날짜
	private int order_payment; // 결제 방법
	private int order_status; // 배송상태
	private Date order_modifydate; // 배송상태 수정일
	private int mem_num; // 회원 번호
	private String id;
	
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getOrder_name() {
		return order_name;
	}
	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getOrder_zipcode() {
		return order_zipcode;
	}
	public void setOrder_zipcode(String order_zipcode) {
		this.order_zipcode = order_zipcode;
	}
	public String getOrder_address1() {
		return order_address1;
	}
	public void setOrder_address1(String order_address1) {
		this.order_address1 = order_address1;
	}
	public String getOrder_address2() {
		return order_address2;
	}
	public void setOrder_address2(String order_address2) {
		this.order_address2 = order_address2;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	public int getOrder_total() {
		return order_total;
	}
	public void setOrder_total(int order_total) {
		this.order_total = order_total;
	}
	public String getOrder_notice() {
		return order_notice;
	}
	public void setOrder_notice(String order_notice) {
		this.order_notice = order_notice;
	}
	public Date getOrder_regdate() {
		return order_regdate;
	}
	public void setOrder_regdate(Date order_regdate) {
		this.order_regdate = order_regdate;
	}
	public int getOrder_payment() {
		return order_payment;
	}
	public void setOrder_payment(int order_payment) {
		this.order_payment = order_payment;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public Date getOrder_modifydate() {
		return order_modifydate;
	}
	public void setOrder_modifydate(Date order_modifydate) {
		this.order_modifydate = order_modifydate;
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
