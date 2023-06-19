package kr.item.vo;

public class ItemFavVO {
	
	private int fav_num;//상품찜번호
	private int item_num;//상품번호
	private int mem_num;//회원번호
	
	public ItemFavVO() {}
	public ItemFavVO(int item_num, int mem_num) {
		this.item_num = item_num;
		this.mem_num = mem_num;
	}

	public int getFav_num() {
		return fav_num;
	}

	public void setFav_num(int fav_num) {
		this.fav_num = fav_num;
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
}
