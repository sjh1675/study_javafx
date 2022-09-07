package dto;

import java.time.LocalDateTime;

public class Machine {
	private int mnum;
	private int mamount;
	private String mphone;
	private String mtemperature;
	private String mdegree;
	private LocalDateTime mtime;
	private int inputamount;
	
	public Machine() {
		// TODO Auto-generated constructor stub
	}

	public Machine(int mnum, int mamount) {
		super();
		this.mnum = mnum;
		this.mamount = mamount;
	}
	public Machine(int mnum, int mamount, String mphone, String mtemperature, String mdegree, LocalDateTime mtime,
			int inputamount) {
		super();
		this.mnum = mnum;
		this.mamount = mamount;
		this.mphone = mphone;
		this.mtemperature = mtemperature;
		this.mdegree = mdegree;
		this.mtime = mtime;
		this.inputamount = 0;
	}

	public int getMnum() {
		return mnum;
	}

	public void setMnum(int mnum) {
		this.mnum = mnum;
	}

	public int getMamount() {
		return mamount;
	}

	public void setMamount(int mamount) {
		this.mamount = mamount;
	}

	public String getMphone() {
		return mphone;
	}

	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

	public String getMtemperature() {
		return mtemperature;
	}

	public void setMtemperature(String mtemperature) {
		this.mtemperature = mtemperature;
	}

	public String getMdegree() {
		return mdegree;
	}

	public void setMdegree(String mdegree) {
		this.mdegree = mdegree;
	}

	public LocalDateTime getMtime() {
		return mtime;
	}

	public void setMtime(LocalDateTime mtime) {
		this.mtime = mtime;
	}

	public int getinputamount() {
		return inputamount;
	}

	public void setinputamount(int inputamount) {
		this.inputamount = inputamount;
	}
	
}