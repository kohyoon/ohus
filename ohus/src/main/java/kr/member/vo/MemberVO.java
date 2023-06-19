package kr.member.vo;

import java.sql.Date;

public class MemberVO {
	private int  mem_num; // 회원 식별번호
	private String id; // 회원 아이디 영문 숫자 포함 최소 6자 최대 12자
	private int auth; // 회원 등급 0:탈퇴, 1:정지, 2:일반, 9:관리자
	private String name;
	private int reports;
	private String password; // 영문 숫자 포함 8자 이상
	private String phone;
	private String email;
	private String zipcode;
	private String address1;
	private String address2;
	private String photo;
	private Date reg_date; // 회원 가입일 default SYSDATE
	private Date modify_date; // 회원 정보 수정일

	//비밀번호 일치 여부 체크
	public boolean isCheckedPassword(String userPassword) {
		//회원등급(auth):0탈퇴회원,1정지회원,2일반회원,9관리자
		if( auth > 1 && password.equals(userPassword)) {
			return true;
		}
		return false;
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

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReports() {
		return reports;
	}

	public void setReports(int reports) {
		this.reports = reports;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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
	
}
