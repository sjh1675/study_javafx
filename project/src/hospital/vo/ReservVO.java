package hospital.vo;

import java.sql.Timestamp;

public class ReservVO {

	private String id; // 아이디 (FK)
	private String name; // 성명
	private Timestamp time; // 예약일
	
	public ReservVO() {}
	
	public ReservVO(String id, String name, Timestamp time) {
		this.id = id;
		this.name = name;
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}