package hospital.vo;

import javafx.scene.control.CheckBox;

public class UserVO {
	// UserVO
	private String id; // 아이디 (PK)
	private String password; // 비밀번호
	private String name; // 성명
	private String regNum; // 주민번호 (UNIQUE)
	private String phoneNum; // 전화번호
	private CheckBox chk; // admin_User_Info 회원 선택용

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj instanceof UserVO) {
			UserVO other = (UserVO) obj;
			return this.id.equals(other.id);
		}
		return false;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public CheckBox getChk() {
		return chk;
	}

	public void setChk(CheckBox chk) {
		this.chk = chk;
	}

	public UserVO() {
	}

	public UserVO(String id, String password, String name, String regNum, String phoneNum) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.regNum = regNum;
		if ("null".equals(phoneNum)) {
			this.phoneNum = "";
		} else {
			this.phoneNum = phoneNum;
		}
		this.chk = new CheckBox();
	}

}