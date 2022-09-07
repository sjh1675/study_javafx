package hospital.vo;

import java.sql.Timestamp;

public class InpatientVO {

	private String id; // 아이디 (FK)
	private String name; // 성명
	private Timestamp time; // 입원일
	private String room; // 입원 호실
	
	public InpatientVO() {}
	
	public InpatientVO(String id, String name, Timestamp time, String room) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.room = room;
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

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
		
}