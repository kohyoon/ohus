package kr.qna.vo;


public class ItemAnswerVO {
	private int a_num;
	private String a_content;
	private String a_regdate;
	private String a_mdate;
	private int qna_num;
	private int mem_num;
	private String id; //테이블에 없는 요소
	
	public int getA_num() {
		return a_num;
	}
	public void setA_num(int a_num) {
		this.a_num = a_num;
	}
	public String getA_content() {
		return a_content;
	}
	public void setA_content(String a_content) {
		this.a_content = a_content;
	}
	public String getA_regdate() {
		return a_regdate;
	}
	public void setA_regdate(String a_regdate) {
		this.a_regdate = a_regdate;
	}
	public String getA_mdate() {
		return a_mdate;
	}
	public void setA_mdate(String a_mdate) {
		this.a_mdate = a_mdate;
	}
	public int getQna_num() {
		return qna_num;
	}
	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
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
